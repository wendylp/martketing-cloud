package cn.rongcapital.mkt.vo.out;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by Yunfeng on 2016-6-14.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class UploadFileAccordTemplateOut {
    private String dataTopic;
    private String dataRows;
    private String unrecognizeFields;

    @JsonProperty("data_topic")
    public String getDataTopic() {
        return dataTopic;
    }

    public void setDataTopic(String dataTopic) {
        this.dataTopic = dataTopic;
    }

    @JsonProperty("data_rows")
    public String getDataRows() {
        return dataRows;
    }

    public void setDataRows(String dataRows) {
        this.dataRows = dataRows;
    }

    @JsonProperty("unrecognize_fields")
    public String getUnrecognizeFields() {
        return unrecognizeFields;
    }

    public void setUnrecognizeFields(String unrecognizeFields) {
        this.unrecognizeFields = unrecognizeFields;
    }
}
