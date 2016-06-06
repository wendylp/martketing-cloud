package cn.rongcapital.mkt.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
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
import cn.rongcapital.mkt.po.DataApp;
import cn.rongcapital.mkt.po.DataCrm;
import cn.rongcapital.mkt.po.DataEshop;
import cn.rongcapital.mkt.po.DataParty;
import cn.rongcapital.mkt.po.DataPersonal;
import cn.rongcapital.mkt.po.DataPos;
import cn.rongcapital.mkt.po.DataPublic;
import cn.rongcapital.mkt.po.DataTmall;
import cn.rongcapital.mkt.po.ImportTemplate;
import cn.rongcapital.mkt.service.DataGetMainListService;
import cn.rongcapital.mkt.vo.BaseOutput;

@Service
public class DataGetMainListServiceImpl implements DataGetMainListService {

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
    public Object getMainList(String method, String userToken, Integer dataType, Integer index,
                    Integer size, String ver) {

        BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
                        ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO, null);

        // 这代码写的太2了
        if (dataType == DataTypeEnum.PARTY.getCode()) {
            assignPartyData(result, index, size, dataType);
        } else if (dataType == DataTypeEnum.APP.getCode()) {
            assignAppData(result, index, size, dataType);
        } else if (dataType == DataTypeEnum.POS.getCode()) {
            assignPosData(result, index, size, dataType);
        } else if (dataType == DataTypeEnum.PUBLIC.getCode()) {
            assignPublicData(result, index, size, dataType);
        } else if (dataType == DataTypeEnum.PERSONAL.getCode()) {
            assignPersonalData(result, index, size, dataType);
        } else if (dataType == DataTypeEnum.ESHOP.getCode()) {
            assignEshopData(result, index, size, dataType);
        } else if (dataType == DataTypeEnum.CRM.getCode()) {
            assignCrmData(result, index, size, dataType);
        } else if (dataType == DataTypeEnum.TMALL.getCode()) {
            assignTmallData(result, index, size, dataType);
        } else {
            logger.error("传入错误的data type : {}", dataType);
        }

        result.setTotal(result.getData().size());

