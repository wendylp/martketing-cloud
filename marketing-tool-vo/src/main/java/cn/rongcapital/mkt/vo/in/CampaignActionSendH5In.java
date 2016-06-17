package cn.rongcapital.mkt.vo.in;


import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class CampaignActionSendH5In {
	
	private Integer campaignHeadId;
	
    private Integer id;

    private String itemId;

    private String name;

    private Integer wechatH5Id;

    private String wechatH5Name;

    private String pubId;

    private String pubName;

    private String prvtId;

    private String prvtGroupName;

    private String prvtName;
    
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

    @JsonProperty("wechat_h5_id")
    public Integer getWechatH5Id() {
        return wechatH5Id;
    }

    public void setWechatH5Id(Integer wechatH5Id) {
        this.wechatH5Id = wechatH5Id;
    }

    @JsonProperty("wechat_h5_name")
    public String getWechatH5Name() {
        return wechatH5Name;
    }

    public void setWechatH5Name(String wechatH5Name) {
        this.wechatH5Name = wechatH5Name == null ? null : wechatH5Name.trim();
    }

    @JsonProperty("pub_id")
    public String getPubId() {
        return pubId;
    }

    public void setPubId(String pubId) {
        this.pubId = pubId == null ? null : pubId.trim();
    }

    @JsonProperty("pub_name")
    public String getPubName() {
        return pubName;
    }

    public void setPubName(String pubName) {
        this.pubName = pubName == null ? null : pubName.trim();
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
}
