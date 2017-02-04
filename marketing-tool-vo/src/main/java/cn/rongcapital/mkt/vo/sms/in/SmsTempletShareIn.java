/*************************************************
 * @功能简述: SmsTempletShareIn
 * @项目名称: marketing cloud
 * @see:
 * @author: zhuxuelong
 * @version: 0.0.1
 * @date: 2017/02/04
 * @复审人:
 *************************************************/
package cn.rongcapital.mkt.vo.sms.in;


import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class SmsTempletShareIn {

    @NotNull
    @JsonProperty("from_org_id")
    private Long fromOrgId;

    @NotNull
    @JsonProperty("to_org_id")
    private Long toOrgId;

    @NotNull
    @JsonProperty("resource_id")
    private Long resourceId;

    @NotNull
    @JsonProperty("writeable")
    private Boolean writeable;

    public Long getFromOrgId() {
        return fromOrgId;
    }

    public void setFromOrgId(Long fromOrgId) {
        this.fromOrgId = fromOrgId;
    }

    public Long getToOrgId() {
        return toOrgId;
    }

    public void setToOrgId(Long toOrgId) {
        this.toOrgId = toOrgId;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public Boolean getWriteable() {
        return writeable;
    }

    public void setWriteable(Boolean writeable) {
        this.writeable = writeable;
    }

    @Override
    public String toString() {
        return "SmsTempletShareIn [fromOrgId=" + fromOrgId + ", toOrgId=" + toOrgId + ", resourceId=" + resourceId
                + ", writeable=" + writeable + "]";
    }

}
