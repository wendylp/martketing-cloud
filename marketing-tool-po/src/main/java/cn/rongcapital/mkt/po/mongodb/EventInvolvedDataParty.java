package cn.rongcapital.mkt.po.mongodb;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "event_involved_data_party")
public class EventInvolvedDataParty implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field(value = "event_code")
    private String eventCode;

    @Field(value = "data_id")
    private Integer dataId;

    @Field(value = "campaign_head_id")
    private Long campaignHeadId;
    
    @Field(value = "inserted")
    private Boolean inserted;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEventCode() {
        return eventCode;
    }

    public void setEventCode(String eventCode) {
        this.eventCode = eventCode;
    }

    public Integer getDataId() {
        return dataId;
    }

    public void setDataId(Integer dataId) {
        this.dataId = dataId;
    }

    public Long getCampaignHeadId() {
        return campaignHeadId;
    }

    public void setCampaignHeadId(Long campaignHeadId) {
        this.campaignHeadId = campaignHeadId;
    }

    public Boolean getInserted() {
        return inserted;
    }

    public void setInserted(Boolean inserted) {
        this.inserted = inserted;
    }

}
