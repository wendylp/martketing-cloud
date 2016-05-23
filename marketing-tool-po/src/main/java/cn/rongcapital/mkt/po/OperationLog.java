package cn.rongcapital.mkt.po;

import cn.rongcapital.mkt.po.base.BaseQuery;

import java.util.Date;

public class OperationLog extends BaseQuery{
    private Long id;

    private String user_id;

    private String operate_desc;

    private Date operate_time;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id == null ? null : user_id.trim();
    }

    public String getOperate_desc() {
        return operate_desc;
    }

    public void setOperate_desc(String operate_desc) {
        this.operate_desc = operate_desc == null ? null : operate_desc.trim();
    }

    public Date getOperate_time() {
        return operate_time;
    }

    public void setOperate_time(Date operate_time) {
        this.operate_time = operate_time;
    }
}
