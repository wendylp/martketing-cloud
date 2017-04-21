
package cn.rongcapital.mkt.common.enums.dataauth;

public enum ShareOrgTypeEnum {
	TOORGS("toOrgs","分享给某些组织"), ORGTO("orgTo","来源那个组织");
	
    private String code = "";
    private String description = "";

    private ShareOrgTypeEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * @功能描述: 通过Code获取相应的枚举值
     * @param code
     * @return boolean
     */
    public static ShareOrgTypeEnum getByCode(String code) {
        if (ShareOrgTypeEnum.contains(code)) {
            for (ShareOrgTypeEnum item : ShareOrgTypeEnum.values()) {
                if (item.code.equals(code)) {
                    return item;
                }
            }
        }
        return null;
    }

    /**
     * @功能描述: 验证是否包含此Code
     * @param code
     * @return boolean
     */
    public static boolean contains(String code) {
    	ShareOrgTypeEnum[] channelCodes = values();
        for (ShareOrgTypeEnum item : channelCodes) {
            if (item.getCode().equals(code)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
