package cn.rongcapital.mkt.service.impl.vo;

import cn.rongcapital.mkt.po.ImportDataHistory;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.out.UploadFileAccordTemplateOut;

/**
 * Created by ethan on 16/7/5.
 */
public class UploadFileVO {

    private BaseOutput output;

    private UploadFileAccordTemplateOut parseOutput;

    private ImportDataHistory importDataHistory;

    private String fileName;

    public BaseOutput getOutput() {
        return output;
    }

    public void setOutput(BaseOutput output) {
        this.output = output;
    }

    public UploadFileAccordTemplateOut getParseOutput() {
        return parseOutput;
    }

    public void setParseOutput(UploadFileAccordTemplateOut parseOutput) {
        this.parseOutput = parseOutput;
    }

    public ImportDataHistory getImportDataHistory() {
        return importDataHistory;
    }

    public void setImportDataHistory(ImportDataHistory importDataHistory) {
        this.importDataHistory = importDataHistory;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
