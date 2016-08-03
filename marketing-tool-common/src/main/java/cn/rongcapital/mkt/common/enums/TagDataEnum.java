package cn.rongcapital.mkt.common.enums;

public enum TagDataEnum {

    IN_THIS_MONTH(1, "本月内"),
    IN_THREE_MONTH(2, "三个月内"),
    IN_HALF_YEAR(3, "半年内"),
    ONE_TIME(4, "1次"),
    TWO_TIME(5, "2次"),
    THREE_TIME(6, "3次"),
    MORE_THAN_FOUR_TIME(7, "4次以上"),
    ONE_TO_TWO_TIME(8, "1-2次"),
    MORE_THAN_THREE_TIME(9, "3次以上")

    ;

    private TagDataEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    private int code;

    private String name;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
