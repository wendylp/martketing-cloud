package cn.rongcapital.mkt.vo.out;

import java.math.BigDecimal;
import java.util.Date;

/*************************************************
 * @功能及特点的描述简述: BAS的event所需的VO
 * @author : nianjun.sun
 * @date : 2016-07-13
 *************************************************/
public class BusEventOut {

    /**
     * 事件类型
     */
    private String event;

    /**
     * 时间发生的时间
     */
    private String dateTime;

    /**
     * MID
     */
    private String mid;

    /**
     * 支付渠道
     */
    private String payChannel;

    /**
     * 支付宝账号
     */
    private String alipayNumber;

    /**
     * 账务流水号
     */
    private String accSerialNumber;

    /**
     * 业务流水号
     */
    private String serviceSerialNumber;

    /**
     * 商户订单号
     */
    private String busOrderId;

    /**
     * 商品名称
     */
    private String BusName;

    /**
     * 发生时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 支付状态
     */
    private String payStatus;

    /**
     * 对方账号
     */
    private String otherAccount;

    /**
     * 收入金额
     */
    private BigDecimal takeMoney;

}
