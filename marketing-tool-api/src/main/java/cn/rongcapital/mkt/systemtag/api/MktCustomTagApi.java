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

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import cn.rongcapital.mkt.service.*;
import cn.rongcapital.mkt.vo.in.*;
import org.hibernate.validator.constraints.NotEmpty;
import org.jboss.resteasy.plugins.validation.hibernate.ValidateRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.tag.service.CustomtagCategoryCreateService;
import cn.rongcapital.mkt.tag.vo.in.CustomTagCategoryIn;
import cn.rongcapital.mkt.vo.BaseOutput;

import java.util.ArrayList;

@Component
@Path(ApiConstant.API_PATH)
@Produces({ MediaType.APPLICATION_JSON })
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

	@Autowired
	private CustomtagCategoryCreateService customtagCategoryCreateService;
	
	@Autowired
	private CustomtagAllCountService customtagAllCountService;

	@Autowired
	private CreateCustomTagToCategoryService createCustomTagToCategoryService;

	@Autowired
	private CustomTagMoveToSpecifyCategoryService customTagMoveToSpecifyCategoryService;

	@Autowired
	private CustomTagNameEditService customTagNameEditService;

	@Autowired
	private CustomTagCategoryDeleteService customTagCategoryDeleteService;

	@Autowired
	private CustomTagToDeleteService customTagToDeleteService;
	/**
	 * 功能描述：自定义分类列表 接口：mkt.customtag.category.list
	 *
	 * @return
	 */
	@GET
	@Path("/mkt.customtag.test")
	public BaseOutput customtagTest(@NotEmpty @QueryParam("user_token") String userToken) {
		 ArrayList<String> customTagList = new ArrayList<>();
		 customTagList.add("wangweiqiang15");
		 customTagList.add("wangweiqiang5");
		 customTagList.add("wangweiqiang6");
		 customTagList.add("wangweiqiang4");
		 customTagActionService.insertCustomTagListIntoDefaultCategory(customTagList);
		return new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO,
				null);
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
	 * 功能描述：创建、编辑自定义标签的分类
	 * 
	 * @param body
	 * @return BaseOutput
	 */
	@POST
	@Path("/mkt.customtag.category.create")
	@Consumes({ MediaType.APPLICATION_JSON })
	public BaseOutput createCustomtagCategory(@Valid CustomTagCategoryIn body,
			@Context SecurityContext securityContext) {
		return customtagCategoryCreateService.updateCustomtagCategory(body, securityContext);
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
		return customtagListService.customtagListGet(customTagCategoryId, index, size);
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
	    return customtagQrcodeFuzzyListService.customtagQrcodeFuzzyListGet(customTagName, null, null);
	}
	
    /**
     * 功能描述：自定义标签个数
     * 
     * @return
     */
    @GET
    @Path("/mkt.customtag.all.count")
    public BaseOutput customtagAllCount(@NotEmpty @QueryParam("method") String method,
            @NotEmpty @QueryParam("user_token") String userToken) {
        return customtagAllCountService.customtagAllCount();
    }

	/**
	 * 功能描述：保存自定义标签到分类
	 *
	 * @param customTagSaveToCategoryIn
	 * @param securityContext
	 * @return BaseOutput
	 */
	@POST
	@Path("/mkt.customtag.create.tocategory")
	@Consumes({ MediaType.APPLICATION_JSON })
	public BaseOutput createCustomTagToCategory(@Valid CustomTagSaveToCategoryIn customTagSaveToCategoryIn,
											  @Context SecurityContext securityContext) {
		return createCustomTagToCategoryService.createCustomTagToCategory(customTagSaveToCategoryIn);
	}

	/**
	 * 功能描述：移动未分类下的某个标签到特定的自定义分类
	 *
	 * @param ctMoveToSpeCategoryIn
	 * @param securityContext
	 * @return BaseOutput
	 */
	@POST
	@Path("/mkt.customtag.add.tocategory")
	@Consumes({ MediaType.APPLICATION_JSON })
	public BaseOutput moveCustomTagToSpecifyCategory(@Valid CtMoveToSpeCategoryIn ctMoveToSpeCategoryIn,
												@Context SecurityContext securityContext) {
		return customTagMoveToSpecifyCategoryService.moveCustomTagToSpecifyCategory(ctMoveToSpeCategoryIn);
	}

	/**
	 * 功能描述：编辑标签名称
	 *
	 * @param customTagNameEditIn
	 * @param securityContext
	 * @return BaseOutput
	 */
	@POST
	@Path("/mkt.customtag.edit")
	@Consumes({ MediaType.APPLICATION_JSON })
	public BaseOutput editCustomTagName(@Valid CustomTagNameEditIn customTagNameEditIn,
										@Context SecurityContext securityContext) {
		return customTagNameEditService.editCustomTagName(customTagNameEditIn);
	}

	/**
	 * 功能描述：删除自定义标签分类
	 *
	 * @param customTagCategoryDeleteIn
	 * @param securityContext
	 * @return BaseOutput
	 */
	@POST
	@Path("/mkt.customtag.category.delete")
	@Consumes({ MediaType.APPLICATION_JSON })
	public BaseOutput deleteCustomTagCategory(@Valid CustomTagCategoryDeleteIn customTagCategoryDeleteIn,
										@Context SecurityContext securityContext) {
		return customTagCategoryDeleteService.deleteCustomTagCategory(customTagCategoryDeleteIn);
	}

	/**
	 * 功能描述：删除自定义标签
	 *
	 * @param customTagToDeleteIn
	 * @param securityContext
	 * @return BaseOutput
	 */
	@POST
	@Path("/mkt.customtag.delete")
	@Consumes({ MediaType.APPLICATION_JSON })
	public BaseOutput deleteCustomTag(@Valid CustomTagToDeleteIn customTagToDeleteIn,
											  @Context SecurityContext securityContext) {
		return customTagToDeleteService.deleteCustomTag(customTagToDeleteIn);
	}
}
