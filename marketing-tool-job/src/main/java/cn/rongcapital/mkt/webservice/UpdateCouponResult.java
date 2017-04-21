/*************************************************
 * @功能及特点的描述简述: 同步优惠券结果
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

public class UpdateCouponResult {
    private Boolean success;
    private String msg;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
