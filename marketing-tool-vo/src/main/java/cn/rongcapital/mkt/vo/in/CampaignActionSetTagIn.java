package cn.rongcapital.mkt.vo.in;


import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class CampaignActionSetTagIn {
	
	private Integer campaignHeadId;
	
    private Integer id;

    private String itemId;

    private String name;

    private List<String> tagNames;

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

    @JsonProperty("tag_names")
	public List<String> getTagNames() {
		return tagNames;
	}

	public void setTagNames(List<String> tagNames) {
		this.tagNames = tagNames;
	}
    
}
