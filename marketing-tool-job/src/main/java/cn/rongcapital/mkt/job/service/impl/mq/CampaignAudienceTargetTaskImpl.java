package cn.rongcapital.mkt.job.service.impl.mq;

import java.util.List;

import javax.jms.Queue;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.dao.CampaignAudienceTargetDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.po.CampaignAudienceTarget;
import cn.rongcapital.mkt.po.CampaignSwitch;
import cn.rongcapital.mkt.po.TaskSchedule;
import cn.rongcapital.mkt.po.mongodb.Segment;

@Service
public class CampaignAudienceTargetTaskImpl extends BaseMQService implements TaskService {
	
	@Autowired  
    private MQUtil mqUtil;
	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private CampaignAudienceTargetDao campaignAudienceTargetDao;
	
	@Override
	public void task(TaskSchedule taskSchedule) {
		Integer campaignHeadId = taskSchedule.getCampaignHeadId();
		String itemId = taskSchedule.getCampaignItemId();
		Queue queue = getDynamicQueue(campaignHeadId+"-"+itemId);
		List<Segment> segmentList = getQueueData(queue);
		if(CollectionUtils.isNotEmpty(segmentList)) {
			mongoTemplate.insertAll(segmentList);//把接收到的人群保存到mongo
		}
		//查询mongo中该节点的人群
		CampaignAudienceTarget campaignAudienceTarget = new CampaignAudienceTarget();
		campaignAudienceTarget.setItemId(itemId);
		campaignAudienceTarget.setCampaignHeadId(campaignHeadId);
		campaignAudienceTarget.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		List<CampaignAudienceTarget> campaignAudienceTargetList = campaignAudienceTargetDao.selectList(campaignAudienceTarget);
		if(CollectionUtils.isNotEmpty(campaignAudienceTargetList)) {
			CampaignAudienceTarget cat = campaignAudienceTargetList.get(0);
			List<Segment> segmentListMongo = mongoTemplate.find(new Query(Criteria.where("segmentation_head_id").is(cat.getSegmentationId()+"")), Segment.class);
			List<CampaignSwitch> campaignEndsList = queryCampaignEndsList(campaignHeadId, itemId);
			//发送人群到后面的节点
			for(CampaignSwitch cs:campaignEndsList) {
				mqUtil.sendDynamicQueue(segmentListMongo, cs.getCampaignHeadId()+"-"+cs.getNextItemId());
			}
		}
	}

	@Override
	public void task(Integer taskId) {
		// TODO Auto-generated method stub
		
	}

}
