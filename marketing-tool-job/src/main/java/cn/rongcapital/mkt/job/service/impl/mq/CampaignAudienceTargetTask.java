package cn.rongcapital.mkt.job.service.impl.mq;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.dao.CampaignAudienceTargetDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.po.CampaignAudienceTarget;
import cn.rongcapital.mkt.po.CampaignSwitch;
import cn.rongcapital.mkt.po.TaskSchedule;
import cn.rongcapital.mkt.po.mongodb.DataParty;
import cn.rongcapital.mkt.po.mongodb.Segment;

@Service
public class CampaignAudienceTargetTask extends BaseMQService implements TaskService {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private CampaignAudienceTargetDao campaignAudienceTargetDao;
	
	@Override
	public void task(TaskSchedule taskSchedule) {
		Integer campaignHeadId = taskSchedule.getCampaignHeadId();
		String itemId = taskSchedule.getCampaignItemId();
		String queueKey = campaignHeadId+"-"+itemId;
		CampaignAudienceTarget campaignAudienceTarget = new CampaignAudienceTarget();
		campaignAudienceTarget.setCampaignHeadId(campaignHeadId);
		campaignAudienceTarget.setItemId(itemId);
		campaignAudienceTarget.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		//查询mysql中该节点对应的segmentId
		List<CampaignAudienceTarget> campaignAudienceTargetList = campaignAudienceTargetDao.selectList(campaignAudienceTarget);
		if(CollectionUtils.isNotEmpty(campaignAudienceTargetList)) {
			CampaignAudienceTarget cat = campaignAudienceTargetList.get(0);
			//查询mongo中该segmentId对应的segment list
			List<Segment> segmentList = mongoTemplate.find(new Query(Criteria.where("segmentationHeadId").is(cat.getSegmentationId())), Segment.class);
			if(CollectionUtils.isNotEmpty(segmentList)) {
				List<Segment> segmentListUnique =  new ArrayList<Segment>();//去重后的segment list
				for(Segment segment:segmentList) {
					boolean audienceExist = checkNodeAudienceExist(campaignHeadId, itemId, segment.getDataId());
					if(!audienceExist) {//只存node_audience表中不存在的数据
						//把segment保存到mongo中的node_audience表
						insertNodeAudience(campaignHeadId, itemId, segment.getDataId(), segment.getName());
						DataParty dp = mongoTemplate.findOne(new Query(Criteria.where("mid").is(segment.getDataId())), DataParty.class);
						if(null != dp && dp.getMdType() != null && dp.getMdType() == ApiConstant.DATA_PARTY_MD_TYPE_WECHAT) {
							segment.setFansFriendsOpenId(dp.getFansOpenId());//设置微信粉丝/好友的openid
						}
						segmentListUnique.add(segment);
					}
				}
				//查询节点后面的分支
				List<CampaignSwitch> campaignEndsList = queryCampaignEndsList(campaignHeadId, itemId);
				for(CampaignSwitch cs:campaignEndsList) {
					//发送segment数据到后面的节点
					sendDynamicQueue(segmentListUnique, cs.getCampaignHeadId()+"-"+cs.getNextItemId());
					//逻辑删除传递走的数据
					logicDeleteNodeAudience(campaignHeadId,itemId,segmentListUnique);
					logger.info(queueKey+"-out:"+JSON.toJSONString(segmentListUnique));
				}
			}
			
		}
	}

	@Override
	public void task(Integer taskId) {
		// TODO Auto-generated method stub
		
	}

}
