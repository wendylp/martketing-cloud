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
import cn.rongcapital.mkt.dao.CampaignActionSendPubDao;
import cn.rongcapital.mkt.dao.ImgTextAssetDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.po.CampaignActionSendPub;
import cn.rongcapital.mkt.po.CampaignSwitch;
import cn.rongcapital.mkt.po.ImgTextAsset;
import cn.rongcapital.mkt.po.TaskSchedule;
import cn.rongcapital.mkt.po.mongodb.DataParty;
import cn.rongcapital.mkt.po.mongodb.NodeAudience;
import cn.rongcapital.mkt.po.mongodb.Segment;

@Service
public class CampaignActionPubWechatSendH5Task extends BaseMQService implements TaskService {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private CampaignActionSendPubDao campaignActionSendPubDao;
	@Autowired
	private ImgTextAssetDao imgTextAssetDao;
	
	@Value("${runxue.h5.api.base.url}")
	private String h5BaseUrl;
	
	public void task(TaskSchedule taskSchedule) {
		Integer campaignHeadId = taskSchedule.getCampaignHeadId();
		String itemId = taskSchedule.getCampaignItemId();
		
		List<CampaignSwitch> campaignEndsList = queryCampaignEndsList(campaignHeadId, itemId);
		
		CampaignActionSendPub campaignActionSendPubT = new CampaignActionSendPub();
		campaignActionSendPubT.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		campaignActionSendPubT.setCampaignHeadId(campaignHeadId);
		campaignActionSendPubT.setItemId(itemId);
		List<CampaignActionSendPub> campaignActionSendPubList = campaignActionSendPubDao.selectList(campaignActionSendPubT);
		if(CollectionUtils.isEmpty(campaignActionSendPubList) ||
		   StringUtils.isBlank(campaignActionSendPubList.get(0).getPubId()) || 
		   null == campaignActionSendPubList.get(0).getMaterialId()) {
			logger.error("没有配置公众号和图文属性,return,campaignHeadId:"+campaignHeadId+",itemId:"+itemId);
			return;
		}
		CampaignActionSendPub campaignActionSendPub = campaignActionSendPubList.get(0);
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
											 itemId,campaignEndsList,campaignActionSendPub);
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
								  CampaignActionSendPub campaignActionSendPub) {
		List<Segment> segmentListToNext = new ArrayList<Segment>();//要传递给下面节点的数据(执行了发送微信操作的数据)
		String queueKey = campaignHeadId+"-"+itemId;
		for(Segment segment:segmentList) {
			if(!checkNodeAudienceExist(campaignHeadId, itemId, segment.getDataId())) {
				NodeAudience nodeAudience = new NodeAudience();
				nodeAudience.setCampaignHeadId(campaignHeadId);
				nodeAudience.setItemId(itemId);
				nodeAudience.setDataId(segment.getDataId());
				nodeAudience.setName(segment.getName());
				nodeAudience.setStatus(0);
				mongoTemplate.insert(nodeAudience);//插入mongo的node_audience表
				Integer dataId = segment.getDataId();
				//从mongo的主数据表中查询该条id对应的主数据详细信息
				DataParty dp = mongoTemplate.findOne(new Query(Criteria.where("mid").is(dataId)), DataParty.class);
				if(null!=dp && null !=dp.getMdType() &&
						StringUtils.isNotBlank(dp.getMappingKeyid()) &&
						dp.getMdType() == ApiConstant.DATA_PARTY_MD_TYPE_WECHAT) {
					//调用微信公众号发送图文接口
					boolean isSent = sendWechatByH5Interface(campaignActionSendPub,dp.getMappingKeyid());
					if(isSent) {
						String h5MobileUrl = getH5MobileUrl(campaignActionSendPub.getImgTextAssetId());
						segment.setPubId(campaignActionSendPub.getPubId());
						segment.setH5MobileUrl(h5MobileUrl);
						segment.setMaterialId(campaignActionSendPub.getMaterialId());
						segmentListToNext.add(segment);//数据放入向后面节点传递的list里
					}
				}
			}
		}
		if(CollectionUtils.isNotEmpty(campaignEndsList)) {
			for(CampaignSwitch cs:campaignEndsList) {
				//发送segment数据到后面的节点
				sendDynamicQueue(segmentListToNext, cs.getCampaignHeadId()+"-"+cs.getNextItemId());
				logicDeleteNodeAudience(campaignHeadId,itemId,segmentListToNext);
				logger.info(queueKey+"-out:"+JSON.toJSONString(segmentListToNext));
			}
		}
	}
	
	private String getH5MobileUrl(Integer imgTextAssetId){
		String h5Url = null;
		ImgTextAsset imgTextAssetT = new ImgTextAsset();
		imgTextAssetT.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		imgTextAssetT.setId(imgTextAssetId);
		List<ImgTextAsset> imgTextAssetList = imgTextAssetDao.selectList(imgTextAssetT);
		if(CollectionUtils.isNotEmpty(imgTextAssetList)) {
			h5Url = imgTextAssetList.get(0).getMobilePreviewUrl();
		}
		return h5Url;
		
	}
	
	/**
	 * 
	 * @param campaignActionSendH5
	 * @param fansWeixinId
	 * @return 任务id
	 */
	private boolean sendWechatByH5Interface(CampaignActionSendPub campaignActionSendPub,String fansWeixinId) {
		boolean isSent = false;
		HttpUrl httpUrl = new HttpUrl();
		httpUrl.setHost(h5BaseUrl);
		httpUrl.setPath(ApiConstant.DL_PUB_SEND_API_PATH+getPid());
		HashMap<Object , Object> params = new HashMap<Object , Object>();
		params.put("pub_id", campaignActionSendPub.getPubId());
		List<String> fansWeixinIds = new ArrayList<String>();
		fansWeixinIds.add(fansWeixinId);
		params.put("fans_weixin_ids",fansWeixinIds);
		params.put("message_type","news");
		params.put("material_id",campaignActionSendPub.getMaterialId());
		httpUrl.setRequetsBody(JSON.toJSONString(params));
		httpUrl.setContentType(ApiConstant.CONTENT_TYPE_JSON);
		try {
			PostMethod postResult = HttpClientUtil.getInstance().postExt(httpUrl);
			String postResStr = postResult.getResponseBodyAsString();
			String status = JSON.parseObject(postResStr).getJSONObject("hfive_mkt_pub_send_response").getString("status");
			if(StringUtils.isNotBlank(status) && status.equalsIgnoreCase("true")) {
				isSent = true;
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			isSent = false;
		}
		return isSent;
	}
	

//	public static void main(String[] args) {
//		HttpUrl httpUrl = new HttpUrl();
//		httpUrl.setHost("test.h5plus.net");
//		httpUrl.setPath(ApiConstant.DL_PUB_SEND_API_PATH + "55cbf3a3986a9b483376f279");
//		HashMap<Object , Object> params = new HashMap<Object , Object>();
//		params.put("pub_id", "gh_e611846d32ee");
//		params.put("message_type","news");
//		params.put("material_id",1297);
//		List<String> fansWeixinIds = new ArrayList<String>();
//		fansWeixinIds.add("ozn8st4fvXQ3oGzB__j6gMt9Va7A");
//		params.put("fans_weixin_ids",JSON.toJSON(fansWeixinIds));
//		httpUrl.setRequetsBody(JSON.toJSONString(params));
//		httpUrl.setContentType(ApiConstant.CONTENT_TYPE_JSON);
//		try {
//			PostMethod postResult = HttpClientUtil.getInstance().postExt(httpUrl);
//			String postResStr = postResult.getResponseBodyAsString();
//			System.out.println(postResStr);
//			Integer taskId = JSON.parseObject(postResStr).getJSONObject("hfive_mkt_pub_send_response").getInteger("task_id");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
	public void cancelInnerTask(TaskSchedule taskSchedule) {
		super.cancelCampaignInnerTask(taskSchedule);
	}
	
	@Override
	public void task(Integer taskId) {

	}

}
