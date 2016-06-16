package cn.rongcapital.mkt.vo.out;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonProperty;

import cn.rongcapital.mkt.vo.BaseOutput;

public class DataGetMainCountOut extends BaseOutput {

    public DataGetMainCountOut() {
        mdTypesPair = new HashMap<>();
    }

    public DataGetMainCountOut(int code, String msg, int total, List<Object> data) {
        super(code, msg, total, data);
        mdTypesPair = new HashMap<>();
    }

    @JsonProperty("md_types")
    private Map<String, Integer> mdTypesPair;

    public Map<String, Integer> getMdTypesPair() {
        return mdTypesPair;
    }

    public void setMdTypesPair(Map<String, Integer> mdTypesPair) {
        this.mdTypesPair = mdTypesPair;
    }

}
