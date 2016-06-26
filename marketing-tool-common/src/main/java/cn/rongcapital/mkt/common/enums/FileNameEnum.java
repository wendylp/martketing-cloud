package cn.rongcapital.mkt.common.enums;

public enum FileNameEnum {

    IMPORT_DATA_MODIFY_LOG("import_data_modify_log"),

    ;

    private FileNameEnum(String detailName) {
        this.detailName = detailName;
    }

    private String detailName;

    public String getDetailName() {
        return detailName;
    }

    public void setDetailName(String detailName) {
        this.detailName = detailName;
    }

}
