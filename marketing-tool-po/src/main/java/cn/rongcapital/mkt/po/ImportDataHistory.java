package cn.rongcapital.mkt.po;

import cn.rongcapital.mkt.po.base.BaseQuery;

import java.util.Date;

public class ImportDataHistory extends BaseQuery {
    private Long id;

    private Date importStartTime;

    private Date importEndTime;

    private Integer totalRows;

    private Integer legalRows;

    private Byte success;

    private String source;

    private String sourceFilename;

    private Integer emailRows;

    private Integer mobileRows;

    private Integer duplicateRows;

    private Integer illegalRows;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getImportStartTime() {
        return importStartTime;
    }

    public void setImportStartTime(Date importStartTime) {
        this.importStartTime = importStartTime;
    }

    public Date getImportEndTime() {
        return importEndTime;
    }

    public void setImportEndTime(Date importEndTime) {
        this.importEndTime = importEndTime;
    }

    public Integer getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(Integer totalRows) {
        this.totalRows = totalRows;
    }

    public Integer getLegalRows() {
        return legalRows;
    }

    public void setLegalRows(Integer legalRows) {
        this.legalRows = legalRows;
    }

    public Byte getSuccess() {
        return success;
    }

    public void setSuccess(Byte success) {
        this.success = success;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    public String getSourceFilename() {
        return sourceFilename;
    }

    public void setSourceFilename(String sourceFilename) {
        this.sourceFilename = sourceFilename == null ? null : sourceFilename.trim();
    }

    public Integer getEmailRows() {
        return emailRows;
    }

    public void setEmailRows(Integer emailRows) {
        this.emailRows = emailRows;
    }

    public Integer getMobileRows() {
        return mobileRows;
    }

    public void setMobileRows(Integer mobileRows) {
        this.mobileRows = mobileRows;
    }

    public Integer getDuplicateRows() {
        return duplicateRows;
    }

    public void setDuplicateRows(Integer duplicateRows) {
        this.duplicateRows = duplicateRows;
    }

    public Integer getIllegalRows() {
        return illegalRows;
    }

    public void setIllegalRows(Integer illegalRows) {
        this.illegalRows = illegalRows;
    }
}
