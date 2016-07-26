package cn.rongcapital.mkt.vo.out;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class HomePageCalendarListOut {

    private String today;

    private List<HomePageCalendarData> calendarData;

    public String getToday() {
        return today;
    }

    public void setToday(String today) {
        this.today = today;
    }

    @JsonProperty("calendar_data")
    public List<HomePageCalendarData> getCalendarData() {
        return calendarData;
    }

    public void setCalendarData(List<HomePageCalendarData> calendarData) {
        this.calendarData = calendarData;
    }

}
