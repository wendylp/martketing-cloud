package cn.rongcapital.mkt.po.mongodb;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.rongcapital.mkt.common.util.GenderUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * @author Administrator
 *
 */
@Document(collection = "data_party")
public class DataParty implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	
	private Integer mid;

	private Integer mdType;
	
	private String name;
	
	private String wxName;
	
	@Field(value = "mapping_keyid")
	private String mappingKeyid;
	
	@Field(value = "audience_list")
	private List<Audience> audienceList;
	
	@Field(value = "tag_list")
	private List<Tag> tagList;
	
	@Field(value = "wxCode")
	private String fansOpenId;
	
	@Field(value = "pubId")
	private String pubId;
	
	@Field(value = "subscribeTime")
	private String subscribeTime;

	private String mobile;
	
	private String maritalStatus;
	
	private String education;
	
	private String employment;
	
	private String sex;
	
    private Byte gender;

    private Date birthday;

    private String citizenship;

    private String provice;

    private String city;

    private String job;

    private BigDecimal monthlyIncome;

    private String memberLevel;

    private String memberPoints;

    private String source;

    private BigDecimal monthlyConsume;

    private Date lastLogin;

    private Byte status;

    private String batchId;

    private String updateTime;

	private Integer receiveCount;

	private Integer totalCount;
	
	private Date payLastTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getMid() {
		return mid;
	}

	public void setMid(Integer mid) {
		this.mid = mid;
	}

	public Integer getMdType() {
		return mdType;
	}

	public void setMdType(Integer mdType) {
		this.mdType = mdType;
	}

	public String getMappingKeyid() {
		return mappingKeyid;
	}

	public void setMappingKeyid(String mappingKeyid) {
		this.mappingKeyid = mappingKeyid;
	}

	public List<Audience> getAudienceList() {
		return audienceList;
	}

	public void setAudienceList(List<Audience> audienceList) {
		this.audienceList = audienceList;
	}

	public List<Tag> getTagList() {
		return tagList;
	}

	public void setTagList(List<Tag> tagList) {
		this.tagList = tagList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Byte getGender() {
		return gender;
	}

	public void setGender(Byte gender) {
		this.gender = gender;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getProvice() {
		return provice;
	}

	public void setProvice(String provice) {
		this.provice = provice;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public BigDecimal getMonthlyIncome() {
		return monthlyIncome;
	}

	public void setMonthlyIncome(BigDecimal monthlyIncome) {
		this.monthlyIncome = monthlyIncome;
	}

	public String getMemberLevel() {
		return memberLevel;
	}

	public void setMemberLevel(String memberLevel) {
		this.memberLevel = memberLevel;
	}

	public String getMemberPoints() {
		return memberPoints;
	}

	public void setMemberPoints(String memberPoints) {
		this.memberPoints = memberPoints;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public BigDecimal getMonthlyConsume() {
		return monthlyConsume;
	}

	public void setMonthlyConsume(BigDecimal monthlyConsume) {
		this.monthlyConsume = monthlyConsume;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getFansOpenId() {
		return fansOpenId;
	}

	public void setFansOpenId(String fansOpenId) {
		this.fansOpenId = fansOpenId;
	}

	public String getPubId() {
		return pubId;
	}

	public void setPubId(String pubId) {
		this.pubId = pubId;
	}

	public String getSubscribeTime() {
		return subscribeTime;
	}

	public void setSubscribeTime(String subscribeTime) {
		this.subscribeTime = subscribeTime;
	}

	public String getSex() {
		return GenderUtils.byteToChar(getGender());
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getEmployment() {
		return employment;
	}

	public void setEmployment(String employment) {
		this.employment = employment;
	}

	public String getWxName() {
		return wxName;
	}

	public void setWxName(String wxName) {
		this.wxName = wxName;
	}

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getCitizenship() {
        return citizenship;
    }

    public void setCitizenship(String citizenship) {
        this.citizenship = citizenship;
    }

	public Integer getReceiveCount() {
		return receiveCount;
	}

	public void setReceiveCount(Integer receiveCount) {
		this.receiveCount = receiveCount;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Date getPayLastTime() {
		return payLastTime;
	}

	public void setPayLastTime(Date payLastTime) {
		this.payLastTime = payLastTime;
	}
	
}
