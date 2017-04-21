/*************************************************
 * @功能及特点的描述简述: 贝贝熊优惠码增加接口类
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：营销云系统
 * @author: 谢小良
 * @version: 版本v1.7
 * @date(创建、开发日期)：2017-4-11
 * @date(最后修改日期)：2017-4-11
 * @复审人：
 *************************************************/
package cn.rongcapital.mkt.bbx.service;

import cn.rongcapital.mkt.material.coupon.vo.MaterialCouponCodeStatusUpdateVO;

import java.util.List;

public interface BbxCouponCodeAddService {

    void addCouponCodeToBBX(List<MaterialCouponCodeStatusUpdateVO> voList);


    void verifyCouponCode();

}
