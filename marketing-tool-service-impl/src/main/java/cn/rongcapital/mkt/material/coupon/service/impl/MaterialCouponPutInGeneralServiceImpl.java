/*************************************************
* @功能及特点的描述简述: 返回投放优惠券统计数量
* 该类被编译测试过
* @see （与该类关联的类）：
* @对应项目名称：MC
* @author: liuhaizhan
* @version: 版本
* @date(创建、开发日期)：2016年12月7日
* 最后修改日期：2016年12月7日
* @复审人:
*************************************************/
package cn.rongcapital.mkt.material.coupon.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.enums.MaterialCouponCodeReleaseStatusEnum;
import cn.rongcapital.mkt.common.enums.MaterialCouponCodeVerifyStatusEnum;
import cn.rongcapital.mkt.dao.material.coupon.MaterialCouponDao;
import cn.rongcapital.mkt.material.coupon.service.MaterialCouponPutInGeneralService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.out.CouponPutInGeneral;

@Service
public class MaterialCouponPutInGeneralServiceImpl implements MaterialCouponPutInGeneralService {

    /*
     * (non-Javadoc)
     * 
     * @see cn.rongcapital.mkt.service.MaterialCouponPutInGeneralService#PutInGeneral(long)
     */

    @Autowired
    private MaterialCouponDao materialCouponDao;

    @Override
    public Object PutInGeneral(long id) {
        // TODO Auto-generated method stub
        // return null;

        List<Map> data = materialCouponDao.getPutInCoupon(id);
        CouponPutInGeneral cpi = new CouponPutInGeneral();
        BaseOutput outPut = new BaseOutput(0, "success", 1, null);
        for (Map map : data) {
            cpi.setRestCount(Integer.valueOf(map.get("rest_count").toString()));
            doPutIndata(cpi, map);
        }
        List outCpI = new ArrayList();
        outCpI.add(cpi);
        outPut.setData(outCpI);
        return outPut;
    }

      
     /**
       @author liuhaizhan
     * @功能简述 返回投放统计实体
     * @param：param1 message1
     * @param：param2 message2
     * @return：message2
     */
    private void doPutIndata(CouponPutInGeneral cpi, Map map) {
        if (MaterialCouponCodeReleaseStatusEnum.RECEIVED.getCode().equals(map.get("status").toString())) {
            cpi.setReleasesuccessCount(Double.valueOf(map.get("tjcount").toString()).intValue());
        } else if (MaterialCouponCodeReleaseStatusEnum.UNRECEIVED.getCode().equals(map.get("status").toString())) {
            cpi.setReleaseFailCount(Double.valueOf(map.get("tjcount").toString()).intValue());
        } else if (MaterialCouponCodeVerifyStatusEnum.UNVERIFY.getCode().equals(map.get("status"))) {
            cpi.setUnverifyAmount(Double.valueOf(map.get("tjcount").toString()));
        } else {
            cpi.setVerifyAmount(Double.valueOf(map.get("tjcount").toString()));
        }

    }

}
