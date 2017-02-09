package cn.rongcapital.mkt.vo.out;

import org.codehaus.jackson.annotate.JsonProperty;

public class CampaignAudienceFixOut {

    private String name;

    private Integer audienceFixId;

    private String audienceFixName;

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