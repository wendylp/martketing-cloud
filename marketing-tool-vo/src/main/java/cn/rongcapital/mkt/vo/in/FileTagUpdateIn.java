package cn.rongcapital.mkt.vo.in;

import cn.rongcapital.mkt.vo.BaseInput;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;

/**
 * Created by Yunfeng on 2016-6-25.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class FileTagUpdateIn extends BaseInput{

    private String fileUnique;
    private ArrayList<String> tag_names;

    @JsonProperty("file_unique")
    public String getFileUnique() {
        return fileUnique;
    }

    public void setFileUnique(String fileUnique) {
        this.fileUnique = fileUnique;
    }

    @JsonProperty("tag_names")
    public ArrayList<String> getTag_names() {
        return tag_names;
    }

    public void setTag_names(ArrayList<String> tag_names) {
        this.tag_names = tag_names;
    }
}
