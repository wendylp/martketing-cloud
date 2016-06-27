package cn.rongcapital.mkt.vo.in;


import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class CampaignActionSendH5In {
	
    private String name;

    
    private Integer pubAssetId;
    
    
    private Integer imgTextAssetId;

    @JsonProperty("name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty("pub_asset_id")
	public Integer getPubAssetId() {
		return pubAssetId;
	}

	public void setPubAssetId(Integer pubAssetId) {
		this.pubAssetId = pubAssetId;
	}

	@JsonProperty("img_text_asset_id")
	public Integer getImgTextAssetId() {
		return imgTextAssetId;
	}

	public void setImgTextAssetId(Integer imgTextAssetId) {
		this.imgTextAssetId = imgTextAssetId;
	}

}
