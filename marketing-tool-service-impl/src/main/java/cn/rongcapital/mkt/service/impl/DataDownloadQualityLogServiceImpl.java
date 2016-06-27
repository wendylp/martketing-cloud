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
import cn.rongcapital.mkt.dao.ImportDataModifyLogDao;
import cn.rongcapital.mkt.po.ImportDataModifyLog;
import cn.rongcapital.mkt.service.DataDownloadQualityLogService;
import cn.rongcapital.mkt.vo.BaseOutput;

@Service
public class DataDownloadQualityLogServiceImpl implements DataDownloadQualityLogService {

    @Autowired
    private ImportDataModifyLogDao importDataModifyLogDao;

    @Override
    public BaseOutput downloadQualityLog(Long importDataId) {

        ImportDataModifyLog paramImportDataModifyLog = new ImportDataModifyLog();
        paramImportDataModifyLog.setImportDataId(importDataId);

        List<ImportDataModifyLog> importDataModifyLogs = importDataModifyLogDao.selectList(paramImportDataModifyLog);

        File file = FileUtil.generateFileforDownload(importDataModifyLogs,
                        FileNameEnum.IMPORT_DATA_MODIFY_LOG.getDetailName());
        BaseOutput baseOutput = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
                        ApiConstant.INT_ZERO, null);
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("download_file_name", file.getName());

        baseOutput.getData().add(resultMap);

        return baseOutput;
    }

}
