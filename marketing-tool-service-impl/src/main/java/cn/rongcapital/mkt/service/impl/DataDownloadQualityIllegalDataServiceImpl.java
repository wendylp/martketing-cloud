package cn.rongcapital.mkt.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.enums.DataTypeEnum;
import cn.rongcapital.mkt.common.enums.FileNameEnum;
import cn.rongcapital.mkt.common.util.FileUtil;
import cn.rongcapital.mkt.dao.DataArchPointDao;
import cn.rongcapital.mkt.dao.DataCustomerTagsDao;
import cn.rongcapital.mkt.dao.DataLoginDao;
import cn.rongcapital.mkt.dao.DataMemberDao;
import cn.rongcapital.mkt.dao.DataPartyDao;
import cn.rongcapital.mkt.dao.DataPaymentDao;
import cn.rongcapital.mkt.dao.DataPopulationDao;
import cn.rongcapital.mkt.dao.DataShoppingDao;
import cn.rongcapital.mkt.dao.IllegalDataDao;
import cn.rongcapital.mkt.dao.ImportTemplateDao;
import cn.rongcapital.mkt.po.IllegalData;
import cn.rongcapital.mkt.po.ImportTemplate;
import cn.rongcapital.mkt.service.DataDownloadQualityIllegalDataService;
import cn.rongcapital.mkt.vo.BaseOutput;

@Service
@SuppressWarnings("unchecked")

public class DataDownloadQualityIllegalDataServiceImpl implements DataDownloadQualityIllegalDataService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IllegalDataDao illegalDataDao;

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
    public BaseOutput downloadIllegalData(Long batchId) {
        BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
                        ApiConstant.INT_ZERO, null);

        IllegalData paramIllegalData = new IllegalData();
        paramIllegalData.setBatchId(batchId);
        List<IllegalData> illegalDatas = illegalDataDao.selectList(paramIllegalData);
        if (CollectionUtils.isEmpty(illegalDatas)) {
            return result;
        }

        // 一个批次下肯定处理的是一个类型的数据
        int dataType = Integer.parseInt(illegalDatas.get(0).getType());
        List dataList = new ArrayList<>();
        List<Integer> idList = new ArrayList<>();
        for (IllegalData illegalData : illegalDatas) {
            idList.add(Integer.parseInt(illegalData.getOriginData()));
        }

        if (dataType == DataTypeEnum.PARTY.getCode()) {
            dataList = dataPartyDao.selectListByIdList(idList);
        } else if (dataType == DataTypeEnum.POPULATION.getCode()) {
            dataList = dataPopulationDao.selectListByIdList(idList);
        } else if (dataType == DataTypeEnum.CUSTOMER_TAGS.getCode()) {
            dataList = dataCustomerTagsDao.selectListByIdList(idList);
        } else if (dataType == DataTypeEnum.ARCH_POINT.getCode()) {
            dataList = dataArchPointDao.selectListByIdList(idList);
        } else if (dataType == DataTypeEnum.MEMBER.getCode()) {
            dataList = dataMemberDao.selectListByIdList(idList);
        } else if (dataType == DataTypeEnum.LOGIN.getCode()) {
            dataList = dataLoginDao.selectListByIdList(idList);
        } else if (dataType == DataTypeEnum.PAYMENT.getCode()) {
            dataList = dataPaymentDao.selectListByIdList(idList);
        } else if (dataType == DataTypeEnum.SHOPPING.getCode()) {
            dataList = dataShoppingDao.selectListByIdList(idList);
        } else {
            logger.error("传入错误的data type : {}", dataType);
        }

        ImportTemplate paramImportTemplate = new ImportTemplate();
        paramImportTemplate.setTemplType(dataType);
        paramImportTemplate.setPageSize(0);
        List<ImportTemplate> importTemplates = importTemplateDao.selectList(paramImportTemplate);
        List<Map<String, String>> columnsMap = DataDownloadMainListServiceImpl.transferNameListtoMap(importTemplates);

        File file = FileUtil.generateFileforDownload(columnsMap, dataList, FileNameEnum.ILLEGAL_DATA.getDetailName());
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("download_file_name", file.getName());
        result.getData().add(resultMap);

        return result;
    }

}
