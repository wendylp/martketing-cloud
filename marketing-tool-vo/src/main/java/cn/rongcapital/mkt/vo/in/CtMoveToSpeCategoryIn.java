package cn.rongcapital.mkt.vo.in;

import cn.rongcapital.mkt.vo.BaseInput;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by hiro on 17/2/7.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class CtMoveToSpeCategoryIn extends BaseInput{

    private String userToken;
    private String customTagOldCategoryId;
    private String customTagOldCategoryName;
    private String customTagNewCategoryId;
    private String customTagNewCategoryName;
    private String customTagId;
    private String customTagName;

    @JsonProperty("user_token")
    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    @JsonProperty("custom_tag_old_category_id")
    public String getCustomTagOldCategoryId() {
        return customTagOldCategoryId;
    }

    public void setCustomTagOldCategoryId(String customTagOldCategoryId) {
        this.customTagOldCategoryId = customTagOldCategoryId;
    }

    @JsonProperty("custom_tag_old_category_name")
    public String getCustomTagOldCategoryName() {
        return customTagOldCategoryName;
    }

    public void setCustomTagOldCategoryName(String customTagOldCategoryName) {
        this.customTagOldCategoryName = customTagOldCategoryName;
    }

    @JsonProperty("custom_tag_new_category_id")
    public String getCustomTagNewCategoryId() {
        return customTagNewCategoryId;
    }

    public void setCustomTagNewCategoryId(String customTagNewCategoryId) {
        this.customTagNewCategoryId = customTagNewCategoryId;
    }

    @JsonProperty("custom_tag_new_category_name")
    public String getCustomTagNewCategoryName() {
        return customTagNewCategoryName;
    }

    public void setCustomTagNewCategoryName(String customTagNewCategoryName) {
        this.customTagNewCategoryName = customTagNewCategoryName;
    }

    @JsonProperty("custom_tag_id")
    public String getCustomTagId() {
        return customTagId;
    }

    public void setCustomTagId(String customTagId) {
        this.customTagId = customTagId;
    }

    @JsonProperty("custom_tag_name")
    public String getCustomTagName() {
        return customTagName;
    }

    public void setCustomTagName(String customTagName) {
        this.customTagName = customTagName;
    }
}
