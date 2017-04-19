/*************************************************
 * @功能及特点的描述简述: message（例如该类是用来做什么的）
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：营销云系统
 * @author: 谢小良
 * @version: 版本v1.7
 * @date(创建、开发日期)：2017-4-13
 * @date(最后修改日期)：2017-4-13
 * @复审人：
 *************************************************/
package cn.rongcapital.mkt.webservice;

import javax.xml.rpc.holders.BooleanHolder;
import javax.xml.rpc.holders.StringHolder;
public class BBXCrmWSUtils {

    static String  crmVipWebServiceAddress;

    public static UpdateCouponResult UpdateVipCoupon(int vipId, int couponId, int actionId, double couponMoney, String canUseBeginDate, String canUseEndDate, String storeCode) throws Exception {

        BooleanHolder updateVipCouponResult = new BooleanHolder();
        StringHolder msgHolder = new StringHolder();


        cn.rongcapital.mkt.webservice.CrmVipCardWebServiceSoap12Stub binding = null;
        binding = (cn.rongcapital.mkt.webservice.CrmVipCardWebServiceSoap12Stub)
                    new cn.rongcapital.mkt.webservice.CrmVipCardWebServiceLocator(BBXCrmWSUtils.crmVipWebServiceAddress).getCrmVipCardWebServiceSoap12();
        // Time out after a minute
        binding.setTimeout(60000);
        // Test operation
        binding.updateVipCoupon(vipId, couponId, actionId, couponMoney, canUseBeginDate, canUseEndDate, storeCode, updateVipCouponResult, msgHolder);
        // TBD - validate results

        UpdateCouponResult result = new UpdateCouponResult();
        result.setSuccess(updateVipCouponResult.value);
        result.setMsg(msgHolder.value);
        return result;
    }


    public static void setCrmVipWebServiceAddress(String crmVipWebServiceAddress) {
        BBXCrmWSUtils.crmVipWebServiceAddress = crmVipWebServiceAddress;
    }
}
