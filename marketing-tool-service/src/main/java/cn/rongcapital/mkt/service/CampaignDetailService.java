package cn.rongcapital.mkt.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.rongcapital.mkt.po.mongodb.CampaignDetail;
import cn.rongcapital.mkt.po.mongodb.CampaignMember;
import cn.rongcapital.mkt.vo.out.CampaignDetailOut;

public interface CampaignDetailService {

	public Logger logger = LoggerFactory.getLogger(CampaignDetailService.class);

	/**
	 * 活动名称
	 * 
	 * @param name
	 * @return
	 */
	public CampaignDetailOut campaignDetail(String name);

	/**
	 * 保存活动详细
	 * 
	 * @table campaign_detail
	 * @param detail
	 */
	default public void saveCampaignDetail(CampaignDetail detail) {

	}

	/**
	 * 保存触达信息
	 * 
	 * @table campaign_detail
	 * @param campaignId
	 * @param itemId
	 * @param member
	 */
	default public void saveCampaignMember(Integer campaignId, String itemId, CampaignMember member) {
	}

	/**
	 * 更新子表状态
	 * 
	 * @table campaign_detail
	 * @param campaignId
	 * @param itemId
	 * @param isHaveSubTable
	 */
	default public void updateCampaignDetailSubTableStatus(Integer campaignId, String itemId, Integer isHaveSubTable) {
	}

	/**
	 * 更新活动预计活动人数
	 * 
	 * @table campaign_detail
	 * @param campaignId
	 * @param itemId
	 * @param total
	 */
	default public void updateCampaignDetailMemberTotal(Integer campaignId, Integer total) {
	}

	/**
	 * 查询活动详细
	 * 
	 * @table campaign_detail
	 * @param campaignId
	 * @param itemId
	 * @return
	 */
	default public CampaignDetail selectCampaignDetail(Integer campaignId, String itemId) {
		return null;
	}

	/**
	 * 更新触达状态、优惠券ID
	 * 
	 * @table campaign_detail
	 * @table campaign_detail
	 * @param campaignId
	 * @param itemId
	 * @param mid
	 * @param isTouch
	 * @param couponId
	 */
	default public void updateCampaignMemberCouponId(Integer campaignId, String itemId, Integer mid, Integer isTouch, Integer couponId) {
	}
}
