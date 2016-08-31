package cn.rongcapital.mkt.po;

import java.util.Date;

public class Tag {
    private Integer id;

    private String name;

    private Byte status;

    private Date createTime;

    private Date updateTime;
<<<<<<< HEAD

    private String tagGroupId;
=======
    
    private String tagGroupId;
    
    
    public String getTagGroupId() {
		return tagGroupId;
	}
>>>>>>> branch 'dev' of http://gitlab.dataengine.com/songshitao/marketing-cloud.git

	public void setTagGroupId(String tagGroupId) {
		this.tagGroupId = tagGroupId;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getTagGroupId() {
        return tagGroupId;
    }

    public void setTagGroupId(String tagGroupId) {
        this.tagGroupId = tagGroupId == null ? null : tagGroupId.trim();
    }
}