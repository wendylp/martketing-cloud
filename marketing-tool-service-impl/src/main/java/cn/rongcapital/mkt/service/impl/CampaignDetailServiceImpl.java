package cn.rongcapital.mkt.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.dao.CampaignBodyDao;
import cn.rongcapital.mkt.dao.CampaignHeadDao;
import cn.rongcapital.mkt.dao.CampaignNodeItemDao;
import cn.rongcapital.mkt.dao.CampaignSwitchDao;
import cn.rongcapital.mkt.dao.DataPartyDao;
import cn.rongcapital.mkt.po.CampaignBody;
import cn.rongcapital.mkt.po.CampaignHead;
import cn.rongcapital.mkt.po.CampaignNodeItem;
import cn.rongcapital.mkt.po.CampaignSwitch;
import cn.rongcapital.mkt.po.DataParty;
import cn.rongcapital.mkt.po.mongodb.CampaignDetail;
import cn.rongcapital.mkt.po.mongodb.CampaignMember;
import cn.rongcapital.mkt.po.mongodb.NodeAudience;
import cn.rongcapital.mkt.service.CampaignDetailService;
import cn.rongcapital.mkt.vo.out.CampaignDetailOut;
import cn.rongcapital.mkt.vo.out.CampaignInfo;
import cn.rongcapital.mkt.vo.out.CampaignItemAudiencesInfo;
import cn.rongcapital.mkt.vo.out.CampaignItemInfo;

/**
 * 
 * @author LiuQ
 * @email Liuqi@rongcapital.cn
 */
@Service
public class CampaignDetailServiceImpl implements CampaignDetailService {

	@Autowired
	private DataPartyDao dataPartyDao;
	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private CampaignSwitchDao switchDao;
	@Autowired
	private CampaignHeadDao campaignHeadDao;
	@Autowired
	private CampaignBodyDao campaignBodyDao;
	@Autowired
	private CampaignNodeItemDao campaignNodeItemDao;

	public final static Byte TRIGGER_TYPE = 0; // 触发类型
	public final static Byte ACTION_TYPE = 3; // 行动类型
	public final static Byte WX_TYPE = 5; // 发送微信图文
	public final static Byte SMS_TYPE = 6; // 发送短信
	public final static int HAVE_SUB_DATA = 1; // 发送短信
	/**
	 * key: key:campaignId:itemId
	 */
	private Map<String, CampaignDetail> cache = new HashMap<String, CampaignDetail>();

	@Override
	public CampaignDetailOut campaignDetail(String name) {
		logger.info("检索活动名称：{}", name);
		List<CampaignInfo> infoList = new ArrayList<CampaignInfo>();
		List<CampaignHead> campaignHeadList = this.selectCampaignHead(name);
		if (campaignHeadList != null && !campaignHeadList.isEmpty()) {
			CampaignInfo campaignInfo = null;
			for (CampaignHead head : campaignHeadList) {
				campaignInfo = new CampaignInfo(head.getId(), head.getName(), head.getCreateTime(), head.getEndTime());
				campaignInfo.setTotalCount(this.selectCampaignAudiencesTotalCount(head.getId()));
				campaignInfo.setItems(this.selectCampaignItemInfoList(head.getId()));
				infoList.add(campaignInfo);
			}
		}
		logger.debug("活动统计数据：{}", infoList);
		return new CampaignDetailOut(infoList);
	}

	/**
	 * 查询参加营销活动的总人数
	 * 
	 * @param campaignHeadId
	 * @return
	 */
	public Integer selectCampaignAudiencesTotalCount(Integer campaignHeadId) {
		logger.debug("活动head_id={}", campaignHeadId);
		Integer defaultCount = 0;
		List<CampaignBody> nodeItemList = this.selectCampaignItemList(campaignHeadId, TRIGGER_TYPE);
		if (nodeItemList == null || nodeItemList.isEmpty()) {
			logger.error("无效的触发节点，活动head_id={}", campaignHeadId);
			return defaultCount;
		}
		CampaignBody firstNodeItem = nodeItemList.get(0);
		CampaignSwitch switch1 = this.selectCampaignSwitch(campaignHeadId, firstNodeItem.getItemId());
		if (switch1 == null) {
			logger.error("无效的活动连线，活动head_id={}，itemId={}", campaignHeadId, firstNodeItem.getItemId());
			return defaultCount;
		}
		Integer totalCount = this.selectPassItemNodTotalCount(campaignHeadId, switch1.getNextItemId());
		return totalCount;
	}

