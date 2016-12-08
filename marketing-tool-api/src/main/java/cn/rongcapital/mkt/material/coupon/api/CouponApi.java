/*************************************************
 * @功能简述: API接口优惠码主类
 * @项目名称: marketing cloud
 * @see:
 * @author: 朱学龙
 * @version: 0.0.1
 * @date: 2016/12/06
 * @复审人:
 *************************************************/
package cn.rongcapital.mkt.material.coupon.api;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.hibernate.validator.constraints.NotEmpty;
import org.jboss.resteasy.plugins.validation.hibernate.ValidateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.service.MaterialCouponPageListService;
import cn.rongcapital.mkt.vo.BaseOutput;

@Component
@Path(ApiConstant.API_PATH)
@Produces({ MediaType.APPLICATION_JSON })
@ValidateRequest
@PropertySource("classpath:${conf.dir}/application-api.properties")
public class CouponApi {
    
    @Autowired
    private MaterialCouponPageListService couponPageListService;
    
    
    /**
     * @功能描述: message
     * @param userToken
     * @param ver
     * @param searchWord
     * @param channelType
     * @param index
     * @param size
     * @return
     * @throws Exception BaseOutput
     * @author xie.xiaoliang
     * @since 2016年12月7日
     */
    @GET
    @Path("/mkt.material.coupon.list")
    public BaseOutput getMaterialCouponListByKeyword(@NotEmpty @QueryParam("user_token") String userToken,
            @NotEmpty @QueryParam("ver") String ver, @NotEmpty @QueryParam("chanel_code") String chanelCode,
            @QueryParam("keyword") String keyword, @QueryParam("coupon_status") String couponStatus,
            @DefaultValue("1") @Min(1) @QueryParam("index") Integer index,
            @DefaultValue("10") @Min(1) @Max(100) @QueryParam("page_size") Integer size) throws Exception {


        return couponPageListService.getMaterialCouponListByKeyword(chanelCode, couponStatus, keyword, index, size);
    }

}
