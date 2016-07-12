package cn.rongcapital.mkt.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.enums.ContactwayEnum;
import cn.rongcapital.mkt.common.enums.DataTypeEnum;
import cn.rongcapital.mkt.common.util.GenderUtils;
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
import cn.rongcapital.mkt.dao.base.BaseDao;
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
import cn.rongcapital.mkt.service.impl.vo.MainDataVO;
import cn.rongcapital.mkt.vo.in.CustomizeViewCheckboxIn;
import cn.rongcapital.mkt.vo.out.DataGetMainListOut;

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
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public <T extends BaseQuery> Object getFilterAudiences(String method, String userToken, String ver, Integer index,
                    Integer size, Integer dataType, List<Integer> taskIdList, List<Integer> contactIds,
                    List<CustomizeViewCheckboxIn> customizeViews) {
        DataGetMainListOut result = new DataGetMainListOut(ApiErrorCode.SUCCESS.getCode(),
                        ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO, null);
        List<Map<String, Object>> columnList = new ArrayList<>();
        List<ImportTemplate> importTemplateList = null;
        Integer totalCount = 0;


        if (customizeViews != null && !customizeViews.isEmpty()) {
            for (int i = 0; i < customizeViews.size(); i++) {
                ImportTemplate paramImportTemplate = new ImportTemplate(index, size);
                paramImportTemplate.setSelected(customizeViews.get(i).getIsSelected());
                paramImportTemplate.setFieldName(customizeViews.get(i).getColName());
                paramImportTemplate.setTemplType(dataType);
                importTemplateDao.updateSelectedByTemplType(paramImportTemplate);
            }
        }

        ImportTemplate paramImportTemplate = new ImportTemplate(index, size);
        paramImportTemplate.setSelected(Boolean.TRUE);
        paramImportTemplate.setTemplType(dataType);
        importTemplateList = importTemplateDao.selectSelectedTemplateList(paramImportTemplate);

        if (importTemplateList != null && !importTemplateList.isEmpty()) {
            for (ImportTemplate importTemplate : importTemplateList) {
                Map<String, Object> map = new HashMap<>();
                map.put("col_id", importTemplate.getId());
                map.put("col_name", importTemplate.getFieldName());
                map.put("col_code", importTemplate.getFieldCode());
                if (importTemplate.getFieldCode().equalsIgnoreCase("gender")) {
                    map.put("col_code", "sex");
                }
                columnList.add(map);
                result.setMd_type(importTemplate.getTemplType());
            }
        }

        // 这代码写的太2了
        MainDataVO mainDataVO = null;
        if (dataType == DataTypeEnum.PARTY.getCode()) {
            DataParty paramObj = new DataParty(index, size);
            mainDataVO = getData(dataType, taskIdList, contactIds, paramObj, dataPartyDao);
        } else if (dataType == DataTypeEnum.POPULATION.getCode()) {
            DataPopulation paramObj = new DataPopulation(index, size);
            mainDataVO = getData(dataType, taskIdList, contactIds, paramObj, dataPopulationDao);
        } else if (dataType == DataTypeEnum.CUSTOMER_TAGS.getCode()) {
            DataCustomerTags paramObj = new DataCustomerTags(index, size);
            mainDataVO = getData(dataType, taskIdList, contactIds, paramObj, dataCustomerTagsDao);
        } else if (dataType == DataTypeEnum.ARCH_POINT.getCode()) {
            DataArchPoint paramObj = new DataArchPoint(index, size);
            mainDataVO = getData(dataType, taskIdList, contactIds, paramObj, dataArchPointDao);
        } else if (dataType == DataTypeEnum.MEMBER.getCode()) {
            DataMember paramObj = new DataMember(index, size);
            mainDataVO = getData(dataType, taskIdList, contactIds, paramObj, dataMemberDao);
        } else if (dataType == DataTypeEnum.LOGIN.getCode()) {
            DataLogin paramObj = new DataLogin(index, size);
            mainDataVO = getData(dataType, taskIdList, contactIds, paramObj, dataLoginDao);
        } else if (dataType == DataTypeEnum.PAYMENT.getCode()) {
            DataPayment paramObj = new DataPayment(index, size);
            mainDataVO = getData(dataType, taskIdList, contactIds, paramObj, dataPaymentDao);
        } else if (dataType == DataTypeEnum.SHOPPING.getCode()) {
            DataShopping paramObj = new DataShopping(index, size);
            mainDataVO = getData(dataType, taskIdList, contactIds, paramObj, dataShoppingDao);
        } else {
            logger.error("传入错误的data type : {}", dataType);
        }

        result.getData().addAll(mainDataVO.getResultList());
        result.setTotal(mainDataVO.getTotalCount());
        result.setTotalCount(mainDataVO.getTotalCount());
        result.getColNames().addAll(columnList);
        return Response.ok().entity(result).build();
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private <T extends BaseQuery, D extends BaseDataFilterDao> MainDataVO getData(Integer mdType,
            List<Integer> taskIdList, List<Integer> contactIdList, T paramObj, D dao) {

        MainDataVO mainDataVO = new MainDataVO();
        Map<String, Object> paramMap = new HashMap<>();
        contactIdList = filterContactId(contactIdList, dao);
        if (CollectionUtils.isEmpty(taskIdList)) {
            taskIdList = null;
        }
        paramMap.put("startIndex", paramObj.getStartIndex());
        paramMap.put("pageSize", paramObj.getPageSize());
        paramMap.put("batchIdList", taskIdList);
        paramMap.put("contactIdList", contactIdList);

        List<T> dataList = dao.selectByBatchId(paramMap);
        paramMap.remove("startIndex");
        paramMap.remove("pageSize");
        Integer totalCount = Integer.valueOf(dao.selectByBatchId(paramMap).size());

        List<Map<String, Object>> resultList = new ArrayList<>();
        if (dataList != null && !dataList.isEmpty()) {
            ImportTemplate paramImportTemplate = new ImportTemplate();
            paramImportTemplate.setSelected(Boolean.TRUE);
            paramImportTemplate.setTemplType(mdType);

            List<ImportTemplate> importTemplateList = importTemplateDao.selectSelectedTemplateList(paramImportTemplate);

            for (T tempT : dataList) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", ReflectionUtil.getObjectPropertyByName(tempT, "id"));
                for (ImportTemplate importTemplate : importTemplateList) {
                    Object value = ReflectionUtil.getObjectPropertyByName(tempT,
                                    ReflectionUtil.recoverFieldName(importTemplate.getFieldCode()));
                    if (value != null && value.getClass().getSimpleName().equals(Date.class.getSimpleName())) {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        value = simpleDateFormat.format((Date) value);
                    }
                    map.put(importTemplate.getFieldCode(), value);
                }

                if (mdType == 0 || mdType == 1) {
                    Object sexByte = ReflectionUtil.getObjectPropertyByName(tempT, "gender");
                    if (sexByte != null) {
                        String sex = GenderUtils.byteToChar(Byte.valueOf(sexByte + ""));
                        map.put("sex", sex);
                    }
                }
                
                resultList.add(map);
            }
        }

        mainDataVO.setResultList(resultList);
        mainDataVO.setTotalCount(totalCount);
        return mainDataVO;
    }

    private <D extends BaseDataFilterDao<?>> List<Integer> filterContactId(List<Integer> contactIdList, D dao) {
        List<Integer> resultList = new ArrayList<>();

        if (CollectionUtils.isEmpty(contactIdList)) {
            return resultList;
        }

        for (Integer contactId : contactIdList) {
            String columnName = ContactwayEnum.getColumnNameById(contactId);
            if (columnName == null) {
                continue;
            } else {
                List<String> columnNameList = dao.selectColumns();
                for (String str : columnNameList) {
                    if (columnName.equalsIgnoreCase(str)) {
                        resultList.add(contactId);
                        break;
                    }
                }
            }
        }

        return resultList;
    }

    private <D extends BaseDao> int getTotalCount(D dao) {
        return dao.selectListCount(null);
    }
}
