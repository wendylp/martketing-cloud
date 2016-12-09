package cn.rongcapital.mkt.vo.sms.in;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import javax.validation.constraints.NotNull;

/**
 * Created by byf on 12/2/16.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class SmsMaterialMaterielIn {

    @NotNull
    private Long componentId;
    @NotNull
    private Integer componentType;

    @JsonProperty("component_id")
    public Long getComponentId() {
        return componentId;
    }

    public void setComponentId(Long componentId) {
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
