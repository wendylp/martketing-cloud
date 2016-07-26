package cn.rongcapital.mkt.vo.out;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by ethan on 16/7/25.
 */
public class SegmentProvinceCountOut {

    @JsonProperty("dimension_name")
    private String province;

    @JsonProperty("population_count")
    private Integer populationCount;

    public SegmentProvinceCountOut(String province, Integer populationCount) {
        this.province = province;
        this.populationCount = populationCount;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public Integer getPopulationCount() {
        return populationCount;
    }

    public void setPopulationCount(Integer populationCount) {
        this.populationCount = populationCount;
    }
}
