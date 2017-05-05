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
import cn.rongcapital.mkt.po.SmsTaskDetail;

import java.util.List;

public interface BbxCouponCodeAddService {

    void addCouponCodeToBBX(List<MaterialCouponCodeStatusUpdateVO> voList);

    /**
     * 完成优惠券绑定关系入到中间关系表的操作，为同步绑定优惠券webservice做准备
     * @param list
     * @param campaignHeadId
     * @param smsTaskHeadId
     * @param campaignItemId
     */
    void addCouponCodeToBBX(List<SmsTaskDetail> list, Integer campaignHeadId, Long smsTaskHeadId,String campaignItemId);

    /**
     * 对已经完成优惠券绑定同步的信息，进行发送短信
     */
    public void synchSuccessCouponSendMsg();

    void verifyCouponCode();

}
