package cn.rongcapital.mkt.service.impl;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;

import cn.rongcapital.mkt.dao.ImportDataModifyLogDao;
import cn.rongcapital.mkt.po.ImportDataModifyLog;
import cn.rongcapital.mkt.service.DataDownloadQualityLogService;

public class DataDownloadQualityLogServiceImpl implements DataDownloadQualityLogService {

    @Autowired
    private ImportDataModifyLogDao importDataModifyLogDao;
    
    @Override
    public File downloadQualityLog(Integer importDataId) {
        
        ImportDataModifyLog importDataModifyLog = new ImportDataModifyLog();
        importDataModifyLog.setImportDataId(importDataId);
        
        return null;
    }

}
