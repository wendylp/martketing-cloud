package cn.rongcapital.mkt.po;

import java.util.Date;

import cn.rongcapital.mkt.po.base.BaseQuery;

public class SmsTemplet extends BaseQuery{
	
    private Integer id;

    private String code;

    private Byte channelType;

    private Byte type;

    private Byte auditStatus;

    private String auditor;

    private Byte status;

    private String creator;

    private Date createTime;

    private String updateUser;

    private Date updateTime;

    private String content;
    
    /**
     * 临时属性
     */
    private String createTimeStr;
    
    private String auditStatusStr;
    
    
    public SmsTemplet(){}


    public SmsTemplet(Byte channelType, Byte type, Byte auditStatus, String content) {
        super();
        this.channelType = channelType;
        this.type = type;
        this.auditStatus = auditStatus;
        this.content = content;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public Byte getChannelType() {
        return channelType;
    }

    public void setChannelType(Byte channelType) {
        this.channelType = channelType;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    public Byte getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Byte auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor == null ? null : auditor.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}


	public String getAuditStatusStr() {
		return auditStatusStr;
	}


	public void setAuditStatusStr(String auditStatusStr) {
		this.auditStatusStr = auditStatusStr;
	}
    
}