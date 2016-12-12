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
import cn.rongcapital.mkt.service.CustomTagDeleteService;
import cn.rongcapital.mkt.service.CustomTagGetService;
import cn.rongcapital.mkt.service.TagDownloadCustomAudienceService;
import cn.rongcapital.mkt.service.TagSystemFlagSetService;
import cn.rongcapital.mkt.service.TagSystemListGetService;
import cn.rongcapital.mkt.service.TagSystemTagcountService;
import cn.rongcapital.mkt.service.TaggroupSystemListGetService;
import cn.rongcapital.mkt.service.TaggroupSystemMenulistGetService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.CustomTagDeleteIn;
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
    
    @Autowired
    private TagSystemTagcountService tagSystemTagcountService;
    
    @Autowired
    private CustomTagGetService customTagGetService;

    @Autowired
    private CustomTagDeleteService customTagDeleteService;
    
    @Autowired
    private TagSystemListGetService tagSystemListGetService;
    
    @Autowired
    private TagDownloadCustomAudienceService tagDownloadCustomAudienceService;


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
    
    /**
     * @功能简述: 获取系统标签总数量
     * @param method
     * @param userToken
     * @return BaseOutput
     */
    @GET
    @Path("/mkt.tag.system.tagcount.get")
    public BaseOutput getTagcount(@NotEmpty @QueryParam("method") String method,
            @NotEmpty @QueryParam("user_token") String userToken) {
        //tagSystemTagcountService.getTagcount(method, userToken);
        return tagSystemTagcountService.getMonggTagcount(method, userToken);
    }

    /**
     * @功能简述 : 获取自定义标签列表
     * @param: String
     *             method, String userToken, Integer index, Integer size
     * @return: Object
     */
    @GET
    @Path("/mkt.tag.custom.list.get")
    public BaseOutput getCustomTagList(@NotEmpty @QueryParam("method") String method,
            @NotEmpty @QueryParam("user_token") String userToken, @QueryParam("index") Integer index,
            @QueryParam("size") Integer size) {
        return customTagGetService.getCustomTagList(method, userToken, index, size);
    }

    /**
     * @功能简述 : 删除某个自定义标签
     * @param: String
     *             method, String userToken, Integer tag_id
     * @return: Object
     */
    @POST
    @Path("mkt.tag.custom.delete")
    @Consumes({ MediaType.APPLICATION_JSON })
    public BaseOutput deleteCustomTag(@Valid CustomTagDeleteIn body, @Context SecurityContext securityContext) {
        return customTagDeleteService.deleteCustomTag(body);
    }

    /**
     * @功能简述 : 根据自定义标签下载覆盖的人群
     * @param: String
     *             userToken, Integer tag_id
     * @return: Object
     */
    @GET
    @Path("mkt.tag.custom.audience.download")
    @Consumes({ MediaType.APPLICATION_JSON })
    public BaseOutput downloadCustomAudience(@NotEmpty @QueryParam("method") String method,
            @NotNull @QueryParam("tag_id") String tagId) {
        return tagDownloadCustomAudienceService.downloadCustomAudience(tagId);
    }


    /**
     * 获取系统标签内容列表
     * 
     * @param method
     * @param userToken
     * @param tagGroupId
     * @param index
     * @param size
     * @return BaseOutput
     */
    @GET
    @Path("/mkt.tag.system.list.get")
    public BaseOutput getTagcountByParentGroupId(@NotEmpty @QueryParam("method") String method,
            @NotEmpty @QueryParam("user_token") String userToken,
            @NotNull @QueryParam("tag_group_id") String tagGroupId, @QueryParam("index") Integer index,
            @QueryParam("size") Integer size) {
        //tagSystemListGetService.getTagcount(method, userToken, tagGroupId, index, size);
        return tagSystemListGetService.getMongoTagList(method, userToken, tagGroupId, index, size);
    }

}
