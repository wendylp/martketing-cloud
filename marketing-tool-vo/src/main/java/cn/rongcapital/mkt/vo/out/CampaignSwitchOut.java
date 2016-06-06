package cn.rongcapital.mkt.vo.out;

public class CampaignSwitchOut {

	private int id;
	
	private int campaignHeadId;
	
	private String itemId;
	
	private byte type;//分支类型,0:curveTriangle
	
	private String color;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCampaignHeadId() {
		return campaignHeadId;
	}
	public void setCampaignHeadId(int campaignHeadId) {
		this.campaignHeadId = campaignHeadId;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public byte getType() {
		return type;
	}
	public void setType(byte type) {
		this.type = type;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	
	
}
