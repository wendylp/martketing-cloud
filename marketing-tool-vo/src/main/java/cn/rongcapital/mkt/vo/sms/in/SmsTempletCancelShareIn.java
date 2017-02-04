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


import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class SmsTempletCancelShareIn {

    @NotNull
    @JsonProperty("org_id")
    private Long orgId;

    @NotEmpty
    @JsonProperty("share_id")
    private String share_id;

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getShare_id() {
        return share_id;
    }

    public void setShare_id(String share_id) {
        this.share_id = share_id;
    }

    @Override
    public String toString() {
        return "SmsTempletCancelShareIn [orgId=" + orgId + ", share_id=" + share_id + "]";
    }

}
