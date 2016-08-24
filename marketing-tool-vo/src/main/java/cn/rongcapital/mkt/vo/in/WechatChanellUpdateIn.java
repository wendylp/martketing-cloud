package cn.rongcapital.mkt.vo.in;

import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import cn.rongcapital.mkt.vo.BaseInput;

/**
 * Created by zhaoguoying on 2016-08-11.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class WechatChanellUpdateIn extends BaseInput {

	@NotNull
	private Integer channelId;
	@NotEmpty
	private String chName;
	@NotNull
	private Integer status;
	
	@JsonProperty("channel_id")
	public Integer getChannelId() {
		return channelId;
	}
	public void setChannelId(Integer channelId) {
		this.channelId = channelId;
	}
	@JsonProperty("ch_name")
	public String getChName() {
		return chName;
	}
	public void setChName(String chName) {
		this.chName = chName;
	}
	@JsonProperty("status")
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
