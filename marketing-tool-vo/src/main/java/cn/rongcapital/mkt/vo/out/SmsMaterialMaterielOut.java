package cn.rongcapital.mkt.vo.out;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by byf on 12/5/16.
 */
public class SmsMaterialMaterielOut {

    private Integer materielId;
    private Integer materielType;

    @JsonProperty("materiel_id")
    public Integer getMaterielId() {
        return materielId;
    }

    public void setMaterielId(Integer materielId) {
        this.materielId = materielId;
    }

    @JsonProperty("materiel_type")
    public Integer getMaterielType() {
        return materielType;
    }

    public void setMaterielType(Integer materielType) {
        this.materielType = materielType;
    }
}
