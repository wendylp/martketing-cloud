package cn.rongcapital.mkt.vo.in;

import cn.rongcapital.mkt.vo.BaseInput;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

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
    private Long taskMaterialId;

    @NotEmpty
    private String taskMaterialContent;

    private List<SmsTargetAudienceIn> smsTargetAudienceInArrayList;

    @NotNull
    private Integer taskSendType;
    
    private Integer taskAppType;
    /**
     * 0:短信平台短信任务，1:活动编排中的任务 默认是0
     */
    private Integer smsTaskType=0;
    /**
     * 任务的编码，相同分组的任务的code是相同的
     */
    private String smsTaskCode;
    
    private Set<Integer> dataPartyIds;

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

    @JsonProperty("task_material_id")
    public Long getTaskMaterialId() {
		return taskMaterialId;
	}

	public void setTaskMaterialId(Long taskMaterialId) {
		this.taskMaterialId = taskMaterialId;
	}

	@JsonProperty("task_material_content")
	public String getTaskMaterialContent() {
		return taskMaterialContent;
	}

	public void setTaskMaterialContent(String taskMaterialContent) {
		this.taskMaterialContent = taskMaterialContent;
	}

	@JsonProperty("target_audience_list")
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

	public Integer getSmsTaskType() {
		return smsTaskType;
	}

	public void setSmsTaskType(Integer smsTaskType) {
		this.smsTaskType = smsTaskType;
	}

	public String getSmsTaskCode() {
		return smsTaskCode;
	}

	public void setSmsTaskCode(String smsTaskCode) {
		this.smsTaskCode = smsTaskCode;
	}

	public Set<Integer> getDataPartyIds() {
		return dataPartyIds;
	}

	public void setDataPartyIds(Set<Integer> dataPartyIds) {
		this.dataPartyIds = dataPartyIds;
	}
    
    
}
