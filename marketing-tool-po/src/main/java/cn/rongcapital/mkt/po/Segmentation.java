package cn.rongcapital.mkt.po;

import cn.rongcapital.mkt.po.base.BaseQuery;

import java.util.Date;

public class Segmentation extends BaseQuery{
    private Long id;

    private Integer audience_count;

    private Date exec_time;

    private String tage_id_list;

    private Byte type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAudience_count() {
        return audience_count;
    }

    public void setAudience_count(Integer audience_count) {
        this.audience_count = audience_count;
    }

    public Date getExec_time() {
        return exec_time;
    }

    public void setExec_time(Date exec_time) {
        this.exec_time = exec_time;
    }

    public String getTage_id_list() {
        return tage_id_list;
    }

    public void setTage_id_list(String tage_id_list) {
        this.tage_id_list = tage_id_list == null ? null : tage_id_list.trim();
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }
}
