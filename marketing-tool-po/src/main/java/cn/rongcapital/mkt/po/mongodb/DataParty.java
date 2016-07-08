package cn.rongcapital.mkt.po.mongodb;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "data_party")
public class DataParty implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	
	private Integer mid;
	
	@Field(value = "md_type")
	private Integer mdType;
	
	private String name;
	
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
	
	private Integer sex;
	
    private String gender;

    private Date birthday;

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

    private Date updateTime;

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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
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

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
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

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}
	
}
