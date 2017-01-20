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

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.po.mongodb.CustomTag;
import cn.rongcapital.mkt.service.CustomTagActionService;
import org.hibernate.validator.constraints.NotEmpty;
import org.jboss.resteasy.plugins.validation.hibernate.ValidateRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.service.CustomtagCategoryListService;
import cn.rongcapital.mkt.vo.BaseOutput;

import java.util.ArrayList;
import java.util.List;

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

}
