package cn.rongcapital.mkt.vo.in;


import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class CampaignDecisionPrvtFriendsIn {
	
    private Integer campaignHeadId;

    private String itemId;

    private String name;

    private String prvtId;

    private String prvtName;

    private String groupName;
    
    private int groupId;//TO DO

    private Integer refreshInterval;

    private Byte refreshIntervalType;

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

    @JsonProperty("prvt_name")
    public String getPrvtName() {
        return prvtName;
    }

    public void setPrvtName(String prvtName) {
        this.prvtName = prvtName == null ? null : prvtName.trim();
    }

    @JsonProperty("group_name")
    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName == null ? null : groupName.trim();
    }

    @JsonProperty("refresh_interval")
    public Integer getRefreshInterval() {
        return refreshInterval;
    }

    public void setRefreshInterval(Integer refreshInterval) {
        this.refreshInterval = refreshInterval;
    }

    @JsonProperty("refresh_interval_type")
    public Byte getRefreshIntervalType() {
        return refreshIntervalType;
    }

    public void setRefreshIntervalType(Byte refreshIntervalType) {
        this.refreshIntervalType = refreshIntervalType;
    }

    @JsonProperty("group_id")
	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
    
}