	/**
	 * 查询触达明细
	 * 
	 * @param campaignHeadId
	 * @return
	 */
	public List<CampaignItemInfo> selectCampaignItemInfoList(Integer campaignHeadId) {
		List<CampaignItemInfo> campaignItemInfos = new ArrayList<CampaignItemInfo>();
		List<CampaignBody> campaignBodyList = this.selectCampaignItemList(campaignHeadId, ACTION_TYPE);
		logger.debug("活动的行动节点：{}", campaignBodyList);
		if (campaignBodyList != null && !campaignBodyList.isEmpty()) {
			for (CampaignBody cur : campaignBodyList) {
				if (cur.getItemType() == WX_TYPE || cur.getItemType() == SMS_TYPE) {
					String audiencesTypeName = this.selectTriggerName(cur.getNodeType(), cur.getItemType()); // 活动触达方式
					CampaignItemInfo campaignItemInfo = new CampaignItemInfo(audiencesTypeName);
					campaignItemInfo.setAudiences(this.selectCampaignItemAudiencesInfoList(campaignHeadId, cur.getItemId()));
					campaignItemInfos.add(campaignItemInfo);
				}
			}
		}
		return campaignItemInfos;
	}

	/**
	 * 获取受众明细
	 * 
	 * @param campaignHeadId
	 * @param itemId
	 * @return
	 */
	public List<CampaignItemAudiencesInfo> selectCampaignItemAudiencesInfoList(Integer campaignHeadId, String itemId) {
		List<CampaignItemAudiencesInfo> campaignItemAudiencesInfoList = new ArrayList<CampaignItemAudiencesInfo>();
		List<Integer> midList = this.selectMidList(campaignHeadId, itemId);
		logger.debug("受众人群的主数据mid：{}", midList);
		if (midList == null || midList.isEmpty()) {
			logger.warn("空的受众人群，活动head_id={}，itemId={}", campaignHeadId, itemId);
			return campaignItemAudiencesInfoList;
		}
		List<DataParty> dataPartyList = this.selectDataParty(midList);
		logger.debug("受众人群的主数据列表：{}", dataPartyList);
		if (midList == null || midList.isEmpty()) {
			logger.warn("空的受众人群主数据列表，mid:{}", midList);
			return campaignItemAudiencesInfoList;
		}
		for (DataParty cur : dataPartyList) {
			CampaignItemAudiencesInfo audiencesInfo = new CampaignItemAudiencesInfo(cur.getId(), cur.getMobile(),
					cur.getWxCode(), cur.getWxmpId());
			campaignItemAudiencesInfoList.add(audiencesInfo);
		}
		logger.info("活动head_id={}，itemId={}， 受众人群：{}", campaignHeadId, itemId, campaignItemAudiencesInfoList);
		return campaignItemAudiencesInfoList;
	}

	/**
	 * 查询流过指定节点的mid
	 * 
	 * @DB MongoDB
	 * @table node_audience
	 * @param campaignHeadId
	 * @param itemId
	 * @return
	 */
	public List<Integer> selectMidList(Integer campaignHeadId, String itemId) {
		Criteria criteria = Criteria.where("campaignHeadId").is(campaignHeadId).and("itemId").is(itemId);
		List<NodeAudience> nodeAudienceList = mongoTemplate.find(new Query(criteria), NodeAudience.class);
		if (nodeAudienceList == null || nodeAudienceList.isEmpty()) {
			logger.warn("没有找到对应的受众节点数据，活动head_id={}，itemId={}", campaignHeadId, itemId);
			return null;
		}
		List<Integer> ids = new ArrayList<Integer>();
		for (NodeAudience cur : nodeAudienceList) {
			ids.add(cur.getDataId());
		}
		logger.info("主数据mid：{}", ids);
		return ids;
	}

	/**
	 * 查询活动连线
	 * 
	 * @DB MySQL
	 * @table campaign_switch
	 * @param campaignHeadId
	 * @param itemId
	 * @return
	 */
	public CampaignSwitch selectCampaignSwitch(Integer campaignHeadId, String itemId) {
		CampaignSwitch query = new CampaignSwitch();
		query.setCampaignHeadId(campaignHeadId);
		query.setItemId(itemId);
		List<CampaignSwitch> campaignSwitchs = this.switchDao.selectList(query);
		if (campaignSwitchs == null || campaignSwitchs.isEmpty()) {
			logger.debug("无效的活动head_id={}，itemId={}", campaignHeadId, itemId);
			return null;
		}
		CampaignSwitch campaignSwitch = campaignSwitchs.get(0);
		return campaignSwitch;
	}

