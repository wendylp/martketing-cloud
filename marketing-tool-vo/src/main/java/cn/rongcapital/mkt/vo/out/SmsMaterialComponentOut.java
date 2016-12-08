package cn.rongcapital.mkt.vo.out;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by byf on 12/5/16.
 */
public class SmsMaterialComponentOut {

    private Integer componentId;
    private Integer componentType;

    @JsonProperty("component_id")
    public Integer getComponentId() {
        return componentId;
    }

    public void setComponentId(Integer componentId) {
        this.componentId = componentId;
    }

    @JsonProperty("component_type")
    public Integer getComponentType() {
        return componentType;
    }

    public void setComponentType(Integer componentType) {
        this.componentType = componentType;
    }
}
