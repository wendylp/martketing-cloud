package cn.rongcapital.mkt.po;

import cn.rongcapital.mkt.po.base.BaseQuery;

import java.util.Date;

public class AudienceList extends BaseQuery {
    private Integer id;

    private String audienceName;

    private String audienceRows;

    private String source;

    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAudienceName() {
        return audienceName;
    }

    public void setAudienceName(String audienceName) {
        this.audienceName = audienceName == null ? null : audienceName.trim();
    }

    public String getAudienceRows() {
        return audienceRows;
    }

    public void setAudienceRows(String audienceRows) {
        this.audienceRows = audienceRows == null ? null : audienceRows.trim();
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source == null ? null : source.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
