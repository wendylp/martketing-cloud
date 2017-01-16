/*************************************************
 * @功能及特点的描述简述: 事件注册的接收类
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：营销云系统
 * @author: 谢小良
 * @version: 版本v1.7
 * @date(创建、开发日期)：2017-1-11
 * @date(最后修改日期)：2017-1-11
 * @复审人：
 *************************************************/
package cn.rongcapital.mkt.event.vo.in;

import cn.rongcapital.mkt.vo.BaseInput;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true)
public class EventRegisterIn extends BaseInput {
    @NotBlank
    @JsonProperty("name")
    @Size(min=1,max=50)
    private String name;
    @NotBlank
    @JsonProperty("code")
    @Size(min=1,max=50)
    private String code;
    @NotBlank
    @JsonProperty("source_code")
    private String sourceCode;
    @NotBlank
    @JsonProperty("object_code")
    private String objectCode;
    @NotBlank
    @JsonProperty("register_opportunity")
    @Size(min=1,max=100)
    private String registerOpportunity;
    @NotBlank
    @JsonProperty("trigger_opportunity")
    @Size(min=1,max=100)
    private String triggerOpportunity;
    @JsonProperty("attributes")
    @Valid
    private List<EventRegisterAttributeIn> attributes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    public String getObjectCode() {
        return objectCode;
    }

    public void setObjectCode(String objectCode) {
        this.objectCode = objectCode;
    }

    public String getRegisterOpportunity() {
        return registerOpportunity;
    }

    public void setRegisterOpportunity(String registerOpportunity) {
        this.registerOpportunity = registerOpportunity;
    }

    public String getTriggerOpportunity() {
        return triggerOpportunity;
    }

    public void setTriggerOpportunity(String triggerOpportunity) {
        this.triggerOpportunity = triggerOpportunity;
    }

    public List<EventRegisterAttributeIn> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<EventRegisterAttributeIn> attributes) {
        this.attributes = attributes;
    }
}
