package cn.rongcapital.mkt.vo.out;


import org.codehaus.jackson.annotate.JsonProperty;

public class CampaignActionSendH5Out {
	
    private String name;

    private Integer imgTextAssetId;

    private String imgTextAssetName;

    private Integer pubAssetId;

    private String pubAssetName;

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    @JsonProperty("img_text_asset_id")
	public Integer getImgTextAssetId() {
		return imgTextAssetId;
	}

	public void setImgTextAssetId(Integer imgTextAssetId) {
		this.imgTextAssetId = imgTextAssetId;
	}

	@JsonProperty("img_text_asset_name")
	public String getImgTextAssetName() {
		return imgTextAssetName;
	}

	public void setImgTextAssetName(String imgTextAssetName) {
		this.imgTextAssetName = imgTextAssetName;
	}

	@JsonProperty("pub_asset_id")
	public Integer getPubAssetId() {
		return pubAssetId;
	}

	public void setPubAssetId(Integer pubAssetId) {
		this.pubAssetId = pubAssetId;
	}

	@JsonProperty("pub_asset_name")
	public String getPubAssetName() {
		return pubAssetName;
	}

	public void setPubAssetName(String pubAssetName) {
		this.pubAssetName = pubAssetName;
	}
    

}
