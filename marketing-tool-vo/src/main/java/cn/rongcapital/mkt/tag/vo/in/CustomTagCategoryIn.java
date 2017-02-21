package cn.rongcapital.mkt.tag.vo.in;

import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import cn.rongcapital.mkt.vo.BaseInput;

/*************************************************
 * @功能及特点的描述简述: 创建自定义标签分类
 *
 * @see （与该类关联的类): CustomtagCreateService
 * @对应项目名称: MC系统
 * @author: 丛树林
 * @version: v1.7 @date(创建、开发日期): 2017-1-20 最后修改日期: 2017-1-20
 * @复审人: 丛树林
 *************************************************/
public class CustomTagCategoryIn extends BaseInput {

	@NotEmpty
	private String userToken = null;

	private String customTagCategoryId = null;

	@NotEmpty
	private String customTagCategoryName = null;

	@JsonProperty("user_token")
	public String getUserToken() {
		return userToken;
	}

	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}

	@JsonProperty("custom_tag_category_id")
	public String getCustomTagCategoryId() {
		return customTagCategoryId;
	}

	public void setCustomTagCategoryId(String customTagCategoryId) {
		this.customTagCategoryId = customTagCategoryId;
	}

	@JsonProperty("custom_tag_category_name")
	public String getCustomTagCategoryName() {
		return customTagCategoryName;
	}

	public void setCustomTagCategoryName(String customTagCategoryName) {
		this.customTagCategoryName = customTagCategoryName;
	}

}
