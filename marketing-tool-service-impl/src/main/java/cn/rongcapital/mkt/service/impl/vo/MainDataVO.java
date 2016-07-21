package cn.rongcapital.mkt.service.impl.vo;

import java.util.List;
import java.util.Map;

/**
 * Created by ethan on 16/7/5.
 */
public class MainDataVO {

    private List<Map<String, Object>> resultList;

    private List<Map<String, Object>> countList;

    private Integer totalCount;

    public List<Map<String, Object>> getResultList() {
        return resultList;
    }

    public void setResultList(List<Map<String, Object>> resultList) {
        this.resultList = resultList;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public List<Map<String, Object>> getCountList() {
        return countList;
    }

    public void setCountList(List<Map<String, Object>> countList) {
        this.countList = countList;
    }
}
