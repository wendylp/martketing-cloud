package cn.rongcapital.mkt.vo.in;

import cn.rongcapital.mkt.vo.BaseInput;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by hiro on 17/2/7.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class CustomTagNameEditIn extends BaseInput{

    private String userToken;
    private String customTagCategoryId;
    private String customTagCategoryName;
    private String customTagId;
    private String customTagOldName;
    private String customTagNewName;

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

    @JsonProperty("custom_tag_id")
    public String getCustomTagId() {
        return customTagId;
    }

    public void setCustomTagId(String customTagId) {
        this.customTagId = customTagId;
    }

    @JsonProperty("custom_tag_old_name")
    public String getCustomTagOldName() {
        return customTagOldName;
    }

    public void setCustomTagOldName(String customTagOldName) {
        this.customTagOldName = customTagOldName;
    }

    @JsonProperty("custom_tag_new_name")
    public String getCustomTagNewName() {
        return customTagNewName;
    }

    public void setCustomTagNewName(String customTagNewName) {
        this.customTagNewName = customTagNewName;
    }
}
