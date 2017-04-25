package cn.rongcapital.mkt.po.mongodb;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * 
 * @author LiuQ
 * @email Liuqi@rongcapital.cn
 */
@Document(collection = "campaign_detail")
public class CampaignDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	@Field(value = "campaign_id")
	private Integer campaignId;
	@Field(value = "campaign_name")
	private String campaignName;
	@Field(value = "campaign_start_time")
	private Date campaignStartTime;
	@Field(value = "campaign_end_time")
	private Date campaignEndTime;
	@Field(value = "campaign_member_num")
	private Integer campaignMemberNum;
	@Field(value = "item_id")
	private String itemId;
	@Field(value = "item_type")
	private Integer itemType;
	@Field(value = "is_have_sub_table")
	private Integer isHaveSubTable = 0;
	@Field(value = "update_time")
	private Date updateTime = new Date();

	public CampaignDetail() {

	}

	public CampaignDetail(Integer campaignId, String campaignName, Date campaignStartTime, String itemId) {
		this.campaignId = campaignId;
		this.campaignName = campaignName;
		this.campaignStartTime = campaignStartTime;
		this.itemId = itemId;
	}

	public Integer getCampaignId() {
		return campaignId;
	}

	public void setCampaignId(Integer campaignId) {
		this.campaignId = campaignId;
	}

	public String getCampaignName() {
		return campaignName;
	}

	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}

	public Date getCampaignStartTime() {
		return campaignStartTime;
	}

	public void setCampaignStartTime(Date campaignStartTime) {
		this.campaignStartTime = campaignStartTime;
	}

	public Date getCampaignEndTime() {
		return campaignEndTime;
	}

	public void setCampaignEndTime(Date campaignEndTime) {
		this.campaignEndTime = campaignEndTime;
	}

	public Integer getCampaignMemberNum() {
		return campaignMemberNum;
	}

	public void setCampaignMemberNum(Integer campaignMemberNum) {
		this.campaignMemberNum = campaignMemberNum;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public Integer getItemType() {
		return itemType;
	}

	public void setItemType(Integer itemType) {
		this.itemType = itemType;
	}

	public Integer getIsHaveSubTable() {
		return isHaveSubTable;
	}

	public void setIsHaveSubTable(Integer isHaveSubTable) {
		this.isHaveSubTable = isHaveSubTable;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}


	@Override
	public String toString() {
		return "CampaignDetail [id=" + id + ", campaignId=" + campaignId + ", campaignName=" + campaignName
				+ ", campaignStartTime=" + campaignStartTime + ", campaignEndTime=" + campaignEndTime + ", campaignMemberNum="
				+ campaignMemberNum + ", ItemId=" + itemId + ", ItemType=" + itemType + ", isHaveSubTable=" + isHaveSubTable
				+ ", updateTime=" + updateTime + "]";
	}

}
