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

    private Integer emailCount;

    private Integer contactCount;

    private Integer mobileCount;

    private Integer qqCount;

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

    public Integer getEmailCount() {
        return emailCount;
    }

    public void setEmailCount(Integer emailCount) {
        this.emailCount = emailCount;
    }

    public Integer getContactCount() {
        return contactCount;
    }

    public void setContactCount(Integer contactCount) {
        this.contactCount = contactCount;
    }

    public Integer getMobileCount() {
        return mobileCount;
    }

    public void setMobileCount(Integer mobileCount) {
        this.mobileCount = mobileCount;
    }

    public Integer getQqCount() {
        return qqCount;
    }

    public void setQqCount(Integer qqCount) {
        this.qqCount = qqCount;
    }
}
