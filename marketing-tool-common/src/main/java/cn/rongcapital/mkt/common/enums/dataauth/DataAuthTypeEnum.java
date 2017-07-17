
package cn.rongcapital.mkt.common.enums.dataauth;

public enum DataAuthTypeEnum {
	ORIGINAL("O","原始操作"), CLONE("C","克隆操作"), SHARE("S","分享操作操作");
	
    private String code = "";
    private String description = "";

    private DataAuthTypeEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * @功能描述: 通过Code获取相应的枚举值
     * @param code
     * @return boolean
     */
    public static DataAuthTypeEnum getByCode(String code) {
        if (DataAuthTypeEnum.contains(code)) {
            for (DataAuthTypeEnum item : DataAuthTypeEnum.values()) {
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
    	DataAuthTypeEnum[] channelCodes = values();
        for (DataAuthTypeEnum item : channelCodes) {
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
