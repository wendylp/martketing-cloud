/*************************************************
 * @功能简述: SmsTempletCancelShareIn
 * @项目名称: marketing cloud
 * @see:
 * @author: zhuxuelong
 * @version: 0.0.1
 * @date: 2017/02/04
 * @复审人:
 *************************************************/
package cn.rongcapital.mkt.vo.sms.in;


import java.util.List;

import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class SmsTempletCancelShareIn {

    @NotNull
    @JsonProperty("resource_id")
    private Long resourceId;

    @NotEmpty
    @JsonProperty("share_ids")
    private List<String> shareIds;

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public List<String> getShareIds() {
        return shareIds;
    }

    public void setShareIds(List<String> shareIds) {
        this.shareIds = shareIds;
    }

    @Override
    public String toString() {
        return "SmsTempletCancelShareIn [resourceId=" + resourceId + ", shareIds=" + shareIds + "]";
    }

}
