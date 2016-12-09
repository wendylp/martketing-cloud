/*************************************************
 * @功能简述: API接口 系统标签
 * @项目名称: marketing cloud
 * @see:
 * @author:
 * @version:
 * @date:
 * @复审人:
 *************************************************/
package cn.rongcapital.mkt.systemtag.api;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import org.hibernate.validator.constraints.NotEmpty;
import org.jboss.resteasy.plugins.validation.hibernate.ValidateRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.service.TagSystemFlagSetService;
import cn.rongcapital.mkt.service.TaggroupSystemListGetService;
import cn.rongcapital.mkt.service.TaggroupSystemMenulistGetService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.TagSystemFlagSetIn;

@Component
@Path(ApiConstant.API_PATH)
@Produces({MediaType.APPLICATION_JSON})
@ValidateRequest
public class MktSystemTagApi {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private TaggroupSystemListGetService taggroupSystemListGetService;
    
    @Autowired
    private TagSystemFlagSetService tagSystemFlagSetService;
    
    @Autowired
    private TaggroupSystemMenulistGetService taggroupSystemMenulistGetService;


    /**
     * 获取系统标签组列表
     * 
     * @param method
     * @param userToken
     * @param tagGroupId
     * @param index
     * @param size
     * @return BaseOutput
     */
    @GET
    @Path("/mkt.taggroup.system.list.get")
    public BaseOutput getTagGroupByParentGroupId(@NotEmpty @QueryParam("method") String method,
            @NotEmpty @QueryParam("user_token") String userToken,
            @NotNull @QueryParam("tag_group_id") String tagGroupId, @QueryParam("index") Integer index,
            @QueryParam("size") Integer size) {
        // taggroupSystemListGetService.getTagGroupByParentGroupId(method, userToken, tagGroupId,
        // index, size);
        return taggroupSystemListGetService.getMongoTagRecommendByTagTreeId(method, userToken, tagGroupId, index, size);
    }
    
    /**
     * 推荐标签（设置）
     *
     * @param body
     * @param securityContext
     * @return
     * @author shuiyangyang
     * @Date 2016-11-08
     */
    @POST
    @Path("/mkt.tag.system.flag.set")
    @Consumes({MediaType.APPLICATION_JSON})
    public BaseOutput tagSystemFlagSet(@Valid TagSystemFlagSetIn body,
                    @Context SecurityContext securityContext) {
        return tagSystemFlagSetService.updateFlag(body, securityContext);
    }
    
    /**
     * @功能简述: 获取系统标签组列表
     * @param method
     * @param userToken
     * @param index
     * @param size
     * @return BaseOutput
     */
    @GET
    @Path("/mkt.taggroup.system.menulist.get")
    public BaseOutput getTaggroupSystemMenulist(@NotEmpty @QueryParam("method") String method,
            @NotEmpty @QueryParam("user_token") String userToken, @QueryParam("index") Integer index,
            @QueryParam("size") Integer size) {
        //taggroupSystemMenulistGetService.getTaggroupSystemMenulist(method, userToken, index, size);
        return taggroupSystemMenulistGetService.getMonggTagTreelist(method, userToken, index, size);
    }

}
