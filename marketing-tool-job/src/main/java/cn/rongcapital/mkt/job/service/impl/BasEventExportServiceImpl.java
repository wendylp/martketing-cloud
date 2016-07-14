package cn.rongcapital.mkt.job.service.impl;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.enums.BasEventEnum;
import cn.rongcapital.mkt.common.util.FileUtil;
import cn.rongcapital.mkt.dao.DataArchPointDao;
import cn.rongcapital.mkt.dao.DataCustomerTagsDao;
import cn.rongcapital.mkt.dao.DataLoginDao;
import cn.rongcapital.mkt.dao.DataMemberDao;
import cn.rongcapital.mkt.dao.DataPartyDao;
import cn.rongcapital.mkt.dao.DataPaymentDao;
import cn.rongcapital.mkt.dao.DataPopulationDao;
import cn.rongcapital.mkt.dao.DataShoppingDao;
import cn.rongcapital.mkt.dao.base.BaseDao;
import cn.rongcapital.mkt.job.service.base.BasEventExportService;
import cn.rongcapital.mkt.po.base.BaseQuery;
import cn.rongcapital.mkt.vo.out.BasEventOut;

@Service
@SuppressWarnings({"rawtypes", "unchecked"})
public class BasEventExportServiceImpl implements BasEventExportService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    // 每次获取的数据量,不能一次把数据库的数据都导出来
    private static final int LOOP_COUNT = 1000;

    @Autowired
    private DataPartyDao dataPartyDao;

    @Autowired
    private DataPopulationDao dataPopulationDao;

    @Autowired
    private DataCustomerTagsDao dataCustomerTagsDao;

    @Autowired
    private DataArchPointDao dataArchPointDao;

    @Autowired
    private DataMemberDao dataMemberDao;

    @Autowired
    private DataLoginDao dataLoginDao;

    @Autowired
    private DataPaymentDao dataPaymentDao;

    @Autowired
    private DataShoppingDao dataShoppingDao;

    @Override
    public File exportData() {
        List<BasEventOut> basEventOuts = new ArrayList<>();
        Map<String, BaseDao> dataDaoMap = getInitDataDaoMap();
        Map<String, BaseDao> userDaoMap = getInitUserDaoMap();

        List<Map<String, String>> colNames = new ArrayList<>();
        for (BasEventEnum basEventEnum : BasEventEnum.values()) {
            Map<String, String> colNameMap = new HashMap<>();
            colNameMap.put(basEventEnum.getBasENName(), basEventEnum.getBasCNName());
            colNames.add(colNameMap);
        }

        FileUtil.generateFileforDownload(colNames, basEventOuts, "Basevent");
        return null;
    }


    private Map<String, BaseDao> getInitDataDaoMap() {
        Map<String, BaseDao> daoMap = new HashMap<>();
        daoMap.put("dataArchPointDao", dataArchPointDao);
        daoMap.put("dataLoginDao", dataLoginDao);
        daoMap.put("dataPaymentDao", dataPaymentDao);
        daoMap.put("dataShoppingDao", dataShoppingDao);

        return daoMap;
    }

    // TYPE1_人口属性,TYPE2_客户标签,TYPE4_会员卡记录是需要合并到User文件中的
    private Map<String, BaseDao> getInitUserDaoMap() {
        Map<String, BaseDao> daoMap = new HashMap<>();
        daoMap.put("dataPopulationDao", dataPopulationDao);
        daoMap.put("dataCustomerTagsDao", dataCustomerTagsDao);
        daoMap.put("dataMemberDao", dataMemberDao);

        return daoMap;
    }

    private BasEventOut transferDataToBasEventVO(BaseQuery data) {
        BasEventOut basEventOut = new BasEventOut();
        BasEventEnum[] basEventEnums = BasEventEnum.values();
        boolean isNothingAssigned = true;
        for (BasEventEnum basEventEnum : basEventEnums) {
            if (StringUtils.isEmpty(basEventEnum.getMcName())) {
                continue;
            } else {
                try {
                    Field dataField = data.getClass().getDeclaredField(basEventEnum.getMcName());
                    Field baseEventVOField = basEventOut.getClass().getDeclaredField(basEventEnum.getBasENName());
                    dataField.setAccessible(true);
                    baseEventVOField.setAccessible(true);
                    Object dataFieldValue = dataField.get(data);
                    baseEventVOField.set(basEventOut, dataFieldValue);
                    isNothingAssigned = false;
                } catch (NoSuchFieldException | SecurityException | IllegalArgumentException
                                | IllegalAccessException e) {
                    logger.info("BasEvent对象转换的时候调用了不存在的field,这都不是事");
                }
            }
        }

        return isNothingAssigned ? null : basEventOut;
    }

    private List<BasEventOut> getBasEventVoByDaos(Map<String, BaseDao> daoMap) {

        List<BasEventOut> basEventOuts = new ArrayList<>();

        for (Map.Entry<String, BaseDao> entry : daoMap.entrySet()) {
            List<BaseQuery> datas = entry.getValue().selectList(null);
            if (!CollectionUtils.isEmpty(datas)) {
                int totalCount = datas.size();
                int loopTimes = (totalCount + LOOP_COUNT - 1) / LOOP_COUNT;
                for (int i = 0; i < loopTimes; i++) {
                    int startIndex = i * LOOP_COUNT;
                    int endIndex = (i + 1) * LOOP_COUNT;
                    // 如果是最后一次循环,endIndex就是最大值了.
                    if (i == loopTimes - 1) {
                        endIndex = totalCount;
                    }

                    List<BaseQuery> tmpData = datas.subList(startIndex, endIndex);

                    for (int j = 0; j < tmpData.size(); j++) {
                        BaseQuery data = datas.get(j);
                        BasEventOut tmpBasEventOut = transferDataToBasEventVO(data);
                        if (tmpBasEventOut != null) {
                            basEventOuts.add(tmpBasEventOut);
                        }
                    }

                }
            }
        }

        return basEventOuts;
    }

}
