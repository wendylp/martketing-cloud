package cn.rongcapital.mkt.po.mongodb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
	private String ItemId;
	@Field(value = "item_type")
	private Integer ItemType;
	@Field(value = "is_have_sub_table")
	private Integer IsHaveSubTable;
	@Field(value = "update_time")
	private Date updateTime;
	@Field(value = "campaign_member_list")
	private List<CampaignMember> campaignMemberList = new ArrayList<CampaignMember>();

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
		return ItemId;
	}

	public void setItemId(String itemId) {
		ItemId = itemId;
	}

	public Integer getItemType() {
		return ItemType;
	}

	public void setItemType(Integer itemType) {
		ItemType = itemType;
	}

	public Integer getIsHaveSubTable() {
		return IsHaveSubTable;
	}

	public void setIsHaveSubTable(Integer isHaveSubTable) {
		IsHaveSubTable = isHaveSubTable;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public List<CampaignMember> getCampaignMemberList() {
		return campaignMemberList;
	}

	public void setCampaignMemberList(List<CampaignMember> campaignMemberList) {
		this.campaignMemberList = campaignMemberList;
	}

	@Override
	public String toString() {
		return "CampaignDetail [id=" + id + ", campaignId=" + campaignId + ", campaignName=" + campaignName
				+ ", campaignStartTime=" + campaignStartTime + ", campaignEndTime=" + campaignEndTime + ", campaignMemberNum="
				+ campaignMemberNum + ", ItemId=" + ItemId + ", ItemType=" + ItemType + ", IsHaveSubTable=" + IsHaveSubTable
				+ ", updateTime=" + updateTime + ", campaignMemberList=" + campaignMemberList + "]";
	}

}
