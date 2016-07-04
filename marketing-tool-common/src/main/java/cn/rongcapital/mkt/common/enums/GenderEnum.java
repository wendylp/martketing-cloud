package cn.rongcapital.mkt.common.enums;

public enum GenderEnum {

    MALE(1, "男"),
    FEMALE(2, "女"),
    OTHER(3, "其他"),;

    GenderEnum(Integer statusCode, String description) {
        this.statusCode = statusCode;
        this.description = description;
    }

    private Integer statusCode;

    private String description;

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
