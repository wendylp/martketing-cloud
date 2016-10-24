package cn.rongcapital.mkt.po;

import java.util.Date;

import cn.rongcapital.mkt.po.base.BaseQuery;

public class SmsTaskHead extends BaseQuery{
    private Long id;

    private String smsTaskName;

    private Long smsTaskSignatureId;

    private Long smsTaskTemplateId;

    private String smsTaskTemplateContent;

    private Integer smsTaskSendType;

    private Integer smsTaskAppType;

    private Integer smsTaskStatus;

    private Byte status;

    private Date createTime;

    private Date updateTime;

    private Integer totalCoverNum =0;

    private Integer sendingSuccessNum =0;

    private Integer waitingNum =0;

    private Integer sendingFailNum =0;

    private Integer audienceGenerateStatus=1;
    
    /**
     * 临时属性
     */
    private String smsTaskStatusStr;
    
    private String smsTaskAppTypeStr;
    
    private String createTimeStr;
    
    private Integer totalCoverNumPer = 100;
    
    private Integer sendingSuccessNumPer;
    
    private Integer waitingNumPer = 100;
    
    private Integer sendingFailNumPer = 100;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSmsTaskName() {
        return smsTaskName;
    }

    public void setSmsTaskName(String smsTaskName) {
        this.smsTaskName = smsTaskName == null ? null : smsTaskName.trim();
    }

    public Long getSmsTaskSignatureId() {
        return smsTaskSignatureId;
    }

    public void setSmsTaskSignatureId(Long smsTaskSignatureId) {
        this.smsTaskSignatureId = smsTaskSignatureId;
    }

    public Long getSmsTaskTemplateId() {
        return smsTaskTemplateId;
    }

    public void setSmsTaskTemplateId(Long smsTaskTemplateId) {
        this.smsTaskTemplateId = smsTaskTemplateId;
    }

    public String getSmsTaskTemplateContent() {
        return smsTaskTemplateContent;
    }

    public void setSmsTaskTemplateContent(String smsTaskTemplateContent) {
        this.smsTaskTemplateContent = smsTaskTemplateContent == null ? null : smsTaskTemplateContent.trim();
    }

    public Integer getSmsTaskSendType() {
        return smsTaskSendType;
    }

    public void setSmsTaskSendType(Integer smsTaskSendType) {
        this.smsTaskSendType = smsTaskSendType;
    }

    public Integer getSmsTaskAppType() {
        return smsTaskAppType;
    }

    public void setSmsTaskAppType(Integer smsTaskAppType) {
        this.smsTaskAppType = smsTaskAppType;
    }

    public Integer getSmsTaskStatus() {
        return smsTaskStatus;
    }

    public void setSmsTaskStatus(Integer smsTaskStatus) {
        this.smsTaskStatus = smsTaskStatus;
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

    public Integer getTotalCoverNum() {
        return totalCoverNum;
    }

    public void setTotalCoverNum(Integer totalCoverNum) {
        this.totalCoverNum = totalCoverNum;
    }

    public Integer getSendingSuccessNum() {
        return sendingSuccessNum;
    }

    public void setSendingSuccessNum(Integer sendingSuccessNum) {
        this.sendingSuccessNum = sendingSuccessNum;
    }

    public Integer getWaitingNum() {
        return waitingNum;
    }

    public void setWaitingNum(Integer waitingNum) {
        this.waitingNum = waitingNum;
    }

    public Integer getSendingFailNum() {
        return sendingFailNum;
    }

    public void setSendingFailNum(Integer sendingFailNum) {
        this.sendingFailNum = sendingFailNum;
    }

    public Integer getAudienceGenerateStatus() {
        return audienceGenerateStatus;
    }

    public void setAudienceGenerateStatus(Integer audienceGenerateStatus) {
        this.audienceGenerateStatus = audienceGenerateStatus;
    }

	public String getSmsTaskStatusStr() {
		return smsTaskStatusStr;
	}

	public void setSmsTaskStatusStr(String smsTaskStatusStr) {
		this.smsTaskStatusStr = smsTaskStatusStr;
	}

	public String getSmsTaskAppTypeStr() {
		return smsTaskAppTypeStr;
	}

	public void setSmsTaskAppTypeStr(String smsTaskAppTypeStr) {
		this.smsTaskAppTypeStr = smsTaskAppTypeStr;
	}

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	public Integer getTotalCoverNumPer() {
		return totalCoverNumPer;
	}

	public void setTotalCoverNumPer(Integer totalCoverNumPer) {
		this.totalCoverNumPer = totalCoverNumPer;
	}

	public Integer getSendingSuccessNumPer() {
		return sendingSuccessNumPer;
	}

	public void setSendingSuccessNumPer(Integer sendingSuccessNumPer) {
		this.sendingSuccessNumPer = sendingSuccessNumPer;
	}

	public Integer getWaitingNumPer() {
		return waitingNumPer;
	}

	public void setWaitingNumPer(Integer waitingNumPer) {
		this.waitingNumPer = waitingNumPer;
	}

	public Integer getSendingFailNumPer() {
		return sendingFailNumPer;
	}

	public void setSendingFailNumPer(Integer sendingFailNumPer) {
		this.sendingFailNumPer = sendingFailNumPer;
	}
    
    
}