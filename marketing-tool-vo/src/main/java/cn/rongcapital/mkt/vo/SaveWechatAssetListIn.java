package cn.rongcapital.mkt.vo;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by Yunfeng on 2016-6-1.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class SaveWechatAssetListIn extends BaseInput {
    private int assetId;
    private int groupId;

    @JsonProperty("asset_id")
    public int getAssetId() {
        return assetId;
    }

    public void setAssetId(int assetId) {
        this.assetId = assetId;
    }

    @JsonProperty("group_id")
    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }
}
