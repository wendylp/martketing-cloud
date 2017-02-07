package cn.rongcapital.mkt.vo.in;

import cn.rongcapital.mkt.vo.BaseInput;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;

/**
 * Created by hiro on 17/2/6.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class CustomTagSaveToCategoryIn extends BaseInput{

    private String userToken;
    private String customTagCategoryId;
    private String customTagCategoryName;
    private ArrayList<CustomTagNameIn> customTagList;

    @JsonProperty("user_token")
    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    @JsonProperty("custom_tag_category_id")
    public String getCustomTagCategoryId() {
        return customTagCategoryId;
    }

    public void setCustomTagCategoryId(String customTagCategoryId) {
        this.customTagCategoryId = customTagCategoryId;
    }

    @JsonProperty("custom_tag_category_name")
    public String getCustomTagCategoryName() {
        return customTagCategoryName;
    }

    public void setCustomTagCategoryName(String customTagCategoryName) {
        this.customTagCategoryName = customTagCategoryName;
    }

    @JsonProperty("custom_tag_list")
    public ArrayList<CustomTagNameIn> getCustomTagList() {
        return customTagList;
    }

    public void setCustomTagList(ArrayList<CustomTagNameIn> customTagList) {
        this.customTagList = customTagList;
    }
}

