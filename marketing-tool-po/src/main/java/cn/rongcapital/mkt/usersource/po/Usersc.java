package cn.rongcapital.mkt.usersource.po;

import java.io.Serializable;

public class Usersc implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name; //用户来源
	
	private String description;//描述
	
	private String primaryClassification;//一级分类
	
	private String twoLevelClassification; //二级分类
	
	private String threeLevelClassification; //三级分类
	
	private String remarks; //备注

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPrimaryClassification() {
		return primaryClassification;
	}

	public void setPrimaryClassification(String primaryClassification) {
		this.primaryClassification = primaryClassification;
	}

	public String getTwoLevelClassification() {
		return twoLevelClassification;
	}

	public void setTwoLevelClassification(String twoLevelClassification) {
		this.twoLevelClassification = twoLevelClassification;
	}

	public String getThreeLevelClassification() {
		return threeLevelClassification;
	}

	public void setThreeLevelClassification(String threeLevelClassification) {
		this.threeLevelClassification = threeLevelClassification;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
}