	/**
	 * 查询活动列表
	 * 
	 * @DB MySQL
	 * @table campaign_head
	 * @param name
	 *            活动名称，支持模糊匹配
	 * @return
	 */
	public List<CampaignHead> selectCampaignHead(String name) {
		Map<String, Object> customMap = new HashMap<String, Object>();
		customMap.put("keyword", name);
		CampaignHead query = new CampaignHead();
		query.setCustomMap(customMap);
		List<CampaignHead> campaignHeads = campaignHeadDao.selectCampaignProgressStatusListByPublishStatus(query);
		if (campaignHeads == null || campaignHeads.isEmpty()) {
			logger.debug("没有找到包含“{}”的活动", name);
			return null;
		}
		return campaignHeads;
	}

	/**
	 * 查询活动
	 * 
	 * @DB MySQL
	 * @table campaign_head
	 * @param id
	 * @return
	 */
	public CampaignHead selectCampaignHead(Integer id) {
		if (id == null) {
			logger.error("无效的参数：id={}", id);
			return null;
		}

		CampaignHead query = new CampaignHead();
		query.setId(id);
		List<CampaignHead> campaignHeads = campaignHeadDao.selectList(query);
		if (campaignHeads == null || campaignHeads.isEmpty() || campaignHeads.size() > 1) {
			logger.debug("没有找到或者找到多个活动， id={}", id);
			return null;
		}
		return campaignHeads.get(0);
	}

	/**
	 * 查询流过指定节点的总受众数
	 * 
	 * @DB MongoDB
	 * @table node_audience
	 * @param campaignHeadId
	 * @param itemId
	 * @return
	 */
	public Integer selectPassItemNodTotalCount(Integer campaignHeadId, String itemId) {
		Criteria criteria = Criteria.where("campaignHeadId").is(campaignHeadId).and("itemId").is(itemId);
		Query query = new Query(criteria);
		Long totalCount = mongoTemplate.count(query, NodeAudience.class);
		if (totalCount == null) {
			logger.debug("没有查询到流过该节点的受众人群，活动head_id={}，itemId={}", campaignHeadId, itemId);
			return null;
		}
		return totalCount.intValue();
	}

	/**
	 * 查询触达方式名称
	 * 
	 * @DB MySQL
	 * @table campaign_node_item
	 * @param itemPType
	 * @param itemType
	 * @return
	 */
	public String selectTriggerName(Byte itemPType, Byte itemType) {
		CampaignNodeItem query = new CampaignNodeItem();
		query.setPtype(itemPType);
		query.setType(itemType);
		List<CampaignNodeItem> campaignNodeItems = campaignNodeItemDao.selectList(query);
		if (campaignNodeItems == null || campaignNodeItems.isEmpty()) {
			logger.debug("没有查询到父节点类型是{}，节点类型是{}的节点", itemPType, itemType);
			return null;
		}
		CampaignNodeItem campaignNodeItem = campaignNodeItems.get(0);
		String name = campaignNodeItem.getName();
		return name;
	}

	/**
	 * 查询指定类型的节点
	 * 
	 * @DB MySQL
	 * @table campaign_body
	 * @param campaignHeadId
	 * @param nodeType
	 *            0:触发,1:受众,2决策,3行动,4:分支线
	 * @return
	 */
	public List<CampaignBody> selectCampaignItemList(Integer campaignHeadId, Byte nodeType) {
		CampaignBody query = new CampaignBody();
		query.setHeadId(campaignHeadId);
		query.setNodeType(nodeType);
		List<CampaignBody> campaignBodies = this.campaignBodyDao.selectList(query);
		if (campaignBodies == null || campaignBodies.isEmpty()) {
			logger.debug("没有查询到活动head_id是{}， 节点类型是{}的节点", campaignHeadId, nodeType);
			return null;
		}
		return campaignBodies;
	}

	/**
	 * 查询受众详细信息
	 * 
	 * @DB MySQL
	 * @table data_party
	 * @param mid
	 * @return
	 */
	public List<DataParty> selectDataParty(List<Integer> mids) {
		List<DataParty> list = this.dataPartyDao.selectDataPartyList(mids);
		return list;
	}

