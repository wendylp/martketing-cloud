package cn.rongcapital.mkt.vo.in;

import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class CampaignDecisionPubFansIn {

    private String name;
    
    
    private Integer assetId;

    private Byte subscribeTime;

    private Integer refreshInterval;

    private Byte refreshIntervalType;

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    @JsonProperty("subscribe_time")
    public Byte getSubscribeTime() {
        return subscribeTime;
    }

    public void setSubscribeTime(Byte subscribeTime) {
        this.subscribeTime = subscribeTime;
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

    @JsonProperty("asset_id")
	public Integer getAssetId() {
		return assetId;
	}

	public void setAssetId(Integer assetId) {
		this.assetId = assetId;
	}

}
