/*************************************************
 * @功能简述: 优惠码状态枚举类
 * @项目名称: marketing cloud
 * @see:
 * @author: zhuxuelong
 * @version: 0.0.1
 * @date: 2016/12/06
 * @复审人:
 *************************************************/
package cn.rongcapital.mkt.common.enums;

public enum CouponStatusEnum {

    COUPONSTATUS_UNUSED("unused", "未发布"),
    COUPONSTATUS_USED("used", "已占用"),
    COUPONSTATUS_RELEASING("releasing", "投放中"),
    COUPONSTATUS_RELEASED("released", "已投放");

    private CouponStatusEnum(String code, String status) {
        this.code = code;
        this.status = status;
    }

    private String code;

    private String status;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static String getStatusByCode(String code) {
        CouponStatusEnum[] couponStatusEnums = CouponStatusEnum.values();
        for (CouponStatusEnum couponStatusEnum : couponStatusEnums) {
            if (couponStatusEnum.getCode().equals(code)) {
                return couponStatusEnum.getStatus();
            }
        }
        return "";
    }
}