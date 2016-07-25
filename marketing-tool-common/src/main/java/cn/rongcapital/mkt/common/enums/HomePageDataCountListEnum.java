package cn.rongcapital.mkt.common.enums;

public enum HomePageDataCountListEnum {

    DATA_PARTY(1, "接入数据", "数据接入"),
    TAG(2, "标签", "标签管理"),
    SEGMENtATION_HEAD(4, "细分人群", "细分管理"),

    ;

    private HomePageDataCountListEnum(int id, String name, String linkName) {
        this.id = id;
        this.name = name;
        this.linkName = linkName;
    }

    private int id;

    private String name;
    
    private String linkName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLinkName() {
        return linkName;
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName;
    }
    
}
