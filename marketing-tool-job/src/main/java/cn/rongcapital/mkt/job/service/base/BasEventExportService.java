package cn.rongcapital.mkt.job.service.base;

import java.io.File;

public interface BasEventExportService {

    /**
     * @功能简述 : 从data_xxx表中导出数据给BAS
     * @author nianjun
     * @return File
     */
    public File exportData();
}
