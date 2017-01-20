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
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.service.CustomTagActionService;
import org.hibernate.validator.constraints.NotEmpty;
import org.jboss.resteasy.plugins.validation.hibernate.ValidateRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.service.CustomtagCategoryListService;
import cn.rongcapital.mkt.service.CustomtagListService;
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

}
