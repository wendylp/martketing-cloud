package cn.rongcapital.mkt.vo.in;

import org.codehaus.jackson.annotate.JsonProperty;

/*************************************************
 * @功能简述: 自定标签vo
 * @项目名称: marketing cloud
 * @see:
 * @author: 王伟强
 * @version: 0.0.1
 * @date: 2017/1/18
 * @复审人:
 *************************************************/
public class CustomTagIn {

	private String customTagId;		//自定义标签ID

	private String customTagName;	//自定义标签名称

	private String parentId;	//分类ID
	
	private String parentName;	//分类名称
	
    private Integer isDeleted;	//0-正常，1-过期

    @JsonProperty("custom_tag_id")
	public String getCustomTagId() {
		return customTagId;
	}

	public void setCustomTagId(String customTagId) {
		this.customTagId = customTagId;
	}

	@JsonProperty("custom_tag_name")
	public String getCustomTagName() {
		return customTagName;
	}

	public void setCustomTagName(String customTagName) {
		this.customTagName = customTagName;
	}

	@JsonProperty("parent_id")
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@JsonProperty("parent_name")
	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	
	@JsonProperty("is_deleted")
	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}
	

}