	@Override
	public void saveCampaignDetail(Integer campaignId) {
		if (!isValidForInteger(campaignId)) {
			logger.error("无效的参数：campaignId={}", campaignId);
			return;
		}
		CampaignHead campaignHead = this.selectCampaignHead(campaignId);
		if (campaignHead == null) {
			logger.error("无效的活动：campaignId={}", campaignId);
			return;
		}
		logger.debug("活动详情:{}", campaignHead);
		List<CampaignBody> campaignBodiesType = this.selectCampaignItemList(campaignId, TRIGGER_TYPE);
		if (campaignBodiesType == null || campaignBodiesType.isEmpty()) {
			logger.error("无效的活动：campaignId={}", campaignId);
		}
		Byte campaignType = campaignBodiesType.get(0).getItemType(); // 活动类型
		List<CampaignBody> campaignBodies = this.selectCampaignItemList(campaignId, ACTION_TYPE);
		CampaignDetail detail = null;
		for (CampaignBody cur : campaignBodies) {
			detail = new CampaignDetail(campaignId, campaignHead.getName(), campaignHead.getStartTime(), cur.getItemId());
			detail.setCampaignMemberNum(campaignType.intValue() == 3 ? -1 : 0);
			detail.setItemType(cur.getItemType().intValue());
			String key = this.createKey(campaignId, cur.getItemId());
			if (cache.containsKey(key)) {
				cache.remove(key);
			}
			cache.put(key, detail); // 添加缓存
			mongoTemplate.insert(detail);
			logger.debug("detail={}", detail);
		}
	}

	@Override
	public void saveCampaignMember(Integer campaignId, String itemId, Integer mid) {
		if (!isValidForInteger(campaignId, mid) || !isValidForString(itemId)) {
			logger.error("无效的参数：campaignId={}, itemId={}, mid={}", campaignId, itemId, mid);
			return;
		}
		DataParty dataParty = new DataParty();
		dataParty.setId(mid);
		List<DataParty> dataParties = this.dataPartyDao.selectList(dataParty);
		if (dataParties == null || dataParties.isEmpty()) {
			logger.error("没有找到对应的主数据： mid={}", mid);
			return;
		}
		Criteria criteria = Criteria.where("mid").is(mid);
		Query query = new Query(criteria);
		cn.rongcapital.mkt.po.mongodb.DataParty dp2 = mongoTemplate.findOne(query, cn.rongcapital.mkt.po.mongodb.DataParty.class, "data_party");

		DataParty dp = dataParties.get(0);
		CampaignMember member = new CampaignMember(campaignId, itemId, dp.getId(), dp2 == null ? null : dp2.getMemberId());
		member.setPhone(dp.getMobile());
		member.setWxId(dp.getWxmpId());
		member.setOpenId(dp.getWxCode());
		mongoTemplate.save(member);

		String key = this.createKey(campaignId, itemId);
		CampaignDetail detail = cache.get(key);
		if (detail != null && detail.getIsHaveSubTable().intValue() != HAVE_SUB_DATA) {
			detail.setIsHaveSubTable(HAVE_SUB_DATA);
			this.updateCampaignDetailSubTableStatus(campaignId, itemId, HAVE_SUB_DATA);
		}
		logger.debug("campaignId={}, itemId={}, member={}", campaignId, itemId, member);

	}

	public void updateCampaignDetailSubTableStatus(Integer campaignId, String itemId, Integer isHaveSubTable) {
		if (!isValidForInteger(campaignId, isHaveSubTable) || !isValidForString(itemId)) {
			return;
		}

		Criteria criteria = Criteria.where("campaign_id").is(campaignId).and("item_id").is(itemId);
		Query query = new Query(criteria);

		Update update = new Update();
		update.set("is_have_sub_table", isHaveSubTable);
		mongoTemplate.updateFirst(query, update, CampaignDetail.class);
		logger.debug("campaignId={}, itemId={}, total={}, isHaveSubTable={}", campaignId, itemId, isHaveSubTable);
	}

