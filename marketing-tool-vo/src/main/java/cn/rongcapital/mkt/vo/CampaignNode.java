/*************************************************
 * @功能简述: 活动任务节点
 * @项目名称: marketing cloud
 * @see:
 * @author: zhuxuelong
 * @version: 0.0.1
 * @date: 2017/3/1
 * @复审人:
 *************************************************/
package cn.rongcapital.mkt.vo;


public class CampaignNode {

    private Long campaignHeadId;

    private String itemId;
    
    //新增一些生日属性设置
    
    private Integer caringTime;

    public Integer getCaringTime() {
        return caringTime;
    }

    public void setCaringTime(Integer caringTime) {
        this.caringTime = caringTime;
    }

    public Long getCampaignHeadId() {
        return campaignHeadId;
    }

    public void setCampaignHeadId(Long campaignHeadId) {
        this.campaignHeadId = campaignHeadId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

}
