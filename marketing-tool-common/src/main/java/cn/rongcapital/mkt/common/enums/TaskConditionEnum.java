package cn.rongcapital.mkt.common.enums;

import java.util.Calendar;
import java.util.Date;

public enum TaskConditionEnum {

    H(1, "H", "最近1小时"), 
    D(2, "D", "最近一天"), 
    W(3, "W", "最近一周"),;

    private TaskConditionEnum(int code, String abbreviation, String description) {
        this.code = code;
        this.abbreviation = abbreviation;
        this.description = description;
    }

    private int code;

    private String abbreviation;

    private String description;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getTime() {
        Calendar calendar = Calendar.getInstance();
        if (this.getCode() == 1) {
            calendar.add(Calendar.HOUR, -1);
        } else if (this.getCode() == 2) {
            calendar.add(Calendar.DATE, -1);
            return calendar.getTime();
        } else if (this.getCode() == 3) {
            calendar.add(Calendar.DATE, -7);
            return calendar.getTime();
        }

        return calendar.getTime();
    }
}