	@Override
	public void updateCampaignDetailMemberTotal(Integer campaignId) {
		if (!isValidForInteger(campaignId)) {
			logger.error("无效的参数：campaignId={}", campaignId);
			return;
		}
		CampaignHead campaignHead = this.selectCampaignHead(campaignId);
		logger.debug("活动详情:{}", campaignHead);
		Integer total = this.selectCampaignAudiencesTotalCount(campaignId);

		Criteria criteria = Criteria.where("campaign_id").is(campaignId);
		Query query = new Query(criteria);

		Update update = new Update();
		update.set("campaign_member_num", total);
		update.set("campaign_end_time", campaignHead.getEndTime());
		mongoTemplate.updateFirst(query, update, CampaignDetail.class);
		this.removeCache(createKey(campaignId, "")); // 清楚缓存
		logger.debug("campaignId={}, total={}", campaignId, total);
	}

	@Override
	public CampaignDetail selectCampaignDetail(Integer campaignId, String itemId) {
		if (!isValidForInteger(campaignId) || !isValidForString(itemId)) {
			logger.error("无效的参数：campaignId={}, itemId={}", campaignId, itemId);
			return null;
		}

		String key = this.createKey(campaignId, itemId);
		if (cache.containsKey(key)) {
			return cache.get(key);
		}

		Criteria criteria = Criteria.where("campaign_id").is(campaignId).and("item_id").is(itemId);
		Query query = new Query(criteria);
		CampaignDetail detail = mongoTemplate.findOne(query, CampaignDetail.class);
		cache.put(key, detail);
		logger.debug("campaignId={}, itemId={}, CampaignDetail={}", campaignId, itemId, detail);
		return detail;
	}

	@Override
	public void updateCampaignMemberCouponId(Integer campaignId, String itemId, Integer mid, Integer isTouch, Integer couponId) {
		if (!isValidForInteger(campaignId, mid, isTouch, couponId) || !isValidForString(itemId)) {
			logger.error("无效的参数：campaignId={}, itemId={}, mid={}, isTouch={}, couponId={}", campaignId, itemId, mid, isTouch, couponId);
			return;
		}
		Criteria criteria = Criteria.where("campaign_id").is(campaignId).and("item_id").is(itemId).and("mid").is(mid);
		Query query = new Query(criteria);

		Update update = new Update();
		update.set("is_touch", isTouch); // 是否触达
		update.set("is_respond", isTouch); // 是否相应
		update.set("coupon_id", couponId); // 优惠券ID
		mongoTemplate.updateFirst(query, update, CampaignMember.class);
		logger.debug("campaignId={}, itemId={}, mid={}, isTouch={}, couponId={}", campaignId, itemId, mid, isTouch, couponId);
	}

	@Override
	public void updateCampaignMemberCouponStatus(Integer campaignId, String itemId, Integer mid, Integer isBuy) {
		if (!isValidForInteger(campaignId, mid, isBuy) || !isValidForString(itemId)) {
			logger.error("无效的参数：campaignId={}, itemId={}, mid={}, isBuy={}", campaignId, itemId, mid, isBuy);
			return;
		}
		Criteria criteria = Criteria.where("campaign_id").is(campaignId).and("item_id").is(itemId).and("mid").is(mid);
		Query query = new Query(criteria);

		Update update = new Update();
		update.set("is_buy", isBuy); // 是否核销
		mongoTemplate.updateFirst(query, update, CampaignMember.class);
		logger.debug("campaignId={}, itemId={}, mid={}, isBuy={}", campaignId, itemId, mid, isBuy);
	}

	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	/**
	 * 简单校验参数是否有效
	 * 
	 * @param args
	 * @return
	 */
	public boolean isValidForInteger(Integer... args) {
		for (Integer cur : args) {
			if (cur == null) {
				return false;
			}
		}
		return true;
	}

	public boolean isValidForString(String... args) {
		for (String cur : args) {
			if (StringUtils.isNotBlank(cur)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 支持模糊匹配
	 * 
	 * @param key
	 */
	public void removeCache(String key) {
		Set<String> keys = cache.keySet();
		for (String cur : keys) {
			if (cur.startsWith(key)) {
				cache.remove(cur);
			}
		}
	}

	public String createKey(Integer campaignId, String itemId) {
		if (campaignId == null || campaignId.intValue() == 0) {
			logger.error("无效的参数, campaignId={}", campaignId);
			return null;
		}
		if (StringUtils.isBlank(itemId)) {
			return "key:" + campaignId + ":";
		}
		return "key:" + campaignId + ":" + itemId;
	}
}
