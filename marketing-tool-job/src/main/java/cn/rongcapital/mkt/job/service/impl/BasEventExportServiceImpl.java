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
import cn.rongcapital.mkt.po.DataParty;
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
        Map<String, BaseDao> daoMap = getInitDataDaoMap();
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

        List<Map<String, String>> colNames = new ArrayList<>();
        for (BasEventEnum basEventEnum : BasEventEnum.values()) {
            Map<String, String> colNameMap = new HashMap<>();
            colNameMap.put(basEventEnum.getBasENName(), basEventEnum.getBasCNName());
            colNames.add(colNameMap);
        }

        FileUtil.generateFileforDownload(colNames, basEventOuts, "Basevent");
        return null;
    }

    // 获取所有整理为BAS Event格式的数据
    private List<BasEventOut> getBasData() {

        // 1.获取dataParty中的数据数量,这也是最终导出的数据数量
        DataParty paramDataPary = new DataParty();
        // 导出未被删除的数据
        paramDataPary.setStatus((byte) 0);
        int totalCount = dataPartyDao.selectListCount(paramDataPary);
        List<DataParty> dataParties = dataPartyDao.selectList(paramDataPary);
        List<BasEventOut> basEventOuts = new ArrayList<>(totalCount);

        logger.info("BAS Event预计要导出的数据量为 : {}条", totalCount);

        // 循环次数为"一个房间能住N个人,M个人需要多少房间"的问题.不解释
        int loopTimes = (totalCount + LOOP_COUNT - 1) / LOOP_COUNT;
        for (int i = 0; i < loopTimes; i++) {
            int startIndex = i * LOOP_COUNT;
            int endIndex = (i + 1) * LOOP_COUNT;
            // 如果是最后一次循环,endIndex就是最大值了.
            if (i == loopTimes - 1) {
                endIndex = totalCount;
            }

            basEventOuts.addAll(fillBasEventVo(dataParties.subList(startIndex, endIndex)));
        }

        return basEventOuts;
    }

    // 根据每次截取出来的dataParty数据,查询相关的数据表,并导入BasEventOut对象中去
    private List<BasEventOut> fillBasEventVo(List<DataParty> dataParties) {
        int dataPartyCount = dataParties.size();
        List<BasEventOut> basEventOuts = new ArrayList<>(dataPartyCount);
        // 如果没有数据,则返回空对象,坚决不返回null
        if (dataPartyCount != 0) {
            for (int i = 0; i < dataPartyCount; i++) {
                DataParty dataParty = dataParties.get(i);
                Integer dataType = dataParty.getMdType();
                if (dataType == null) {
                    logger.error("DataParty表中id为{}的数据md_type为null", dataParty.getId());
                    continue;
                } else {

                }
            }

        }

        return basEventOuts;
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


}
