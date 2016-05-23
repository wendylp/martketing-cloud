package cn.rongcapital.mkt.po;

import cn.rongcapital.mkt.po.base.BaseQuery;

import java.util.Date;

public class ImportDataHistory extends BaseQuery{
    private Long id;

    private Date import_start_time;

    private Date import_end_time;

    private Integer total_rows;

    private Integer legal_rows;

    private Byte success;

    private String source;

    private Integer email_count;

    private Integer contact_count;

    private Integer mobile_count;

    private Integer qq_count;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getImport_start_time() {
        return import_start_time;
    }

    public void setImport_start_time(Date import_start_time) {
        this.import_start_time = import_start_time;
    }

    public Date getImport_end_time() {
        return import_end_time;
    }

    public void setImport_end_time(Date import_end_time) {
        this.import_end_time = import_end_time;
    }

    public Integer getTotal_rows() {
        return total_rows;
    }

    public void setTotal_rows(Integer total_rows) {
        this.total_rows = total_rows;
    }

    public Integer getLegal_rows() {
        return legal_rows;
    }

    public void setLegal_rows(Integer legal_rows) {
        this.legal_rows = legal_rows;
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

    public Integer getEmail_count() {
        return email_count;
    }

    public void setEmail_count(Integer email_count) {
        this.email_count = email_count;
    }

    public Integer getContact_count() {
        return contact_count;
    }

    public void setContact_count(Integer contact_count) {
        this.contact_count = contact_count;
    }

    public Integer getMobile_count() {
        return mobile_count;
    }

    public void setMobile_count(Integer mobile_count) {
        this.mobile_count = mobile_count;
    }

    public Integer getQq_count() {
        return qq_count;
    }

    public void setQq_count(Integer qq_count) {
        this.qq_count = qq_count;
    }
}
