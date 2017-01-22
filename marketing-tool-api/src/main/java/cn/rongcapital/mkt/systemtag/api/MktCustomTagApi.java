/*************************************************
 * @功能简述: API接口 自定义标签
 * @项目名称: marketing cloud
 * @see:
 * @author:
 * @version:
 * @date:
 * @复审人:
 *************************************************/
package cn.rongcapital.mkt.systemtag.api;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.hibernate.validator.constraints.NotEmpty;
import org.jboss.resteasy.plugins.validation.hibernate.ValidateRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.service.CustomTagActionService;
import cn.rongcapital.mkt.service.CustomtagCategoryLessListService;
import cn.rongcapital.mkt.service.CustomtagCategoryListService;
import cn.rongcapital.mkt.service.CustomtagFuzzyNameListService;
import cn.rongcapital.mkt.service.CustomtagListService;
import cn.rongcapital.mkt.service.CustomtagQrcodeFuzzyListService;
import cn.rongcapital.mkt.service.TagCampaignFuzzyListService;
import cn.rongcapital.mkt.service.TagSegmentFuzzyListService;
import cn.rongcapital.mkt.vo.BaseOutput;

@Component
@Path(ApiConstant.API_PATH)
@Produces({MediaType.APPLICATION_JSON})
@ValidateRequest
public class MktCustomTagApi {
    private Logger logger = LoggerFactory.getLogger(getClass());


    @Autowired
    private CustomTagActionService customTagActionService;

    @Autowired
    private CustomtagCategoryListService customtagCategoryListService;

    @Autowired
    private CustomtagListService customtagListService;
    
    @Autowired
    private CustomtagFuzzyNameListService customtagFuzzyNameListService;
    
    @Autowired
    private CustomtagCategoryLessListService customtagCategoryLessListService;
    
    @Autowired
    private TagSegmentFuzzyListService tagSegmentFuzzyListService;
    
    @Autowired
    private TagCampaignFuzzyListService tagCampaignFuzzyListService;
    
    @Autowired
    private CustomtagQrcodeFuzzyListService customtagQrcodeFuzzyListService;

    /**
     * 功能描述：自定义分类列表
     * 接口：mkt.customtag.category.list
     *
     * @return
     */
    @GET
    @Path("/mkt.customtag.test")
    public BaseOutput customtagTest(@NotEmpty @QueryParam("user_token") String userToken) {
//        ArrayList<String> customTagList = new ArrayList<>();
//        customTagList.add("wangweiqiang1");
//        customTagList.add("wangweiqiang2");
//        customTagList.add("wangweiqiang3");
//        customTagList.add("wangweiqiang1");
//        customTagActionService.insertCustomTagListIntoDefaultCategory(customTagList);
        return new BaseOutput(ApiErrorCode.SUCCESS.getCode(),ApiErrorCode.SUCCESS.getMsg(),ApiConstant.INT_ZERO,null);
    }

    /**
     * 功能描述：自定义分类列表
     *
     * 接口：mkt.customtag.category.list
     * 
     * @return
     */
    @GET
    @Path("/mkt.customtag.category.list")
    public BaseOutput customtagCategoryListGet(@NotEmpty @QueryParam("method") String method,
            @NotEmpty @QueryParam("user_token") String userToken) {
        return customtagCategoryListService.customtagCategoryListGet();
    }


    /**
     * 功能描述：标签列表(分页， 分类名称展示)
     *
     * @param customTagCategoryId
     * @param index
     * @param size
     * @return
     */
    @GET
    @Path("/mkt.customtag.list")
    public BaseOutput customtagListGet(@NotEmpty @QueryParam("method") String method,
            @NotEmpty @QueryParam("user_token") String userToken,
            @NotEmpty @QueryParam("custom_tag_category_id") String customTagCategoryId,
            @DefaultValue("1") @Min(1) @QueryParam("index") Integer index,
            @DefaultValue("10") @Min(1) @Max(100) @QueryParam("size") Integer size) {
        return customtagListService.customtagListGet(customTagCategoryId,index,size);
    }
    
    /**
     * 功能描述：添加标签时， 查询已存在类似标签列表
     * 
     * @param customTagCategoryId
     * @param customTagName
     * @return
     */
    @GET
    @Path("/mkt.customtag.fuzzy.name.list")
    public BaseOutput customtagFuzzyNameListGet(@NotEmpty @QueryParam("method") String method,
            @NotEmpty @QueryParam("user_token") String userToken,
            @NotEmpty @QueryParam("custom_tag_category_id") String customTagCategoryId,
            @NotNull @QueryParam("custom_tag_name") String customTagName) {
        return customtagFuzzyNameListService.customtagFuzzyNameListGet(customTagCategoryId, customTagName);
    }
    
    /**
     * 功能描述：未分类标签添加到指定分类，分类列表查询(不显示未分类)
     * 
     * @return
     */
    @GET
    @Path("/mkt.customtag.category.less.list")
    public BaseOutput customtagCategoryLessListGet(@NotEmpty @QueryParam("method") String method,
            @NotEmpty @QueryParam("user_token") String userToken) {
        return customtagCategoryLessListService.customtagCategoryLessListGet();
    }
    
    /**
     * 功能描述：细分分析---标签搜索
     * 
     * 接口:mkt.tag.segment.fuzzy.list
     * 
     * @param name
     * @return
     */
    @GET
    @Path("/mkt.tag.segment.fuzzy.list")
    public BaseOutput tagSegmentFuzzyListGet(@NotEmpty @QueryParam("method") String method,
            @NotEmpty @QueryParam("user_token") String userToken, @NotNull @QueryParam("name") String name) {
        return tagSegmentFuzzyListService.tagSegmentFuzzyListGet(name);
    }
    
    
    /**
     * 功能描述：标签查询、 自定义标签查询 活动编排
     * 
     * 接口：mkt.tag.campaign.fuzzy.list
     * 
     * @param name
     * @return
     */
    @GET
    @Path("/mkt.tag.campaign.fuzzy.list")
    public BaseOutput tagCampaignFuzzyListService(@NotEmpty @QueryParam("method") String method,
            @NotEmpty @QueryParam("user_token") String userToken, @NotNull @QueryParam("name") String name) {
        return tagCampaignFuzzyListService.tagCampaignFuzzyListGet(name);
    }

    /**
     * 功能描述：微信二维码，搜索自定义标签列表
     * 
     * @param customTagName
     * @param index
     * @param size
     * @return
     */
    @GET
    @Path("/mkt.customtag.qrcode.fuzzy.list")
    public BaseOutput customtagQrcodeFuzzyListGet(@NotEmpty @QueryParam("method") String method,
            @NotEmpty @QueryParam("user_token") String userToken,
            @NotNull @QueryParam("custom_tag_name") String customTagName,
            @DefaultValue("1") @Min(1) @QueryParam("index") Integer index,
            @DefaultValue("10") @Min(1) @Max(100) @QueryParam("size") Integer size) {
        return customtagQrcodeFuzzyListService.customtagQrcodeFuzzyListGet(customTagName, index, size);
    }
}
