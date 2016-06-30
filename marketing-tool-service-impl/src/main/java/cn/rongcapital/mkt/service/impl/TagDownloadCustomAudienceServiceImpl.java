package cn.rongcapital.mkt.service.impl;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.enums.FileNameEnum;
import cn.rongcapital.mkt.common.util.FileUtil;
import cn.rongcapital.mkt.dao.DataPartyDao;
import cn.rongcapital.mkt.dao.ImportTemplateDao;
import cn.rongcapital.mkt.po.DataParty;
import cn.rongcapital.mkt.po.ImportTemplate;
import cn.rongcapital.mkt.service.TagDownloadCustomAudienceService;
import cn.rongcapital.mkt.vo.BaseOutput;

@Service
public class TagDownloadCustomAudienceServiceImpl implements TagDownloadCustomAudienceService {

    @Autowired
    private DataPartyDao dataPartyDao;

    @Autowired
    private ImportTemplateDao importTemplateDao;

    @Override
    public BaseOutput downloadCustomAudience(Integer tagId) {
        BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
                        ApiConstant.INT_ZERO, null);
        Map<String, String> resultMap = new HashMap<>();
        Map<String, Integer> paramMap = new HashMap<>();
        paramMap.put("tagId", tagId);
        List<DataParty> dataList = dataPartyDao.selectCustomAudiencesByTagId(paramMap);
        ImportTemplate paramImportTemplate = new ImportTemplate();
        paramImportTemplate.setTemplType(FileNameEnum.PARTY.getCode());
        paramImportTemplate.setPageSize(0);
        List<ImportTemplate> importTemplates = importTemplateDao.selectList(paramImportTemplate);
        List<Map<String, String>> columnsMap = DataDownloadMainListServiceImpl.transferNameListtoMap(importTemplates);

        File file = FileUtil.generateFileforDownload(columnsMap, dataList,
                        FileNameEnum.CUSTOM_AUDIENCE.getDetailName());

        resultMap.put("download_file_name", file.getName());
        result.getData().add(resultMap);

        return result;
    }

}
