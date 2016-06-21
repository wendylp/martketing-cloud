package cn.rongcapital.mkt.common.enums;

public enum DeleteStatusEnum {

    ACTIVE(0, "未删除"), 
    DELETED(1, "已删除"),;

    private DeleteStatusEnum(int statusCode, String description) {
        this.statusCode = statusCode;
        this.description = description;
    }

    private int statusCode;

    private String description;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



}
