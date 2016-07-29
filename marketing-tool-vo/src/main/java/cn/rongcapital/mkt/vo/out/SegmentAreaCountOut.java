package cn.rongcapital.mkt.vo.out;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

/**
 * Created by ethan on 16/7/25.
 */
public class SegmentAreaCountOut {

    @JsonProperty("china_population_count")
    private Integer chinaPopulationCount;

    @JsonProperty("foreign_population_count")
    private Integer foreignPopulationCount;

    @JsonProperty("population_count")
    private List<SegmentDimensionCountOut> provinceList;

    public Integer getChinaPopulationCount() {
        return chinaPopulationCount;
    }

    public void setChinaPopulationCount(Integer chinaPopulationCount) {
        this.chinaPopulationCount = chinaPopulationCount;
    }

    public Integer getForeignPopulationCount() {
        return foreignPopulationCount;
    }

    public void setForeignPopulationCount(Integer foreignPopulationCount) {
        this.foreignPopulationCount = foreignPopulationCount;
    }

    public List<SegmentDimensionCountOut> getProvinceList() {
        return provinceList;
    }

    public void setProvinceList(List<SegmentDimensionCountOut> provinceList) {
        this.provinceList = provinceList;
    }
}
