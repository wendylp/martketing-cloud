package cn.rongcapital.mkt.vo.out;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class CampaignNodeChainOut {
	
	private String itemId;
	
	private byte itemType;
	
	private byte nodeType;
	
	private String code;
	
	private String icon;
	
	private String posX;//x坐标
	
	private String posY;//Y坐标
	
	private String posZ;//X坐标
	
	private List<CampaignSwitchOut> campaignSwitchList;
	
	private List<CampaignSwitchOut> campaignEndsList;
	
	private Object info;//节点配置的属性

	@JsonProperty("item_id")
	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
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

	@JsonProperty("x")
	public String getPosX() {
		return posX;
	}

	public void setPosX(String posX) {
		this.posX = posX;
	}

	
	@JsonProperty("y")
	public String getPosY() {
		return posY;
	}

	public void setPosY(String posY) {
		this.posY = posY;
	}

	@JsonProperty("z")
	public String getPosZ() {
		return posZ;
	}

	public void setPosZ(String posZ) {
		this.posZ = posZ;
	}

	@JsonProperty("switch")
	public List<CampaignSwitchOut> getCampaignSwitchList() {
		return campaignSwitchList;
	}
	
	public void setCampaignSwitchList(List<CampaignSwitchOut> campaignSwitchList) {
		this.campaignSwitchList = campaignSwitchList;
	}

	@JsonProperty("ends")
	public List<CampaignSwitchOut> getCampaignEndsList() {
		return campaignEndsList;
	}

	public void setCampaignEndsList(List<CampaignSwitchOut> campaignEndsList) {
		this.campaignEndsList = campaignEndsList;
	}


	@JsonProperty("info")
	public Object getInfo() {
		return info;
	}

	public void setInfo(Object info) {
		this.info = info;
	}

	@JsonProperty("code")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@JsonProperty("icon")
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	
}
