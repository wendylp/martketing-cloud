/*************************************************
 * @功能及特点的描述简述: CouponCodeDictionary实现类
 * 该类被编译测试过
 * @see （与该类关联的类）：cn.rongcapital.mkt.material.coupon.service.CouponCodeDictionary
 * @对应项目名称：营销云系统
 * @author: 谢小良
 * @version: 版本v1.6
 * @date(创建、开发日期)：2016-12-13 
 * @date(最后修改日期)：2016-12-13 
 * @复审人：
 *************************************************/
package cn.rongcapital.mkt.material.coupon.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.enums.MaterialCouponCodeReleaseStatusEnum;
import cn.rongcapital.mkt.common.enums.MaterialCouponCodeVerifyStatusEnum;
import cn.rongcapital.mkt.common.enums.MaterialCouponDictionaryTypeEnum;
import cn.rongcapital.mkt.common.enums.MaterialCouponExpiredEnum;
import cn.rongcapital.mkt.material.coupon.service.CouponCodeDictionaryService;
import cn.rongcapital.mkt.material.coupon.vo.out.CouponCodeDictionaryListOut;
import cn.rongcapital.mkt.material.coupon.vo.out.CouponCodeDictionaryOut;
import cn.rongcapital.mkt.vo.BaseOutput;

@Service
public class CouponCodeDictionaryServiceImpl implements CouponCodeDictionaryService {

    /*
     * @see
     * cn.rongcapital.mkt.material.coupon.service.CouponCodeDictionary#materialCouponCodeCheck(java.
     * lang.String)
     */
    @Override
    public CouponCodeDictionaryListOut materialCouponDictionary(String type) {
        CouponCodeDictionaryListOut baseOutput = new CouponCodeDictionaryListOut(ApiErrorCode.SUCCESS.getCode(),
                ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO);
        List<CouponCodeDictionaryOut> dataList = baseOutput.getCodeDictionaryOuts();
        if (MaterialCouponDictionaryTypeEnum.contains(type)) {
            MaterialCouponDictionaryTypeEnum typeEnum = MaterialCouponDictionaryTypeEnum.getByCode(type);
            switch (typeEnum) {
                case RECEIVED:
                    MaterialCouponCodeReleaseStatusEnum[] values = MaterialCouponCodeReleaseStatusEnum.values();
                    for (MaterialCouponCodeReleaseStatusEnum item : values) {
                        dataList.add(new CouponCodeDictionaryOut(item.getCode(), item.getDescription()));
                    }
                    break;
                case VERIFY:
                    MaterialCouponCodeVerifyStatusEnum[] statusValues = MaterialCouponCodeVerifyStatusEnum.values();
                    for (MaterialCouponCodeVerifyStatusEnum item : statusValues) {
                        dataList.add(new CouponCodeDictionaryOut(item.getCode(), item.getDescription()));
                    }
                    break;
                default:
                    MaterialCouponExpiredEnum[] expiredValues = MaterialCouponExpiredEnum.values();
                    for (MaterialCouponExpiredEnum item : expiredValues) {
                        dataList.add(new CouponCodeDictionaryOut(item.getCode(), item.getDescription()));
                    }
                    break;
            }
            baseOutput.setTotal(dataList.size());
            baseOutput.setTotalCount(dataList.size());
        } else {
            baseOutput = new CouponCodeDictionaryListOut(ApiErrorCode.PARAMETER_ERROR.getCode(),
                    ApiErrorCode.PARAMETER_ERROR.getMsg(), ApiConstant.INT_ZERO);
        }
        return baseOutput;
    }

}
