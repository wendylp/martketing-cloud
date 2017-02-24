package cn.rongcapital.mkt.vo.in;


import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown=true)
public class CampaignActionSendSmsIn {

    private String name;

    private Integer smsMaterialId;

    private Integer smsCategoryType;

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    @JsonProperty("sms_material_id")
    public Integer getSmsMaterialId() {
        return smsMaterialId;
    }

    public void setSmsMaterialId(Integer smsMaterialId) {
        this.smsMaterialId = smsMaterialId;
    }

    @JsonProperty("sms_category_type")
    public Integer getSmsCategoryType() {
        return smsCategoryType;
    }

    public void setSmsCategoryType(Integer smsCategoryType) {
        this.smsCategoryType = smsCategoryType;
    }
}