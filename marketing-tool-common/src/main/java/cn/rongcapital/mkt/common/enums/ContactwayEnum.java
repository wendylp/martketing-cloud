package cn.rongcapital.mkt.common.enums;

public enum ContactwayEnum {

    MOBILE(1, "手机"), 
    EMAIL(2, "电子邮件"), 
    WECHAT(3, "微信号"),

    ;

    private ContactwayEnum(int id, String description) {
        this.id = id;
        this.description = description;
    }

    private int id;

    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
