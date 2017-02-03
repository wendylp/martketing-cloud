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
public class TagSyncEventObjectIn extends BaseInput {
	@NotEmpty
	private String userId = null;
	@NotEmpty
	private String userToken = null;
	@NotEmpty
	private String objectCode = null;
	@NotEmpty
	private String qrcodeId = null;
	@NotEmpty
	private long beginTime;
	@NotEmpty
	private long endTime;

	@JsonProperty("user_token")
	public String getUserToken() {
		return userToken;
	}

	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}

	@JsonProperty("user_id")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@JsonProperty("object_code")
	public String getObjectCode() {
		return objectCode;
	}

	public void setObjectCode(String objectCode) {
		this.objectCode = objectCode;
	}

	@JsonProperty("qrcode_id")
	public String getQrcodeId() {
		return qrcodeId;
	}

	public void setQrcodeId(String qrcodeId) {
		this.qrcodeId = qrcodeId;
	}

	@JsonProperty("begin_time")
	public long getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(long beginTime) {
		this.beginTime = beginTime;
	}

	@JsonProperty("end_time")
	public long getEndTime() {
		return endTime;
	}
	@JsonProperty("end_time")
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
}
