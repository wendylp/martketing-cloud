package cn.rongcapital.mkt.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.enums.DataTypeEnum;
import cn.rongcapital.mkt.common.util.ReflectionUtil;
import cn.rongcapital.mkt.dao.DataArchPointDao;
import cn.rongcapital.mkt.dao.DataCustomerTagsDao;
import cn.rongcapital.mkt.dao.DataLoginDao;
import cn.rongcapital.mkt.dao.DataMemberDao;
import cn.rongcapital.mkt.dao.DataPartyDao;
import cn.rongcapital.mkt.dao.DataPaymentDao;
import cn.rongcapital.mkt.dao.DataPopulationDao;
import cn.rongcapital.mkt.dao.DataShoppingDao;
import cn.rongcapital.mkt.dao.ImportTemplateDao;
import cn.rongcapital.mkt.dao.base.BaseDataFilterDao;
import cn.rongcapital.mkt.po.DataArchPoint;
import cn.rongcapital.mkt.po.DataCustomerTags;
import cn.rongcapital.mkt.po.DataLogin;
import cn.rongcapital.mkt.po.DataMember;
import cn.rongcapital.mkt.po.DataParty;
import cn.rongcapital.mkt.po.DataPayment;
import cn.rongcapital.mkt.po.DataPopulation;
import cn.rongcapital.mkt.po.DataShopping;
import cn.rongcapital.mkt.po.ImportTemplate;
import cn.rongcapital.mkt.po.base.BaseQuery;
import cn.rongcapital.mkt.service.DataGetFilterAudiencesService;

@Service
public class DataGetFilterAudiencesServiceImpl implements DataGetFilterAudiencesService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ImportTemplateDao importTemplateDao;

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
    public <T extends BaseQuery> List<Map<String, Object>> getFilterAudiences(String method, String userToken,
                    String ver, Integer index, Integer size, Integer dataType, List<Integer> taskIdList,
                    List<Integer> contactIds) {

        // 这代码写的太2了
        if (dataType == DataTypeEnum.PARTY.getCode()) {
            DataParty paramObj = new DataParty(index, size);
            return getData(dataType, taskIdList, contactIds, paramObj, dataPartyDao);
        } else if (dataType == DataTypeEnum.POPULATION.getCode()) {
            DataPopulation paramObj = new DataPopulation(index, size);
            return getData(dataType, taskIdList, contactIds, paramObj, dataPopulationDao);
        } else if (dataType == DataTypeEnum.CUSTOMER_TAGS.getCode()) {
            DataCustomerTags paramObj = new DataCustomerTags(index, size);
            return getData(dataType, taskIdList, contactIds, paramObj, dataCustomerTagsDao);
        } else if (dataType == DataTypeEnum.ARCH_POINT.getCode()) {
            DataArchPoint paramObj = new DataArchPoint(index, size);
            return getData(dataType, taskIdList, contactIds, paramObj, dataArchPointDao);
        } else if (dataType == DataTypeEnum.MEMBER.getCode()) {
            DataMember paramObj = new DataMember(index, size);
            return getData(dataType, taskIdList, contactIds, paramObj, dataMemberDao);
        } else if (dataType == DataTypeEnum.LOGIN.getCode()) {
            DataLogin paramObj = new DataLogin(index, size);
            return getData(dataType, taskIdList, contactIds, paramObj, dataLoginDao);
        } else if (dataType == DataTypeEnum.PAYMENT.getCode()) {
            DataPayment paramObj = new DataPayment(index, size);
            return getData(dataType, taskIdList, contactIds, paramObj, dataPaymentDao);
        } else if (dataType == DataTypeEnum.SHOPPING.getCode()) {
            DataShopping paramObj = new DataShopping(index, size);
            return getData(dataType, taskIdList, contactIds, paramObj, dataShoppingDao);
        } else {
            logger.error("传入错误的data type : {}", dataType);
        }

        return null;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private <T extends BaseQuery, D extends BaseDataFilterDao> List<Map<String, Object>> getData(Integer mdType,
                    List<Integer> taskIdList, List<Integer> contactIdList, T paramObj, D dao) {

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("startIndex", paramObj.getStartIndex());
        paramMap.put("pageSize", paramObj.getPageSize());
        paramMap.put("batchIdList", taskIdList);
        paramMap.put("contactIdList", contactIdList);

        List<T> dataList = dao.selectByBatchId(paramMap);
        List<Map<String, Object>> resultList = new ArrayList<>();
        if (dataList != null && !dataList.isEmpty()) {
            ImportTemplate paramImportTemplate = new ImportTemplate();
            paramImportTemplate.setSelected(Boolean.TRUE);
            paramImportTemplate.setTemplType(mdType);

            List<ImportTemplate> importTemplateList = importTemplateDao.selectSelectedTemplateList(paramImportTemplate);

            for (T tempT : dataList) {
                Map<String, Object> map = new HashMap<>();
                for (ImportTemplate importTemplate : importTemplateList) {
                    Object value = ReflectionUtil.getObjectPropertyByName(tempT,
                                    ReflectionUtil.recoverFieldName(importTemplate.getFieldCode()));
                    if (value != null && value.getClass().getSimpleName().equals(Date.class.getSimpleName())) {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        value = simpleDateFormat.format((Date) value);
                    }
                    map.put(importTemplate.getFieldCode(), value);
                }

                resultList.add(map);
            }
        }

        return resultList;
    }
}
