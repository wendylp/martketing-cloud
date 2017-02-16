package cn.rongcapital.mkt.common.enums;

public enum CampaignTagTypeEnum {

	CAMPAIGN_TAG_TYPE_CUSTOM(1, "自定义标签"),
	CAMPAIGN_TAG_TYPE_SYSTEM(0, "系统标签");
	
    private Integer code;

    private String description;
	
	private CampaignTagTypeEnum(Integer code,String description){
        this.code = code;
        this.description = description;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
    
}
