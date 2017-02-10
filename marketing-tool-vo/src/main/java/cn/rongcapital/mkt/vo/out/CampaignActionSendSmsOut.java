package cn.rongcapital.mkt.vo.out;


import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown=true)
public class CampaignActionSendSmsOut {

    private String name;

    private Integer smsMaterialId;

    private Integer smsCategoryType;
    
    private String smsMaterialName;
    
    private String smsCategoryName;

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
    
    @JsonProperty("sms_material_name")
	public String getSmsMaterialName() {
		return smsMaterialName;
	}

	public void setSmsMaterialName(String smsMaterialName) {
		this.smsMaterialName = smsMaterialName;
	}

    @JsonProperty("sms_category_name")
	public String getSmsCategoryName() {
		return smsCategoryName;
	}

	public void setSmsCategoryName(String smsCategoryName) {
		this.smsCategoryName = smsCategoryName;
	}
    
    
}