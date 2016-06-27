package cn.rongcapital.mkt.service.impl;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import cn.rongcapital.mkt.common.enums.FileNameEnum;
import cn.rongcapital.mkt.common.util.FileUtil;
import cn.rongcapital.mkt.dao.ImportDataModifyLogDao;
import cn.rongcapital.mkt.po.ImportDataModifyLog;
import cn.rongcapital.mkt.service.DataDownloadQualityLogService;

public class DataDownloadQualityLogServiceImpl implements DataDownloadQualityLogService {

    @Autowired
    private ImportDataModifyLogDao importDataModifyLogDao;

    @Override
    public File downloadQualityLog(Long importDataId) {

        ImportDataModifyLog paramImportDataModifyLog = new ImportDataModifyLog();
        paramImportDataModifyLog.setImportDataId(importDataId);

        List<ImportDataModifyLog> importDataModifyLogs = importDataModifyLogDao.selectList(paramImportDataModifyLog);

        File file = FileUtil.generateFileforDownload(importDataModifyLogs,
                        FileNameEnum.IMPORT_DATA_MODIFY_LOG.getDetailName());

        return null;
    }

}
