package cn.rongcapital.mkt.vo.out;

import org.codehaus.jackson.annotate.JsonProperty;

public class HomePageCalendarData {

    private String activeDay;

    private String status;

    @JsonProperty("active_day")
    public String getActiveDay() {
        return activeDay;
    }

    public void setActiveDay(String activeDay) {
        this.activeDay = activeDay;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
