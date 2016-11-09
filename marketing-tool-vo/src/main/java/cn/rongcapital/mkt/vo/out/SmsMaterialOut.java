package cn.rongcapital.mkt.vo.out;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by byf on 11/9/16.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class SmsMaterialOut {

    private Long id;
    private String materialId;
    private String materialName;
    private Integer smsType;
    private Integer smsSignId;
    private String smsSignContent;
    private Integer smsTemplateId;
    private String smsTemplateContent;

    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonProperty("material_id")
    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    @JsonProperty("material_name")
    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    @JsonProperty("sms_type")
    public Integer getSmsType() {
        return smsType;
    }

    public void setSmsType(Integer smsType) {
        this.smsType = smsType;
    }

    @JsonProperty("sms_sign_id")
    public Integer getSmsSignId() {
        return smsSignId;
    }

    public void setSmsSignId(Integer smsSignId) {
        this.smsSignId = smsSignId;
    }

    @JsonProperty("sms_sign_content")
    public String getSmsSignContent() {
        return smsSignContent;
    }

    public void setSmsSignContent(String smsSignContent) {
        this.smsSignContent = smsSignContent;
    }

    @JsonProperty("sms_template_id")
    public Integer getSmsTemplateId() {
        return smsTemplateId;
    }

    public void setSmsTemplateId(Integer smsTemplateId) {
        this.smsTemplateId = smsTemplateId;
    }

    @JsonProperty("sms_template_content")
    public String getSmsTemplateContent() {
        return smsTemplateContent;
    }

    public void setSmsTemplateContent(String smsTemplateContent) {
        this.smsTemplateContent = smsTemplateContent;
    }
}
