/*************************************************
 * @功能简述: MaterialCouponAudienceCreateService 实现类
 * @项目名称: marketing cloud
 * @see:
 * @author: shanjingqi
 * @version: 0.0.1
 * @date: 2016/12/13
 * @复审人:
 * 
 *************************************************/
package cn.rongcapital.mkt.material.coupon.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.material.coupon.MaterialCouponCodeDao;
import cn.rongcapital.mkt.dao.material.coupon.MaterialCouponDao;
import cn.rongcapital.mkt.material.coupon.po.MaterialCoupon;
import cn.rongcapital.mkt.material.coupon.service.MaterialCouponAudienceCreateService;
import cn.rongcapital.mkt.service.AudienceListService;
import cn.rongcapital.mkt.vo.BaseOutput;

@Service
public class MaterialCouponAudienceCreateServiceImpl implements MaterialCouponAudienceCreateService {

    @Autowired
    MaterialCouponDao materialCouponDao;

    @Autowired
    MaterialCouponCodeDao materialCouponCodeDao;

    @Autowired
    AudienceListService audienceListService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public BaseOutput createTargetAudienceGroup(Long id, String audienceName, String blurSearch, String releaseStatus,
            String verifyStatus, String expireStatus) {
        BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
                ApiConstant.INT_ZERO, null);

        Map<String, Object> paramMap = new HashMap();
        paramMap.put("id", id);
        paramMap.put("user", blurSearch);
        paramMap.put("releaseStatus", releaseStatus);
        paramMap.put("verifyStatus", verifyStatus);
        if (StringUtils.isNotBlank(expireStatus)) {
            paramMap.put("expireStatus", expireStatus);
            paramMap.put("expireTime", new Date());
        }

        List<String> mobileList = materialCouponCodeDao.getCouponCodeVerifyUserInfoList(paramMap);

        List<Integer> idList = new ArrayList<>();
        idList.add(id.intValue());
        List<MaterialCoupon> mc = materialCouponDao.selectListByIdList(idList);
        Long taskHeadId =
                (!CollectionUtils.isEmpty(mc) && mc.get(0).getTaskId() != null) ? mc.get(0).getTaskId() : null;

        if (taskHeadId != null) {
            boolean flag = audienceListService.saveAudienceByMobile(taskHeadId, mobileList, audienceName);
            if (flag) {
                return result;
            } else {
                return new BaseOutput(ApiErrorCode.BIZ_ERROR_MATERIAL_COUPOON_CODE_CREATE_AUDIENCE_FAILED.getCode(),
                        ApiErrorCode.BIZ_ERROR_MATERIAL_COUPOON_CODE_CREATE_AUDIENCE_FAILED.getMsg(),
                        ApiConstant.INT_ZERO, null);
            }
        } else {
            return new BaseOutput(ApiErrorCode.BIZ_ERROR_MATERIAL_COUPOON_CODE_CREATE_AUDIENCE.getCode(),
                    ApiErrorCode.BIZ_ERROR_MATERIAL_COUPOON_CODE_CREATE_AUDIENCE.getMsg(), ApiConstant.INT_ZERO, null);
        }
    }
}
