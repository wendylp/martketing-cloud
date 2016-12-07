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

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.hibernate.validator.constraints.NotEmpty;
import org.jboss.resteasy.plugins.validation.hibernate.ValidateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.service.MaterialCouponCountGetService;
import cn.rongcapital.mkt.vo.BaseOutput;

@Component
@Path(ApiConstant.API_PATH)
@Produces({MediaType.APPLICATION_JSON})
@ValidateRequest
public class CouponApi {

    @Autowired
    private MaterialCouponCountGetService materialCouponCountGetService;

    /**
     * 获取指定条件的优惠券的数量
     * 
     * 接口：mkt.material.coupon.counts
     * 
     * @param user_token
     * @param ver
     * @param chanel_code
     * @param keyword
     * @return BaseOutput
     * @author zhuxuelong
     * @Date 2016-12-06
     */
    @GET
    @Path("/mkt.material.coupon.counts")
    public BaseOutput getWechatAssetTypeCount(@NotEmpty @QueryParam("user_token") String userToken,
                    @NotEmpty @QueryParam("ver") String ver, @QueryParam("chanel_code") String chanelCode,
                    @QueryParam("keyword") String keyword) throws Exception {
        return materialCouponCountGetService.getMaterialCouponCount(chanelCode, keyword);
    }
}
