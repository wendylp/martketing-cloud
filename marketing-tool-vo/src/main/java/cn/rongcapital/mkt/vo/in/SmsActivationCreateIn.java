package cn.rongcapital.mkt.vo.in;

import cn.rongcapital.mkt.vo.BaseInput;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by byf on 10/18/16.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class SmsActivationCreateIn extends BaseInput{

    private Long taskId;

    @NotEmpty
    private String taskName;

    @NotNull
    private Long taskSignatureId;

    @NotNull
    private Long taskTemplateId;

    @NotEmpty
    private String taskTemplateContent;

    private List<SmsTargetAudienceIn> smsTargetAudienceInArrayList;

    @NotNull
    private Integer taskSendType;
    private Integer taskAppType;

    @JsonProperty("task_id")
    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    @JsonProperty("task_name")
    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    @JsonProperty("task_signature_id")
    public Long getTaskSignatureId() {
        return taskSignatureId;
    }

    public void setTaskSignatureId(Long taskSignatureId) {
        this.taskSignatureId = taskSignatureId;
    }

    @JsonProperty("task_template_id")
    public Long getTaskTemplateId() {
        return taskTemplateId;
    }

    public void setTaskTemplateId(Long taskTemplateId) {
        this.taskTemplateId = taskTemplateId;
    }

    @JsonProperty("task_template_content")
    public String getTaskTemplateContent() {
        return taskTemplateContent;
    }

    public void setTaskTemplateContent(String taskTemplateContent) {
        this.taskTemplateContent = taskTemplateContent;
    }

    @JsonProperty("task_audience_list")
    public List<SmsTargetAudienceIn> getSmsTargetAudienceInArrayList() {
        return smsTargetAudienceInArrayList;
    }

    public void setSmsTargetAudienceInArrayList(List<SmsTargetAudienceIn> smsTargetAudienceInArrayList) {
        this.smsTargetAudienceInArrayList = smsTargetAudienceInArrayList;
    }

    @JsonProperty("task_send_type")
    public Integer getTaskSendType() {
        return taskSendType;
    }

    public void setTaskSendType(Integer taskSendType) {
        this.taskSendType = taskSendType;
    }

    @JsonProperty("task_app_type")
    public Integer getTaskAppType() {
        return taskAppType;
    }

    public void setTaskAppType(Integer taskAppType) {
        this.taskAppType = taskAppType;
    }
}
