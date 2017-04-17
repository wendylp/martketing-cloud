package cn.rongcapital.mkt.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
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
	public final static Byte ITEM_PTYPE = 3; // 节点父类型 @see mysql.campaign_node_item.ptype

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
				String audiencesTypeName = this.selectTriggerName(ITEM_PTYPE, cur.getItemType()); // 活动触达方式
				CampaignItemInfo campaignItemInfo = new CampaignItemInfo(audiencesTypeName);
				campaignItemInfo.setAudiences(this.selectCampaignItemAudiencesInfoList(campaignHeadId, cur.getItemId()));
				campaignItemInfos.add(campaignItemInfo);
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
			CampaignItemAudiencesInfo audiencesInfo = new CampaignItemAudiencesInfo(cur.getId(), cur.getMobile(), cur.getWxCode());
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
}
