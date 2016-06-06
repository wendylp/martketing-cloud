package cn.rongcapital.mkt.vo.out;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import cn.rongcapital.mkt.vo.BaseOutput;

public class CampaignBodyOut extends BaseOutput {
	
	private int segmentHeadId;
	private String intemId;
	private int audienceCount;//节点当前人数
	private byte itemType;
	private byte nodeType;
	private String statisticsUrl;//页面右侧图标url
	private String posX;//x坐标
	private String poxY;
	
	private List<CampaignSwitchOut> campaignSwitchList = new ArrayList<CampaignSwitchOut>();
	
	private Object info;//节点配置的属性

	public CampaignBodyOut(int code, String msg, int total, List<Object> data){
		super(code,msg,total,data);
	}
	@JsonProperty("segment_head_id")
	public int getSegmentHeadId() {
		return segmentHeadId;
	}

	public void setSegmentHeadId(int segmentHeadId) {
		this.segmentHeadId = segmentHeadId;
	}

	@JsonProperty("item_id")
	public String getIntemId() {
		return intemId;
	}

	public void setIntemId(String intemId) {
		this.intemId = intemId;
	}

	@JsonProperty("audience_count")
	public int getAudienceCount() {
		return audienceCount;
	}

	public void setAudienceCount(int audienceCount) {
		this.audienceCount = audienceCount;
	}

	@JsonProperty("item_type")
	public byte getItemType() {
		return itemType;
	}

	public void setItemType(byte itemType) {
		this.itemType = itemType;
	}

	@JsonProperty("node_type")
	public byte getNodeType() {
		return nodeType;
	}

	public void setNodeType(byte nodeType) {
		this.nodeType = nodeType;
	}

	@JsonProperty("statistics_url")
	public String getStatisticsUrl() {
		return statisticsUrl;
	}

	public void setStatisticsUrl(String statisticsUrl) {
		this.statisticsUrl = statisticsUrl;
	}

	@JsonProperty("x")
	public String getPosX() {
		return posX;
	}

	public void setPosX(String posX) {
		this.posX = posX;
	}

	@JsonProperty("y")
	public String getPoxY() {
		return poxY;
	}

	public void setPoxY(String poxY) {
		this.poxY = poxY;
	}

	@JsonProperty("y")
	public List<CampaignSwitchOut> getCampaignSwitchList() {
		return campaignSwitchList;
	}
	
	public void setCampaignSwitchList(List<CampaignSwitchOut> campaignSwitchList) {
		this.campaignSwitchList = campaignSwitchList;
	}

	@JsonProperty("switch")
	public Object getInfo() {
		return info;
	}

	public void setInfo(Object info) {
		this.info = info;
	}

	
}
