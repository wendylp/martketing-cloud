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
import javax.ws.rs.core.MediaType;

import org.hibernate.validator.constraints.NotEmpty;
import org.jboss.resteasy.plugins.validation.hibernate.ValidateRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.service.CouponCodeListService;
import cn.rongcapital.mkt.service.MaterialCouponCodeVerifyListService;
import cn.rongcapital.mkt.service.MaterialCouponCountGetService;
import cn.rongcapital.mkt.service.MaterialCouponGeneralGetService;
import cn.rongcapital.mkt.service.MaterialCouponGetSystemTimeService;

import cn.rongcapital.mkt.service.MaterialCouponDeleteService;
import cn.rongcapital.mkt.service.MaterialCouponPutInGeneralService;
import cn.rongcapital.mkt.service.MaterialCouponPageListService;
import cn.rongcapital.mkt.service.MaterialCouponReleaseGeneralService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.MaterialCouponDeleteIn;

@Component
@Path(ApiConstant.API_PATH)
@Produces({ MediaType.APPLICATION_JSON })
@ValidateRequest
@PropertySource("classpath:${conf.dir}/application-api.properties")
public class CouponApi {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MaterialCouponCountGetService materialCouponCountGetService;

    @Autowired
    private MaterialCouponReleaseGeneralService materialCouponReleaseGeneralService;

    @Autowired
    private CouponCodeListService couponCodeListService;
	
    @Autowired
    private MaterialCouponPageListService couponPageListService;
    
    @Autowired
    private MaterialCouponGetSystemTimeService materialCouponGetSystemTimeService;

    @Autowired
    private MaterialCouponCodeVerifyListService materialCouponCodeVerifyListService;

    @Autowired
    private MaterialCouponGeneralGetService materialCouponGeneralGetService;

    @Autowired
    private MaterialCouponPutInGeneralService materialCouponPutInGeneralService;

    @Autowired
    private MaterialCouponDeleteService materialCouponDeleteService;

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

    /**
     * @author guozhenchao
     * @功能简述: 获取优惠码列表接口
     * @param fileTagUpdateIn
     * @return BaseOutput
     */
    @GET
    @Path("/mkt.materiel.coupon.code.list")
    @Consumes({ MediaType.APPLICATION_JSON })
    public BaseOutput couponCodeList(@NotEmpty @QueryParam("user_token") String userToken,
            @NotEmpty @QueryParam("ver") String ver, @NotNull @QueryParam("id") Long id,
            @QueryParam("index") Integer index, @QueryParam("size") Integer size) {
        return couponCodeListService.couponCodeList(id, index, size);
    }

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
    @Path("/mkt.material.coupon.releaseGeneral")
    public BaseOutput releaseGeneral(@NotEmpty @QueryParam("user_token") String userToken,
            @NotEmpty @QueryParam("ver") String version, @NotNull @QueryParam("id") Long id) {

        return materialCouponReleaseGeneralService.releaseGeneralById(id, userToken, version);
    }

    /**
     * @author liuhaizhan
     * @功能简述 优惠券删除
     * @param：入参请求
     * @return：message2
     */
    @POST
    @Path("/mkt.material.coupon.delete")
    @Consumes({ MediaType.APPLICATION_JSON })
    public Object Delete(@Valid MaterialCouponDeleteIn mcdi) {
        return materialCouponDeleteService.Delete(mcdi.getId());
    }

    /**
     * @author liuhaizhan
     * @功能简述 获取发放统计数据
     * @param：id 优惠券Id
     * @return：message2
     */
    @GET
    @Path("/mkt.material.coupon.putInGeneral")
    @Consumes({ MediaType.APPLICATION_JSON })
    public Object getPutInGeneral(@NotEmpty @QueryParam("user_token") String userToken,
            @NotEmpty @QueryParam("ver") String ver, @NotNull @QueryParam("id") Integer id) {
        return materialCouponPutInGeneralService.PutInGeneral(id);
    }

   
  


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
            @NotEmpty @QueryParam("ver") String ver, @NotEmpty @QueryParam("channel_code") String channelCode,
            @QueryParam("keyword") String keyword, @QueryParam("coupon_status") String couponStatus,
            @DefaultValue("1") @Min(1) @QueryParam("index") Integer index,
            @DefaultValue("10") @Min(1) @Max(100) @QueryParam("page_size") Integer size) throws Exception {
    
        return couponPageListService.getMaterialCouponListByKeyword(channelCode, couponStatus, keyword, index, size);
    }
    
    /**
     * @功能简述: 获取当前系统时间
     * @return BaseOutput
     * @author zhuxuelong
     */
    @GET
    @Path("/mkt.material.coupon.systemtime")
    @Consumes({MediaType.APPLICATION_JSON})
    public BaseOutput getSystemTime(@NotEmpty @QueryParam("user_token") String userToken,
                    @NotEmpty @QueryParam("ver") String ver) {
        return materialCouponGetSystemTimeService.getSystemTime();
    }
    
    /**
     * @功能简述: 获取优惠券概要信息
     * @param id 优惠券主键
     * @return BaseOutput
     * @author zhuxuelong
     */
    @GET
    @Path("/mkt.material.coupon.get")
    @Consumes({MediaType.APPLICATION_JSON})
    public BaseOutput getMaterialCouponGeneral(@NotEmpty @QueryParam("user_token") String userToken,
                    @NotEmpty @QueryParam("ver") String ver, @NotNull @QueryParam("id") Long id) {
        return materialCouponGeneralGetService.getMaterialCouponGeneral(id);
    }

    /**
     * @功能简述: 获取核销对账数据列表
     * 
     * @param id 优惠券主键
     * @param blur_search 查询关键字
     * @param receive_status 收到状态
     * @param verify_status 使用状态
     * @param expire_status 过期状态
     * @param index 页码
     * @param size 单页最大数量
     * @return BaseOutput
     * @author zhuxuelong
     */
    @GET
    @Path("/mkt.material.coupon.get")
    @Consumes({MediaType.APPLICATION_JSON})
    public BaseOutput listMaterialCouponCodeVerfy(@NotEmpty @QueryParam("user_token") String userToken,
                    @NotEmpty @QueryParam("ver") String ver, @NotNull @QueryParam("id") Long id,
                    @QueryParam("blur_search") String blurSearch, @QueryParam("receive_status") String receiveStatus,
                    @QueryParam("verify_status") String verifyStatus, @QueryParam("expire_status") String expireStatus,
                    @QueryParam("index") Integer index, @QueryParam("size") Integer size) {
        return materialCouponCodeVerifyListService.listMaterialCouponCodeVerfy(id, blurSearch, receiveStatus,
                        verifyStatus, expireStatus, index, size);
    }

}
