/*************************************************
 * @功能简述: API接口通用主类
 * @项目名称: marketing cloud
 * @see: 
 * @author: 宋世涛
 * @version: 0.0.1
 * @date: 2016/5/16
 * @复审人: 
*************************************************/

package cn.rongcapital.mkt.data.api;

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
import cn.rongcapital.mkt.service.MainBasicInfoGetService;
import cn.rongcapital.mkt.vo.BaseOutput;

@Component
@Path(ApiConstant.API_PATH)
@Produces({ MediaType.APPLICATION_JSON })
@ValidateRequest
public class MktDataApi {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private MainBasicInfoGetService mainBasicInfoGetService;

	/**
	 * @功能简述: 获取某条主数据详细信息
	 * @param userToken
	 * @param contactId
	 * @return BaseOutput
	 */
	@GET
	@Path("/mkt.data.main.basicinfo.get")
	public BaseOutput getPartyBehaviorByCondition(@NotEmpty @QueryParam("user_token") String userToken,
			@NotNull @QueryParam("contact_id") Integer contactId, @NotNull @QueryParam("md_type") Integer dataType) {
		return mainBasicInfoGetService.getMainBasicInfo(contactId, dataType, userToken);
	}
}
