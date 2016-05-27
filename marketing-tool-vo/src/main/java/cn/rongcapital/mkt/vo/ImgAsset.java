package cn.rongcapital.mkt.vo;


import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Created by Yunfeng on 2016-5-27.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class ImgAsset extends BaseInput{
    private int imgtextId;
    private int assetType;
    private String ownerName;

    @JsonProperty("imgtext_id")
    public int getImgtextId() {
        return imgtextId;
    }

    public void setImgtextId(int imgtextId) {
        this.imgtextId = imgtextId;
    }

    @JsonProperty("owner_name")
    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    @JsonProperty("type")
    public int getAssetType() {
        return assetType;
    }

    public void setAssetType(int assetType) {
        this.assetType = assetType;
    }
}