        return Response.ok().entity(result).build();
    }

    private void assignPartyData(BaseOutput result, int index, int size, Integer mdType) {
        DataParty paramParty = new DataParty();
        paramParty.setStartIndex(index);
        paramParty.setPageSize(size);
        paramParty.setDeleted(Boolean.FALSE);

        List<DataParty> dataPartyList = dataPartyDao.selectList(paramParty);
        if (dataPartyList != null && !dataPartyList.isEmpty()) {
            ImportTemplate paramImportTemplate = new ImportTemplate();
            paramImportTemplate.setSelected(Boolean.TRUE);
            paramImportTemplate.setTemplType(mdType);

            List<ImportTemplate> importTemplateList =
                            importTemplateDao.selectList(paramImportTemplate);

            for (DataParty dataParty : dataPartyList) {
                Map<String, Object> map = new HashMap<>();
                map.put("data_id", dataParty.getId());
                for (ImportTemplate importTemplate : importTemplateList) {
                    if (importTemplate.getSelected()) {
                        ReflectionUtil.getObjectPropertyByName(dataParty, ReflectionUtil
                                        .recoverFieldName(importTemplate.getFieldCode()));
                    }
                }

                result.getData().add(map);
            }
        }
    }

    private void assignAppData(BaseOutput result, int index, int size, Integer mdType) {
        DataApp paramDataApp = new DataApp();
        paramDataApp.setStartIndex(index);
        paramDataApp.setPageSize(size);
        paramDataApp.setDeleted(Boolean.FALSE);

        List<DataApp> dataAppList = dataAppDao.selectList(paramDataApp);
        if (dataAppList != null && !dataAppList.isEmpty()) {
            ImportTemplate paramImportTemplate = new ImportTemplate();
            paramImportTemplate.setSelected(Boolean.TRUE);
            paramImportTemplate.setTemplType(mdType);

            List<ImportTemplate> importTemplateList =
                            importTemplateDao.selectList(paramImportTemplate);

            for (DataApp dataApp : dataAppList) {
                Map<String, Object> map = new HashMap<>();
                map.put("data_id", dataApp.getId());
                for (ImportTemplate importTemplate : importTemplateList) {
                    if (importTemplate.getSelected()) {
                        ReflectionUtil.getObjectPropertyByName(dataApp, ReflectionUtil
                                        .recoverFieldName(importTemplate.getFieldCode()));
                    }
                }

                result.getData().add(map);
            }
        }
    }

    private void assignPosData(BaseOutput result, int index, int size, Integer mdType) {
        DataPos paramDataPos = new DataPos();
        paramDataPos.setStartIndex(index);
        paramDataPos.setPageSize(size);
        paramDataPos.setDeleted(Boolean.FALSE);

        List<DataPos> dataPosList = dataPosDao.selectList(paramDataPos);

        if (dataPosList != null && !dataPosList.isEmpty()) {
            ImportTemplate paramImportTemplate = new ImportTemplate();
            paramImportTemplate.setSelected(Boolean.TRUE);
            paramImportTemplate.setTemplType(mdType);

            List<ImportTemplate> importTemplateList =
                            importTemplateDao.selectList(paramImportTemplate);

            for (DataPos dataPos : dataPosList) {
                Map<String, Object> map = new HashMap<>();
                map.put("data_id", dataPos.getId());
                for (ImportTemplate importTemplate : importTemplateList) {
                    if (importTemplate.getSelected()) {
                        ReflectionUtil.getObjectPropertyByName(dataPos, ReflectionUtil
                                        .recoverFieldName(importTemplate.getFieldCode()));
                    }
                }

                result.getData().add(map);
            }
        }
    }

    private void assignPublicData(BaseOutput result, int index, int size, Integer mdType) {
        DataPublic paramDataPublic = new DataPublic();
        paramDataPublic.setStartIndex(index);
        paramDataPublic.setPageSize(size);
        paramDataPublic.setDeleted(Boolean.FALSE);

        List<DataPublic> dataPublicList = dataPublicDao.selectList(paramDataPublic);

        if (dataPublicList != null && !dataPublicList.isEmpty()) {
            ImportTemplate paramImportTemplate = new ImportTemplate();
            paramImportTemplate.setSelected(Boolean.TRUE);
            paramImportTemplate.setTemplType(mdType);

            List<ImportTemplate> importTemplateList =
                            importTemplateDao.selectList(paramImportTemplate);

            for (DataPublic dataPublic : dataPublicList) {
                Map<String, Object> map = new HashMap<>();
                map.put("data_id", dataPublic.getId());
                for (ImportTemplate importTemplate : importTemplateList) {
                    if (importTemplate.getSelected()) {
                        ReflectionUtil.getObjectPropertyByName(dataPublic, ReflectionUtil
                                        .recoverFieldName(importTemplate.getFieldCode()));
                    }
                }

                result.getData().add(map);
            }
        }
    }

    private void assignPersonalData(BaseOutput result, int index, int size, Integer mdType) {
        DataPersonal paramDataPersonal = new DataPersonal();
        paramDataPersonal.setStartIndex(index);
        paramDataPersonal.setPageSize(size);
        paramDataPersonal.setDeleted(Boolean.FALSE);

        List<DataPersonal> dataPersonalList = dataPersonalDao.selectList(paramDataPersonal);

        if (dataPersonalList != null && !dataPersonalList.isEmpty()) {
            ImportTemplate paramImportTemplate = new ImportTemplate();
            paramImportTemplate.setSelected(Boolean.TRUE);
            paramImportTemplate.setTemplType(mdType);

            List<ImportTemplate> importTemplateList =
                            importTemplateDao.selectList(paramImportTemplate);

            for (DataPersonal dataPersonal : dataPersonalList) {
                Map<String, Object> map = new HashMap<>();
                map.put("data_id", dataPersonal.getId());
                for (ImportTemplate importTemplate : importTemplateList) {
                    if (importTemplate.getSelected()) {
                        ReflectionUtil.getObjectPropertyByName(dataPersonal, ReflectionUtil
                                        .recoverFieldName(importTemplate.getFieldCode()));
                    }
                }

                result.getData().add(map);
            }
        }
    }

    private void assignEshopData(BaseOutput result, int index, int size, Integer mdType) {
        DataEshop paramDataEshop = new DataEshop();
        paramDataEshop.setStartIndex(index);
        paramDataEshop.setPageSize(size);
        paramDataEshop.setDeleted(Boolean.FALSE);

        List<DataEshop> dataEshopList = dataEshopDao.selectList(paramDataEshop);

        if (dataEshopList != null && !dataEshopList.isEmpty()) {
            ImportTemplate paramImportTemplate = new ImportTemplate();
            paramImportTemplate.setSelected(Boolean.TRUE);
            paramImportTemplate.setTemplType(mdType);

            List<ImportTemplate> importTemplateList =
                            importTemplateDao.selectList(paramImportTemplate);

            for (DataEshop dataEshop : dataEshopList) {
                Map<String, Object> map = new HashMap<>();
                map.put("data_id", dataEshop.getId());
                for (ImportTemplate importTemplate : importTemplateList) {
                    if (importTemplate.getSelected()) {
                        ReflectionUtil.getObjectPropertyByName(dataEshop, ReflectionUtil
                                        .recoverFieldName(importTemplate.getFieldCode()));
                    }
                }

                result.getData().add(map);
            }
        }
    }

    private void assignCrmData(BaseOutput result, int index, int size, Integer mdType) {
        DataCrm paramDataCrm = new DataCrm();
        paramDataCrm.setStartIndex(index);
        paramDataCrm.setPageSize(size);
        paramDataCrm.setDeleted(Boolean.FALSE);

        List<DataCrm> dataCrmList = dataCrmDao.selectList(paramDataCrm);

        if (dataCrmList != null && !dataCrmList.isEmpty()) {
            ImportTemplate paramImportTemplate = new ImportTemplate();
            paramImportTemplate.setSelected(Boolean.TRUE);
            paramImportTemplate.setTemplType(mdType);

            List<ImportTemplate> importTemplateList =
                            importTemplateDao.selectList(paramImportTemplate);

            for (DataCrm dataCrm : dataCrmList) {
                Map<String, Object> map = new HashMap<>();
                map.put("data_id", dataCrm.getId());
                for (ImportTemplate importTemplate : importTemplateList) {
                    if (importTemplate.getSelected()) {
                        ReflectionUtil.getObjectPropertyByName(dataCrm, ReflectionUtil
                                        .recoverFieldName(importTemplate.getFieldCode()));
                    }
                }

                result.getData().add(map);
            }
        }
    }

    private void assignTmallData(BaseOutput result, int index, int size, Integer mdType) {
        DataTmall paramDataTmall = new DataTmall();
        paramDataTmall.setStartIndex(index);
        paramDataTmall.setPageSize(size);
        paramDataTmall.setDeleted(Boolean.FALSE);

        List<DataTmall> dataTmallList = dataTmallDao.selectList(paramDataTmall);

        if (dataTmallList != null && !dataTmallList.isEmpty()) {
            ImportTemplate paramImportTemplate = new ImportTemplate();
            paramImportTemplate.setSelected(Boolean.TRUE);
            paramImportTemplate.setTemplType(mdType);

            List<ImportTemplate> importTemplateList =
                            importTemplateDao.selectList(paramImportTemplate);

            for (DataTmall dataTmall : dataTmallList) {
                Map<String, Object> map = new HashMap<>();
                map.put("data_id", dataTmall.getId());
                for (ImportTemplate importTemplate : importTemplateList) {
                    if (importTemplate.getSelected()) {
                        ReflectionUtil.getObjectPropertyByName(dataTmall, ReflectionUtil
                                        .recoverFieldName(importTemplate.getFieldCode()));
                    }
                }

                result.getData().add(map);
            }
        }
    }

}
