package cn.rongcapital.mkt.segment.api;

import javax.validation.constraints.NotNull;
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
import cn.rongcapital.mkt.service.SegmentAudienctAnalysisService;
import cn.rongcapital.mkt.service.TagGroupLimitService;
import cn.rongcapital.mkt.vo.BaseOutput;

@Component
@Path(ApiConstant.API_PATH)
@Produces({ MediaType.APPLICATION_JSON })
@ValidateRequest
public class MktSegmentApi {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private TagGroupLimitService tagGroupLimitService;
	
	@Autowired
	private SegmentAudienctAnalysisService segmentAudienctAnalysisService;
	
	/**
	 * 获取创建联系人表单界面中，右侧的显示列表
	 * 
	 * @param userToken
	 * @param var
	 * @param contactId
	 * @return
	 */
	@GET
	@Path("mkt.taggroup.limit.get")
	public BaseOutput getContactKeyList(@NotEmpty @QueryParam("user_token") String userToken,
			@NotEmpty @QueryParam("ver") String ver, @QueryParam("source") String source) {
		return tagGroupLimitService.getTagGroupLimit(source);
	}
	
	/**
	 * 细分管理分析
	 * 
	 * @param userToken
	 * @param var
	 * @param contactId
	 * @return
	 */
	@GET
	@Path("mkt.segment.audienct.analysis.get")
	public BaseOutput getSegmentAnalysis(@NotEmpty @QueryParam("user_token") String userToken,
			@NotEmpty @QueryParam("ver") String ver,
			@NotEmpty @QueryParam("tag_id") String tagId,
			@NotNull @QueryParam("segment_head_id") Integer segmentHeadId){
		return segmentAudienctAnalysisService.getSegmentAudienctAnalysis(tagId, segmentHeadId);
	}
	
}
