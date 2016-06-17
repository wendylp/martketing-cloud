package cn.rongcapital.mkt.vo.in;


import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class CampaignActionSendPrivtIn {
	
	private Integer campaignHeadId;
	
    private String itemId;

    private String name;

    private String prvtId;

    private String prvtGroupName;

    private String prvtName;

    private String textInfo;

    @JsonProperty("campaign_head_id")
    public Integer getCampaignHeadId() {
		return campaignHeadId;
	}

	public void setCampaignHeadId(Integer campaignHeadId) {
		this.campaignHeadId = campaignHeadId;
	}

    @JsonProperty("item_id")
    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId == null ? null : itemId.trim();
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    @JsonProperty("prvt_id")
    public String getPrvtId() {
        return prvtId;
    }

    public void setPrvtId(String prvtId) {
        this.prvtId = prvtId == null ? null : prvtId.trim();
    }

    @JsonProperty("prvt_group_name")
    public String getPrvtGroupName() {
        return prvtGroupName;
    }

    public void setPrvtGroupName(String prvtGroupName) {
        this.prvtGroupName = prvtGroupName == null ? null : prvtGroupName.trim();
    }

    @JsonProperty("prvt_name")
    public String getPrvtName() {
        return prvtName;
    }

    public void setPrvtName(String prvtName) {
        this.prvtName = prvtName == null ? null : prvtName.trim();
    }

    @JsonProperty("text_info")
    public String getTextInfo() {
        return textInfo;
    }

    public void setTextInfo(String textInfo) {
        this.textInfo = textInfo == null ? null : textInfo.trim();
    }
}
