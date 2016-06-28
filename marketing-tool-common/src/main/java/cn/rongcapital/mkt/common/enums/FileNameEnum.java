package cn.rongcapital.mkt.common.enums;

public enum FileNameEnum {

    PARTY(0, "party"), 
    POPULATION(1, "population"), 
    CUSTOMER_TAGS(2, "customer_tags"), 
    ARCH_POINT(3, "arch_point"), 
    MEMBER(4, "member"), 
    LOGIN(5, "login"), 
    PAYMENT(6, "payment"), 
    SHOPPING(7, "shopping"), 
    IMPORT_DATA_MODIFY_LOG(8, "import_data_modify_log"),

    ;

    private FileNameEnum(int code, String detailName) {
        this.code = code;
        this.detailName = detailName;
    }

    private String detailName;

    private int code;

    public String getDetailName() {
        return detailName;
    }

    public void setDetailName(String detailName) {
        this.detailName = detailName;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static String getNameByCode(int code) {
        FileNameEnum[] fileNameEnums = FileNameEnum.values();
        for (FileNameEnum fileNameEnum : fileNameEnums) {
            if (fileNameEnum.getCode() == code) {
                return fileNameEnum.getDetailName();
            }
        }

        return "";
    }

}
