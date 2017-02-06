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


import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class SmsTempletShareIn {

    @NotNull
    @JsonProperty("resource_id")
    private Long resourceId;

    @NotEmpty
    @JsonProperty("org_ids")
    private List<Long> orgIds = new ArrayList<Long>();

    @NotNull
    @JsonProperty("writeable")
    private Boolean writeable;

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public List<Long> getOrgIds() {
        return orgIds;
    }

    public void setOrgIds(List<Long> orgIds) {
        this.orgIds = orgIds;
    }

    public Boolean getWriteable() {
        return writeable;
    }

    public void setWriteable(Boolean writeable) {
        this.writeable = writeable;
    }

    @Override
    public String toString() {
        return "SmsTempletShareIn [resourceId=" + resourceId + ", orgIds=" + orgIds + ", writeable=" + writeable + "]";
    }

}
