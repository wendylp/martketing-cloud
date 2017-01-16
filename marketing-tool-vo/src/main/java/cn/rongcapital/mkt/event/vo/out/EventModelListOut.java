/*************************************************
 * @功能及特点的描述简述: 事件库查询(分页)
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：营销云系统
 * @author: 单璟琦
 * @version: 版本v1.7
 * @date(创建、开发日期)：2017-1-11 
 * @date(最后修改日期)：
 * @复审人：
 *************************************************/
package cn.rongcapital.mkt.event.vo.out;

import org.codehaus.jackson.annotate.JsonProperty;

public class EventModelListOut{
	
	private Long id;
	
	private String code;
	
	private String name;
	
	private String channel;
	
	private String platform;
	
	private String source;
	
	@JsonProperty("system_event")
	private Boolean systemEvent;
	
	@JsonProperty("bind_count")
	private Long bindCount;
	
	private Boolean subscribed;
	
	private Boolean unsubscribable;
	
	@JsonProperty("object_id")
	private Long objectId;
	
	@JsonProperty("object_name")
	private String objectName;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Boolean getSystemEvent() {
		return systemEvent;
	}

	public void setSystemEvent(Boolean systemEvent) {
		this.systemEvent = systemEvent;
	}

	public Long getBindCount() {
		return bindCount;
	}

	public void setBindCount(Long bindCount) {
		this.bindCount = bindCount;
	}

	public Boolean getSubscribed() {
		return subscribed;
	}

	public void setSubscribed(Boolean subscribed) {
		this.subscribed = subscribed;
	}

	public Boolean getUnsubscribable() {
		return unsubscribable;
	}

	public void setUnsubscribable(Boolean unsubscribable) {
		this.unsubscribable = unsubscribable;
	}

	public Long getObjectId() {
		return objectId;
	}

	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}
	
}
