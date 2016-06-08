package cn.rongcapital.mkt.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.enums.DataTypeEnum;
import cn.rongcapital.mkt.common.util.ReflectionUtil;
import cn.rongcapital.mkt.dao.DataAppDao;
import cn.rongcapital.mkt.dao.DataCrmDao;
import cn.rongcapital.mkt.dao.DataEshopDao;
import cn.rongcapital.mkt.dao.DataPartyDao;
import cn.rongcapital.mkt.dao.DataPersonalDao;
import cn.rongcapital.mkt.dao.DataPosDao;
import cn.rongcapital.mkt.dao.DataPublicDao;
import cn.rongcapital.mkt.dao.DataTmallDao;
import cn.rongcapital.mkt.dao.ImportTemplateDao;
import cn.rongcapital.mkt.dao.base.BaseDao;
import cn.rongcapital.mkt.po.DataApp;
import cn.rongcapital.mkt.po.DataCrm;
import cn.rongcapital.mkt.po.DataEshop;
import cn.rongcapital.mkt.po.DataParty;
import cn.rongcapital.mkt.po.DataPersonal;
import cn.rongcapital.mkt.po.DataPos;
import cn.rongcapital.mkt.po.DataPublic;
import cn.rongcapital.mkt.po.DataTmall;
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
    private DataAppDao dataAppDao;

    @Autowired
    private DataPosDao dataPosDao;

    @Autowired
    private DataPublicDao dataPublicDao;

    @Autowired
    private DataPersonalDao dataPersonalDao;

    @Autowired
    private DataEshopDao dataEshopDao;

    @Autowired
    private DataCrmDao dataCrmDao;

    @Autowired
    private DataTmallDao dataTmallDao;

    @Override
    public <T extends BaseQuery> List<Map<String, Object>> getFilterAudiences(String method, String userToken,
                    String ver, Integer index, Integer size, Integer dataType, List<Integer> taskIdList) {

        // 这代码写的太2了
        if (dataType == DataTypeEnum.PARTY.getCode()) {
            DataParty paramObj = new DataParty();
            paramObj.setDeleted(Boolean.FALSE);
            assignPagingValue(paramObj, index, size);
            return getData(dataType, taskIdList, paramObj, dataPartyDao);
        } else if (dataType == DataTypeEnum.APP.getCode()) {
            DataApp paramObj = new DataApp();
            paramObj.setDeleted(Boolean.FALSE);
            assignPagingValue(paramObj, index, size);
            return getData(dataType, taskIdList, paramObj, dataAppDao);
        } else if (dataType == DataTypeEnum.POS.getCode()) {
            DataPos paramObj = new DataPos();
            paramObj.setDeleted(Boolean.FALSE);
            assignPagingValue(paramObj, index, size);
            return getData(dataType, taskIdList, paramObj, dataPosDao);
        } else if (dataType == DataTypeEnum.PUBLIC.getCode()) {
            DataPublic paramObj = new DataPublic();
            paramObj.setDeleted(Boolean.FALSE);
            assignPagingValue(paramObj, index, size);
            return getData(dataType, taskIdList, paramObj, dataPublicDao);
        } else if (dataType == DataTypeEnum.PERSONAL.getCode()) {
            DataPersonal paramObj = new DataPersonal();
            paramObj.setDeleted(Boolean.FALSE);
            assignPagingValue(paramObj, index, size);
            return getData(dataType, taskIdList, paramObj, dataPersonalDao);
        } else if (dataType == DataTypeEnum.ESHOP.getCode()) {
            DataEshop paramObj = new DataEshop();
            paramObj.setDeleted(Boolean.FALSE);
            assignPagingValue(paramObj, index, size);
            return getData(dataType, taskIdList, paramObj, dataEshopDao);
        } else if (dataType == DataTypeEnum.CRM.getCode()) {
            DataCrm paramObj = new DataCrm();
            paramObj.setDeleted(Boolean.FALSE);
            assignPagingValue(paramObj, index, size);
            return getData(dataType, taskIdList, paramObj, dataCrmDao);
        } else if (dataType == DataTypeEnum.TMALL.getCode()) {
            DataTmall paramObj = new DataTmall();
            paramObj.setDeleted(Boolean.FALSE);
            assignPagingValue(paramObj, index, size);
            return getData(dataType, taskIdList, paramObj, dataTmallDao);
        } else {
            logger.error("传入错误的data type : {}", dataType);
        }

        return null;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private <T extends BaseQuery, D extends BaseDao> List<Map<String, Object>> getData(Integer mdType,
                    List<Integer> taskIdList, T paramObj, D dao) {

        List<T> dataList = dao.selectList(paramObj);
        List<Map<String, Object>> resultList = new ArrayList<>();
        if (dataList != null && !dataList.isEmpty()) {
            ImportTemplate paramImportTemplate = new ImportTemplate();
            paramImportTemplate.setSelected(Boolean.TRUE);
            paramImportTemplate.setTemplType(mdType);

            List<ImportTemplate> importTemplateList = importTemplateDao.selectList(paramImportTemplate);

            for (T tempT : dataList) {
                Map<String, Object> map = new HashMap<>();
                for (ImportTemplate importTemplate : importTemplateList) {
                    if (importTemplate.getSelected()) {
                        ReflectionUtil.getObjectPropertyByName(tempT,
                                        ReflectionUtil.recoverFieldName(importTemplate.getFieldCode()));
                    }
                }

                resultList.add(map);
            }
        }

        return resultList;
    }

    public <T extends BaseQuery> void assignPagingValue(T paramObject, Integer index, Integer size) {
        paramObject.setStartIndex(index);
        paramObject.setPageSize(size);
    }

}
