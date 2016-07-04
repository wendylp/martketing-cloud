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
import cn.rongcapital.mkt.dao.IllegalDataDao;
import cn.rongcapital.mkt.dao.ImportTemplateDao;
import cn.rongcapital.mkt.dao.OriginalDataArchPointDao;
import cn.rongcapital.mkt.dao.OriginalDataCustomerTagsDao;
import cn.rongcapital.mkt.dao.OriginalDataLoginDao;
import cn.rongcapital.mkt.dao.OriginalDataMemberDao;
import cn.rongcapital.mkt.dao.OriginalDataPaymentDao;
import cn.rongcapital.mkt.dao.OriginalDataPopulationDao;
import cn.rongcapital.mkt.dao.OriginalDataShoppingDao;
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
    private OriginalDataPopulationDao originalDataPopulationDao;

    @Autowired
    private OriginalDataCustomerTagsDao originalDataCustomerTagsDao;

    @Autowired
    private OriginalDataArchPointDao originalDataArchPointDao;

    @Autowired
    private OriginalDataMemberDao originalDataMemberDao;

    @Autowired
    private OriginalDataLoginDao originalDataLoginDao;

    @Autowired
    private OriginalDataPaymentDao originalDataPaymentDao;

    @Autowired
    private OriginalDataShoppingDao originalDataShoppingDao;

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

        if (dataType == DataTypeEnum.POPULATION.getCode()) {
            dataList = originalDataPopulationDao.selectListByIdList(idList);
        } else if (dataType == DataTypeEnum.CUSTOMER_TAGS.getCode()) {
            dataList = originalDataCustomerTagsDao.selectListByIdList(idList);
        } else if (dataType == DataTypeEnum.ARCH_POINT.getCode()) {
            dataList = originalDataArchPointDao.selectListByIdList(idList);
        } else if (dataType == DataTypeEnum.MEMBER.getCode()) {
            dataList = originalDataMemberDao.selectListByIdList(idList);
        } else if (dataType == DataTypeEnum.LOGIN.getCode()) {
            dataList = originalDataLoginDao.selectListByIdList(idList);
        } else if (dataType == DataTypeEnum.PAYMENT.getCode()) {
            dataList = originalDataPaymentDao.selectListByIdList(idList);
        } else if (dataType == DataTypeEnum.SHOPPING.getCode()) {
            dataList = originalDataShoppingDao.selectListByIdList(idList);
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
