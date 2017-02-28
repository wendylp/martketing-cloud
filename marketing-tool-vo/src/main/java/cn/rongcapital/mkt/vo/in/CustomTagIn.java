package cn.rongcapital.mkt.vo.in;

import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.data.mongodb.core.mapping.Field;

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

	@Field("custom_tag_id")
	private String customTagId;		//自定义标签ID

	@Field("custom_tag_name")
	private String customTagName;	//自定义标签名称

	@Field("parent_id")
	private String parentId;	//分类ID
	
	@Field("parent_name")
	private String parentName;	//分类名称
	
	@Field("is_deleted")
    private Integer isDeleted;	//0-正常，1-过期
	
	@Field(value = "material_code")
	private String materialCode; // 物料CODE

	@Field(value = "material_type")
	private String materialType; // 物料类型
    
    public CustomTagIn() {
	}
    

	public CustomTagIn(String customTagId, String customTagName, String parentId, String parentName, Integer isDeleted,
			String materialCode, String materialType) {
		super();
		this.customTagId = customTagId;
		this.customTagName = customTagName;
		this.parentId = parentId;
		this.parentName = parentName;
		this.isDeleted = isDeleted;
		this.materialCode = materialCode;
		this.materialType = materialType;
	}





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

	public String getMaterialCode() {
		return materialCode;
	}

	public void setMaterialCode(String materialCode) {
		this.materialCode = materialCode;
	}

	public String getMaterialType() {
		return materialType;
	}

	public void setMaterialType(String materialType) {
		this.materialType = materialType;
	}

}
