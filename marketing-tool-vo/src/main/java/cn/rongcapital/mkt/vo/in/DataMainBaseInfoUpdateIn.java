package cn.rongcapital.mkt.vo.in;

import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import cn.rongcapital.mkt.vo.BaseInput;

public class DataMainBaseInfoUpdateIn  extends BaseInput {
	@NotNull
	private Integer contactId;
	
	private String name;
	
	private Integer gender;
	
	private Integer age;
	
	private String mobile;
	
	private String email;
	
	private String homeAddress;
	
	private String workAddress;
	
	private String wechat;
	
	private String qq;
	
	private String weibo;
	
	private String childAmount;
	
	private String salary; 
	
	@NotEmpty
	private String userToken;

	@JsonProperty("contact_id")
	public Integer getContactId() {
		return contactId;
	}

	public void setContactId(Integer contactId) {
		this.contactId = contactId;
	}

	@JsonProperty("name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@JsonProperty("home_address")
	public String getHomeAddress() {
		return homeAddress;
	}

	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}

	@JsonProperty("work_address")
	public String getWorkAddress() {
		return workAddress;
	}

	public void setWorkAddress(String workAddress) {
		this.workAddress = workAddress;
	}

	public String getWechat() {
		return wechat;
	}

	public void setWechat(String wechat) {
		this.wechat = wechat;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getWeibo() {
		return weibo;
	}

	public void setWeibo(String weibo) {
		this.weibo = weibo;
	}

	@JsonProperty("child_amount")
	public String getChildAmount() {
		return childAmount;
	}

	public void setChildAmount(String childAmount) {
		this.childAmount = childAmount;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	@JsonProperty("user_token")
	public String getUserToken() {
		return userToken;
	}

	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}
}
