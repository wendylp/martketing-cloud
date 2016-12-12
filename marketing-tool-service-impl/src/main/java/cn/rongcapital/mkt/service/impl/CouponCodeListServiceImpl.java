/*************************************************
 * @功能简述: 接口mkt.materiel.coupon.code.list的service接口实现类
 * @项目名称: marketing cloud
 * @see: 
 * @author: guozhenchao
 * @version: 0.0.1
 * @date: 2016/12/7
 * @复审人: 
*************************************************/

package cn.rongcapital.mkt.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.MaterialCouponCodeDao;
import cn.rongcapital.mkt.po.MaterialCouponCode;
import cn.rongcapital.mkt.service.CouponCodeListService;
import cn.rongcapital.mkt.vo.BaseOutput;

@Service
public class CouponCodeListServiceImpl implements CouponCodeListService {

    @Autowired
    private MaterialCouponCodeDao materialCouponCodeDao;

    @Override
    public BaseOutput couponCodeList(Long id, Integer index, Integer size) {
        MaterialCouponCode code = new MaterialCouponCode();
        code.setCouponId(id);
        code.setStartIndex(index);
        code.setPageSize(size);
        List<MaterialCouponCode> codeList = materialCouponCodeDao.selectList(code);
        BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO, null);
        for(MaterialCouponCode materialCouponCode : codeList){
            result.getData().add(materialCouponCode);
        }
        result.setTotal(result.getData().size());
        result.setTotalCount(codeList.size());
        return result;
    }

    @Override
    public BaseOutput couponIssuedCodeList(Long id, Integer index, Integer size) {
        MaterialCouponCode code = new MaterialCouponCode();
        code.setCouponId(id);
        code.setStartIndex(index);
        code.setPageSize(size);
        List<MaterialCouponCode> codeList = materialCouponCodeDao.selectIssuedList(code);
        BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO, null);
        for(MaterialCouponCode materialCouponCode : codeList){
            result.getData().add(materialCouponCode);
        }
        result.setTotal(result.getData().size());
        result.setTotalCount(codeList.size());
        return result;
    }
}
