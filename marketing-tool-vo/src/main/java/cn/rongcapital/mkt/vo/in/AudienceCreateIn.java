/*************************************************
 * @功能简述: 保存固定人群
 * @项目名称: marketing cloud
 * @see:
 * @author: zhuxuelong
 * @version: 0.0.1
 * @date: 2017/4/12
 * @复审人:
 *************************************************/
package cn.rongcapital.mkt.vo.in;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class AudienceCreateIn {

    @NotNull
    @JsonProperty("org_id")
    private Integer orgId = 0;

    /**
     * 人群名称
     */
    @NotEmpty
    @Length(min = 1, max = 45)
    private String name;

    /**
     * 来源
     */
    @NotEmpty
    @Length(min = 1, max = 512)
    private String source;

    /**
     * 人群明细
     */
    @NotEmpty
    private List<Integer> details;

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public List<Integer> getDetails() {
        return details;
    }

    public void setDetails(List<Integer> details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "AudienceCreateIn [orgId=" + orgId + ", name=" + name + ", source=" + source + ", details=" + details
                + "]";
    }

}
