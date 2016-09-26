package cn.rongcapital.mkt.common.enums;

/**
 * Created by Yunfeng on 2016-9-26.
 */
public enum TagSourceEnum {

    SEGMENTATION_SOURCE_ACCESS(0,"细分人群"),
    CAMPAIGN_SOURCE_ACCESS(1,"活动编排"),
    CONTACT_DOCUMENT_SOURCE_ACCESS(2,"联系人档案"),
    FILE_SOURCE_ACCESS(3,"文件接入"),
    WECHAT_QRCODE_SOURCE_ACCESS(4,"微信二维码"),
    CUSTOM_TAG_SOURCE_ACCESS(5,"客户标签")
    ;


    TagSourceEnum(Integer tagSourceId, String tagSourceName) {
        this.tagSourceId = tagSourceId;
        this.tagSourceName = tagSourceName;
    }

    private Integer tagSourceId;
    private String tagSourceName;

    public Integer getTagSourceId() {
        return tagSourceId;
    }

    public void setTagSourceId(Integer tagSourceId) {
        this.tagSourceId = tagSourceId;
    }

    public String getTagSourceName() {
        return tagSourceName;
    }

    public void setTagSourceName(String tagSourceName) {
        this.tagSourceName = tagSourceName;
    }
}
