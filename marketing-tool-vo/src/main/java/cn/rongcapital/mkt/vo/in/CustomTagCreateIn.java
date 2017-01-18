package cn.rongcapital.mkt.vo.in;

import cn.rongcapital.mkt.vo.BaseInput;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.ArrayList;

/**
 * Created by byf on 1/17/17.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class CustomTagCreateIn extends BaseInput{

    @NotEmpty
    private String userToken;

    @NotEmpty
    private String customTagCategoryId;

    @NotEmpty
    private String customTagCategoryName;

    @NotEmpty
    private ArrayList<CustomTagProperty> customTagList = new ArrayList<CustomTagProperty>();

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
    public ArrayList<CustomTagProperty> getCustomTagList() {
        return customTagList;
    }

    public void setCustomTagList(ArrayList<CustomTagProperty> customTagList) {
        this.customTagList = customTagList;
    }
}
