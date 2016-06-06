package cn.rongcapital.mkt.vo.in;


import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;

public class CampaignActionWaitIn {
	
	private Integer campaignHeadId;
	
    private Integer id;

    private String itemId;

    private String name;

    private Byte type;

    private Integer relativeValue;

    private Byte relativeType;

    private Date specificTime;

    @JsonProperty("campaign_head_id")
    public Integer getCampaignHeadId() {
		return campaignHeadId;
	}

	public void setCampaignHeadId(Integer campaignHeadId) {
		this.campaignHeadId = campaignHeadId;
	}

	@JsonProperty("id")
	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    @JsonProperty("type")
    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    @JsonProperty("relative_value")
    public Integer getRelativeValue() {
        return relativeValue;
    }

    public void setRelativeValue(Integer relativeValue) {
        this.relativeValue = relativeValue;
    }

    @JsonProperty("relative_type")
    public Byte getRelativeType() {
        return relativeType;
    }

    public void setRelativeType(Byte relativeType) {
        this.relativeType = relativeType;
    }

    @JsonProperty("specific_time")
    public Date getSpecificTime() {
        return specificTime;
    }

    public void setSpecificTime(Date specificTime) {
        this.specificTime = specificTime;
    }
}
