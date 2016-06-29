package cn.rongcapital.mkt.job.service.impl.mq;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Queue;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.util.HttpClientUtil;
import cn.rongcapital.mkt.common.util.HttpUrl;
import cn.rongcapital.mkt.dao.CampaignActionSendPrivtDao;
import cn.rongcapital.mkt.dao.WechatPersonalUuidDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.po.CampaignActionSendPrivt;
import cn.rongcapital.mkt.po.CampaignSwitch;
import cn.rongcapital.mkt.po.TaskSchedule;
import cn.rongcapital.mkt.po.WechatPersonalUuid;
import cn.rongcapital.mkt.po.mongodb.DataParty;
import cn.rongcapital.mkt.po.mongodb.NodeAudience;
import cn.rongcapital.mkt.po.mongodb.Segment;

@Service
public class CampaignActionPrvWechatSendInfoTask extends BaseMQService implements TaskService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private CampaignActionSendPrivtDao campaignActionSendPrivtDao;
	@Autowired
	private WechatPersonalUuidDao wechatPersonalUuidDao;
	
	@Value("${runxue.h5.api.base.url}")
	private String h5BaseUrl;
	
	public void task (TaskSchedule taskSchedule) {
		Integer campaignHeadId = taskSchedule.getCampaignHeadId();
		String itemId = taskSchedule.getCampaignItemId();
		List<CampaignSwitch> campaignEndsList = queryCampaignEndsList(campaignHeadId, itemId);
		CampaignActionSendPrivt campaignActionSendPrivtT = new CampaignActionSendPrivt();
		campaignActionSendPrivtT.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		campaignActionSendPrivtT.setCampaignHeadId(campaignHeadId);
		campaignActionSendPrivtT.setItemId(itemId);
		List<CampaignActionSendPrivt> campaignActionSendPrivtList = campaignActionSendPrivtDao.selectList(campaignActionSendPrivtT);
		if(CollectionUtils.isEmpty(campaignActionSendPrivtList) ||
		   StringUtils.isBlank(campaignActionSendPrivtList.get(0).getUin())) {
			logger.error("没有配置个人号属性,return,campaignHeadId:"+campaignHeadId+",itemId:"+itemId);
			return;
		}
		CampaignActionSendPrivt campaignActionSendPrivt = campaignActionSendPrivtList.get(0);
		Queue queue = getDynamicQueue(campaignHeadId+"-"+itemId);//获取MQ中的当前节点对应的queue
		MessageConsumer consumer = getQueueConsumer(queue);//获取queue的消费者对象
		//监听MQ的listener
		MessageListener listener = new MessageListener() {
			@SuppressWarnings("unchecked")
			@Override
			public void onMessage(Message message) {
				if(message!=null) {
					try {
						//获取segment list数据对象
						List<Segment> segmentList = (List<Segment>)((ObjectMessage)message).getObject();
						if(CollectionUtils.isNotEmpty(segmentList)) {
							processMqMessage(segmentList,campaignHeadId,
											 itemId,campaignEndsList,campaignActionSendPrivt);
						}
					} catch (Exception e) {
						logger.error(e.getMessage(),e);
					}
				}
			}
		};
		if(null != consumer){
			try {
				//设置监听器
				consumer.setMessageListener(listener);
				consumerMap.put(campaignHeadId+"-"+itemId, consumer);
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
			}     
		}
		
	}

	private void processMqMessage(List<Segment> segmentList,
			  Integer campaignHeadId,String itemId,
			  List<CampaignSwitch> campaignEndsList,
			  CampaignActionSendPrivt campaignActionSendPrivt) {
		List<Segment> segmentListToNext = new ArrayList<Segment>();//要传递给下面节点的数据(执行了发送微信操作的数据)
		String queueKey = campaignHeadId+"-"+itemId;
		for(Segment segment:segmentList) {
			NodeAudience nodeAudience = new NodeAudience();
			nodeAudience.setCampaignHeadId(campaignHeadId);
			nodeAudience.setItemId(itemId);
			nodeAudience.setDataId(segment.getDataId());
			nodeAudience.setName(segment.getName());
			if(!checkNodeAudienceExist(campaignHeadId, itemId, segment.getDataId())) {
				mongoTemplate.insert(nodeAudience);//插入mongo的node_audience表
			}
			Integer dataId = segment.getDataId();
		    //从mongo的主数据表中查询该条id对应的主数据详细信息
			DataParty dp = mongoTemplate.findOne(new Query(Criteria.where("mid").is(dataId)), DataParty.class);
			if(null!=dp && null !=dp.getMdType() &&
			   StringUtils.isNotBlank(dp.getMappingKeyid()) &&
			   dp.getMdType() == ApiConstant.DATA_PARTY_MD_TYPE_WECHAT) {
			  //调用微信个人号发送消息接口
			  boolean isSent = sendWechatByH5Interface(campaignActionSendPrivt,dp.getMappingKeyid());
			  if(isSent) {
				  		segmentListToNext.add(segment);//数据放入向后面节点传递的list里
				   }
			  }
		}
		if(CollectionUtils.isNotEmpty(campaignEndsList)) {
			for(CampaignSwitch cs:campaignEndsList) {
				//发送segment数据到后面的节点
				sendDynamicQueue(segmentListToNext, cs.getCampaignHeadId()+"-"+cs.getNextItemId());
				deleteNodeAudience(campaignHeadId,itemId,segmentListToNext);
				logger.info(queueKey+"-out:"+JSON.toJSONString(segmentListToNext));
			}
		}
	}
	
	private boolean sendWechatByH5Interface(CampaignActionSendPrivt campaignActionSendPrivt,String ucode) {
		boolean isSent = false;
		HashMap<Object , Object> params = new HashMap<Object , Object>();
		WechatPersonalUuid wechatPersonalUuidT = new WechatPersonalUuid();
		wechatPersonalUuidT.setStatus((int)ApiConstant.TABLE_DATA_STATUS_VALID);
		wechatPersonalUuidT.setUin(campaignActionSendPrivt.getUin());
		List<WechatPersonalUuid> wechatPersonalUuidList = wechatPersonalUuidDao.selectList(wechatPersonalUuidT);
		if(CollectionUtils.isNotEmpty(wechatPersonalUuidList)) {
			WechatPersonalUuid wechatPersonalUuid = new WechatPersonalUuid();
			String uuid = wechatPersonalUuid.getUuid();
			params.put("uuid", uuid);
		} else {
			return isSent;//没有有效的uuid,无法发送
		}
		HttpUrl httpUrl = new HttpUrl();
		httpUrl.setHost(h5BaseUrl);
		httpUrl.setPath(ApiConstant.DL_PRV_SEND_API_PATH+getPid());
		params.put("ucode", ucode);
		params.put("message",campaignActionSendPrivt.getTextInfo());
		httpUrl.setRequetsBody(JSON.toJSONString(params));
		httpUrl.setContentType(ApiConstant.CONTENT_TYPE_JSON);
		try {
			PostMethod postResult = HttpClientUtil.getInstance().postExt(httpUrl);
			String postResStr = postResult.getResponseBodyAsString();
			String status = JSON.parseObject(postResStr).getJSONObject("hfive_mkt_personal_send_response").getString("status");
			if(StringUtils.isNotBlank(status) && status.equalsIgnoreCase("true")) {
				isSent = true;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			isSent = false;
		}
		return isSent;
	}
	
	public void cancelInnerTask(TaskSchedule taskSchedule) {
		super.cancelCampaignInnerTask(taskSchedule);
	}
	
	@Override
	public void task(Integer taskId) {
		// TODO Auto-generated method stub

	}

}
