package cn.rongcapital.mkt.po;

import cn.rongcapital.mkt.po.base.BaseQuery;

public class IllegalDataSummary extends BaseQuery{
    private Integer id;

    private String email_count;

    private String monile_count;

    private String need_merge_count;

    private String inspect_time;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail_count() {
        return email_count;
    }

    public void setEmail_count(String email_count) {
        this.email_count = email_count == null ? null : email_count.trim();
    }

    public String getMonile_count() {
        return monile_count;
    }

    public void setMonile_count(String monile_count) {
        this.monile_count = monile_count == null ? null : monile_count.trim();
    }

    public String getNeed_merge_count() {
        return need_merge_count;
    }

    public void setNeed_merge_count(String need_merge_count) {
        this.need_merge_count = need_merge_count == null ? null : need_merge_count.trim();
    }

    public String getInspect_time() {
        return inspect_time;
    }

    public void setInspect_time(String inspect_time) {
        this.inspect_time = inspect_time == null ? null : inspect_time.trim();
    }
}
