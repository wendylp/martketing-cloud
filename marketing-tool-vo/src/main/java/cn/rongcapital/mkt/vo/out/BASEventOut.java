package cn.rongcapital.mkt.vo.out;

import java.math.BigDecimal;
import java.util.Date;

/*************************************************
 * @功能及特点的描述简述: BAS的event所需的VO
 * @author : nianjun.sun
 * @date : 2016-07-13
 *************************************************/
public class BASEventOut {

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

    /**
     * 支出金额
     */
    private BigDecimal payMoney;

    /**
     * 账户余额
     */
    private BigDecimal accBalance;

    /**
     * 备注
     */
    private String remark;

    /**
     * 数据来源
     */
    private String dataSources;

    /**
     * 渠道分类
     */
    private String channelClassify;

    /**
     * 购物渠道ID
     */
    private String shopChannelId;

    /**
     * 购物渠道
     */
    private String shopChannel;

    /**
     * 支付方式
     */
    private String payWay;

    /**
     * 消费时间
     */
    private Date shopTime;

    /**
     * 商品ID
     */
    private String businessId;

    /**
     * 规格
     */
    private String format;

    /**
     * 颜色
     */
    private String color;

    /**
     * 折扣类型
     */
    private String discountType;

    /**
     * 折扣金额
     */
    private BigDecimal discountMoney;

    /**
     * 单价
     */
    private BigDecimal price;

    /**
     * 数量
     */
    private int number;

    /**
     * 库存量
     */
    private int stockNumber;

    /**
     * 品牌
     */
    private String brandName;

    /**
     * 品牌Id
     */
    private String brandId;

    /**
     * 一级品类名称
     */
    private String onelevelName;

    /**
     * 一级品类名称Id
     */
    private String onelevelId;

    /**
     * 二级品类名称
     */
    private String twolevelName;

    /**
     * 二级品类名称Id
     */
    private String twolevelId;

    /**
     * 三级品类名称
     */
    private String threelevelName;

    /**
     * 三级品类名称Id
     */
    private String threelevelId;

    /**
     * 四级品类名称
     */
    private String fourlevelName;

    /**
     * 四级品类名称Id
     */
    private String fourlevelId;



}
