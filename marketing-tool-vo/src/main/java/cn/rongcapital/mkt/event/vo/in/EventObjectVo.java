/*************************************************
 * @功能简述: EventObjectVo
 * @项目名称: marketing cloud
 * @see:
 * @author: zhuxuelong
 * @version: 0.0.1
 * @date: 2017/01/09
 * @复审人:
 *************************************************/
package cn.rongcapital.mkt.event.vo.in;

import java.util.List;

import javax.validation.Valid;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import cn.rongcapital.mkt.vo.BaseInput;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EventObjectVo extends BaseInput {

    /**
     * 类别
     */
    @NotEmpty
    @Length(min = 1, max = 100)
    private String type;

    /**
     * 客体类别标识
     */
    @NotEmpty
    @Length(min = 1, max = 50)
    private String code;

    /**
     * 客体类别名称
     */
    @NotEmpty
    @Length(min = 1, max = 50)
    private String name;

    /**
     * 客体属性
     */
    @JsonProperty("attributes")
    @Valid
    private List<EventObjecAttribure> attributes;


    public String getType() {
        return type;
    }


    public void setType(String type) {
        this.type = type;
    }


    public String getCode() {
        return code;
    }


    public void setCode(String code) {
        this.code = code;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public List<EventObjecAttribure> getAttributes() {
        return attributes;
    }


    public void setAttributes(List<EventObjecAttribure> attributes) {
        this.attributes = attributes;
    }


    @Override
    public String toString() {
        return "EventObjectVo [type=" + type + ", code=" + code + ", name=" + name + ", attributes=" + attributes + "]";
    }

}
