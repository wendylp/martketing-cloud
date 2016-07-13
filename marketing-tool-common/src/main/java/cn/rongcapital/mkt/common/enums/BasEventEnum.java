package cn.rongcapital.mkt.common.enums;

public enum BasEventEnum {

    EVENT("事件类型", "Event", ""), 
    DATE_TIME("事件发生的时间", "datatime", "createTime"), 
    MID("MID", "MID", ""), 
    PAY_CHANNEL("支付渠道", "PayChannel", "payChannel"), 
    ALI_PAY_NUMBER("支付宝账号", "AlipayNumber", ""), 
    ACC_SERIAL_NUMBER("账务流水号", "AccSerialNumber", ""), 
    SERVICE_SERIAL_NUMBER("业务流水号", "ServiceSerialNumber", ""), 
    BUS_ORDER_ID("商户订单号", "BusOrderId", ""),
    BUS_NAME("商品名称", "BusName", ""),
    START_TIME("发生时间", "startTime", ""),
    END_TIME("结束时间", "EndTime", "completeTime"),
    PAY_STATUS("支付状态", "PayStatus", "payStatus"),
    OTHER_ACCOUNT("对方账号", "OtherAccount", "counterAcct"),
    TAKE_MONEY("收入金额", "TakeMoney", "incomeAmt"),
    PAY_MONEY("支出金额", "PayMoney", "paidAmt"),
    ACC_BALANCE("账户余额", "AccBalance", "acctAmt"),
    REMARK("备注", "Remark", "comments"),
    DATA_SOURCES("数据来源", "DataSources", "source"),
    CHANNEL_CLASSIFY("渠道分类", "ChannelClassify", "channelType"),
    SHOP_CHANNEL_ID("购物渠道ID", "ShopChannelId", "channelId"),
    SHOP_CHANNEL("购物渠道", "ShopChannel", "channelName"),
    PAY_WAY("支付方式", "PayWay", "payType"),
    SHOP_TIME("消费时间", "ShopTime", "transTime"),
    BUSINESS_ID("商品ID", "BusinessID", "productId"),
    FORMAT("规格", "Format", "specification"),
    COLOR("颜色", "Color", "color"),
    DISCOUNT_TYPE("折扣类型", "DiscountType", "discountType"),
    DISCOUNT_MONEY("折扣金额", "DiscountMoney", "discountAmt"),
    PRICE("单价", "Price", "price"),
    NUMBER("数量", "Number", "amount"),
    STOCK_NUMBER("库存量", "StockNumber", "inventory"),
    BRAND_NAME("品牌", "BrandName", "brandName"),
    BRAND_ID("品牌ID", "BrandId", "brandId"),
    ONE_LEVEL_NAME("一级品类名称", "OnelevelName", "class1Name"),
    ONE_LEVEL_ID("一级品类名称ID", "OnelevelId", "class1Id"),
    TWO_LEVEL_NAME("二级品类名称", "TwolevelName", "class2Name"),
    TWO_LEVEL_ID("二级品类名称ID", "TwolevelId", "class2Id"),
    THREE_LEVEL_NAME("三级品类名称", "ThreelevelName", "class3Name"),
    THREE_LEVEL_ID("三级品类名称ID", "ThreelevelId", "class3Id"),
    FOUR_LEVEL_NAME("四级品类名称", "FourlevelName", "class4Name"),
    FOUR_LEVEL_ID("四级品类名称ID", "FourlevelId", "class4Id"),
    SHOP_PEOPLE("导购人员", "ShopPeople", "saleAssistance"),
    SHOP_PEOPLE_ID("导购人员ID", "ShopPeopleId", "saleAssistId"),
    BALANCE_PEOPLE("结算人员", "BalancePeople", "settlementClerk"),
    BALANCE_PEOPLE_ID("结算人员ID", "BalancePeopleId", "settlementClerkId"),
    LOGIN_TYPE("登录方式", "LoginType", "loginType"),
    LOGIN_TIME("登录时间", "LoginTime", "loginTime"),
    OUT_TIME("退出时间", "OutTime", "logoutTime"),
    LOGIN_IP("登录IP", "LoginIP", "loginIp"),
    LOGIN_DEVICE("登录设备", "LoginDevice", "loginDevice"),
    RESOLUTION_RATIO("分辨率", "ResolutionRatio", "resolutionRatio"),
    LOGIN_PAGE("登录页面", "LoginPage", "loginUrl"),
    CREATE_TIME("创建时间", "CreateTime", "createTime"),
    CLICK_TIME("点击时间", "ClickTime", "clickTime"),
    VIP_CARD_NUM("会员卡号", "VipCardNum", "memberId"),
    VIP_INTEGRAL("会员积分", "VipIntegral", "memberPoints"),
    VIP_LEVEL("会员等级", "VipLevel", "memberLevel"),
    OPEN_CARD_TIME("开卡时间", "OpenCardTime", "registTime"),
    CARD_BALANCE("卡内余额", "CardBalance", "cardAmt"),
    VALID_DAY("有效期（日）", "ValidDay", "expire"),

    ;

    private BasEventEnum(String basCNName, String basENName, String mcName) {
        this.basCNName = basCNName;
        this.basENName = basENName;
        this.mcName = mcName;
    }

    /**
     * BAS中的中文列名
     */
    private String basCNName;

    /**
     * BAS中的英文字段名
     */
    private String basENName;

    /**
     * Marketing Cloud中对象中的字段名
     */
    private String mcName;

    public String getBasCNName() {
        return basCNName;
    }

    public void setBasCNName(String basCNName) {
        this.basCNName = basCNName;
    }

    public String getBasENName() {
        return basENName;
    }

    public void setBasENName(String basENName) {
        this.basENName = basENName;
    }

    public String getMcName() {
        return mcName;
    }

    public void setMcName(String mcName) {
        this.mcName = mcName;
    }
}
