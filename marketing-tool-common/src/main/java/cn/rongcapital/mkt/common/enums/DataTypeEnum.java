package cn.rongcapital.mkt.common.enums;

public enum DataTypeEnum {

    PARTY(0, "主数据"), 
    APP(1, "APP"), 
    POS(2, "POS"), 
    PUBLIC(3, "公众号"), 
    PERSONAL(4, "个人号"), 
    ESHOP(5, "网上商城"), 
    CRM(6, "CRM"), 
    TMALL(7, "天猫"),

    ;

    private DataTypeEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 数据类型代码
     */
    private int code;

    /**
     * 数据类型描述
     */
    private String description;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static boolean isContainCode(int code) {
        boolean result = false;
        DataTypeEnum[] dataTypeEnums = DataTypeEnum.values();
        for (DataTypeEnum dataTypeEnum : dataTypeEnums) {
            if (dataTypeEnum.getCode() == code) {
                return true;
            }
        }
        return result;
    }

}
