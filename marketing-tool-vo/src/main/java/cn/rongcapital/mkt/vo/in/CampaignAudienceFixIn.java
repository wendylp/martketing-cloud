package cn.rongcapital.mkt.vo.in;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class CampaignAudienceFixIn {

    private Integer campaignHeadId;

    private String itemId;

    private String name;

    private Integer audienceFixId;

    private String audienceFixName;

    
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    @JsonProperty("fix_group_id")
    public Integer getAudienceFixId() {
        return audienceFixId;
    }

    public void setAudienceFixId(Integer audienceFixId) {
        this.audienceFixId = audienceFixId;
    }

    @JsonProperty("fix_group_name")
    public String getAudienceFixName() {
        return audienceFixName;
    }

    public void setAudienceFixName(String audienceFixName) {
        this.audienceFixName = audienceFixName == null ? null : audienceFixName.trim();
    }
}