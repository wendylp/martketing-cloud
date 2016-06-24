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
import cn.rongcapital.mkt.dao.CampaignActionSendH5Dao;
import cn.rongcapital.mkt.dao.ImgTextAssetDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.po.CampaignActionSendH5;
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
    private MQUtil mqUtil;
	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	private CampaignActionSendH5Dao campaignActionSendH5Dao;
	@Autowired
	private ImgTextAssetDao imgTextAssetDao;
	
	private MessageConsumer consumer = null;
	@Value("${runxue.h5.api.base.url}")
	private String h5BaseUrl;
	
	public void task(TaskSchedule taskSchedule) {
		Integer campaignHeadId = taskSchedule.getCampaignHeadId();
		String itemId = taskSchedule.getCampaignItemId();
		
		List<CampaignSwitch> campaignEndsList = queryCampaignEndsList(campaignHeadId, itemId);
		
		CampaignActionSendH5 campaignActionSendH5T = new CampaignActionSendH5();
		campaignActionSendH5T.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		campaignActionSendH5T.setCampaignHeadId(campaignHeadId);
		campaignActionSendH5T.setItemId(itemId);
		List<CampaignActionSendH5> campaignActionSendH5List = campaignActionSendH5Dao.selectList(campaignActionSendH5T);
		if(CollectionUtils.isEmpty(campaignActionSendH5List) ||
		   StringUtils.isBlank(campaignActionSendH5List.get(0).getPubId()) || 
		   null == campaignActionSendH5List.get(0).getWechatH5Id()) {
			logger.error("没有配置公众号和图文属性");
			return;
		}
		CampaignActionSendH5 campaignActionSendH5 = campaignActionSendH5List.get(0);
		Queue queue = getDynamicQueue(campaignHeadId+"-"+itemId);//获取MQ中的当前节点对应的queue
		consumer = getQueueConsumer(queue);//获取queue的消费者对象
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
							processMqMessage(message,segmentList,campaignHeadId,
											 itemId,campaignEndsList,campaignActionSendH5);
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
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
			}     
		}
		
	}
	
	private void processMqMessage(Message message,List<Segment> segmentList,
								  Integer campaignHeadId,String itemId,
								  List<CampaignSwitch> campaignEndsList,
								  CampaignActionSendH5 campaignActionSendH5) {
		List<Segment> segmentListToNext = new ArrayList<Segment>();//要传递给下面节点的数据(执行了发送微信操作的数据)
		for(Segment segment:segmentList) {
			NodeAudience nodeAudience = new NodeAudience();
			nodeAudience.setCampaignHeadId(campaignHeadId+"");
			nodeAudience.setItemId(itemId);
			nodeAudience.setDataId(segment.getDataId());
			String dataIdStr = segment.getDataId();
		    //从mongo的主数据表中查询该条id对应的主数据详细信息
			DataParty dp = mongoTemplate.findOne(new Query(Criteria.where("mid").is(dataIdStr)), DataParty.class);
			if(null!=dp && StringUtils.isNotBlank(dp.getMdType()) &&
			   StringUtils.isNotBlank(dp.getMappingKeyid()) &&
			   dp.getMdType().equals(ApiConstant.DATA_PARTY_MD_TYPE_WECHAT+"")) {
			   //调用微信公众号发送图文接口
			   Integer taskId = sendWechatByH5Interface(campaignActionSendH5,dp.getMappingKeyid());
			   if(null != taskId){
				   String h5Url = getH5Url(campaignActionSendH5.getWechatH5Id());
				   segment.setTaskId(taskId);
				   segment.setPubId(campaignActionSendH5.getPubId());
				   segment.setH5Url(h5Url);
				   nodeAudience.setTaskId(taskId);
				   segmentListToNext.add(segment);//大连返回taskId,该数据放入向后面节点传递的list里
			   }
			}
			mongoTemplate.insert(nodeAudience);//插入mongo的node_audience表
		}
		if(CollectionUtils.isNotEmpty(campaignEndsList)) {
			for(CampaignSwitch cs:campaignEndsList) {
				//发送segment数据到后面的节点
				mqUtil.sendDynamicQueue(segmentListToNext, cs.getCampaignHeadId()+"-"+cs.getNextItemId());
			}
		}
	}
	
	private String getH5Url(Integer wechatH5Id){
		String h5Url = null;
		ImgTextAsset imgTextAssetT = new ImgTextAsset();
		imgTextAssetT.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		imgTextAssetT.setId(wechatH5Id);
		List<ImgTextAsset> imgTextAssetList = imgTextAssetDao.selectList(imgTextAssetT);
		if(CollectionUtils.isNotEmpty(imgTextAssetList)) {
			h5Url = imgTextAssetList.get(0).getPcPreviewUrl();
		}
		return h5Url;
		
	}
	
	/**
	 * 
	 * @param campaignActionSendH5
	 * @param fansWeixinId
	 * @return 任务id
	 */
	private Integer sendWechatByH5Interface(CampaignActionSendH5 campaignActionSendH5,String fansWeixinId) {
		Integer taskId = null;
		HttpUrl httpUrl = new HttpUrl();
		httpUrl.setHost(h5BaseUrl);
		httpUrl.setPath(ApiConstant.DL_PUB_SEND_API_PATH);
		HashMap<Object , Object> params = new HashMap<Object , Object>();
		params.put("pid",getPid());
		params.put("pub_id", campaignActionSendH5.getPubId());
		List<String> fansWeixinIds = new ArrayList<String>();
		fansWeixinIds.add(fansWeixinId);
		params.put("fans_weixin_ids",fansWeixinIds);
		params.put("message_type","news");
		params.put("material_id",campaignActionSendH5.getWechatH5Id());
		httpUrl.setParams(params);
		try {
			PostMethod postResult = HttpClientUtil.getInstance().post(httpUrl);
			String postResStr = postResult.getResponseBodyAsString();
			taskId = JSON.parseObject(postResStr).getJSONObject("hfive_mkt_pub_send_response").getInteger("task_id");
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return taskId;
	}
	

//	public static void main(String[] args) {
//		HttpUrl httpUrl = new HttpUrl();
//		httpUrl.setHost("test.h5plus.net");
//		httpUrl.setPath(ApiConstant.DL_PUB_SEND_API_PATH);
//		HashMap<Object , Object> params = new HashMap<Object , Object>();
//		params.put("pid","55cbf3a3986a9b483376f279");
//		params.put("pub_id", "gh_e611846d32ed");
//		List<String> fansWeixinIds = new ArrayList<String>();
//		fansWeixinIds.add("ozn8st73vomJyqtvFS0ZEDI_jMcw");
//		params.put("fans_weixin_ids",fansWeixinIds);
//		params.put("message_type","news");
//		params.put("material_id",1226);
//		httpUrl.setParams(params);
//		try {
//			PostMethod postResult = HttpClientUtil.getInstance().post(httpUrl);
//			String postResStr = postResult.getResponseBodyAsString();
//			Integer taskId = JSON.parseObject(postResStr).getJSONObject("hfive_mkt_pub_send_response").getInteger("task_id");
//			System.out.println(taskId);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
	public void cancelInnerTask() {
		if(null != consumer) {
			try {
				consumer.close();
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
			}
		}
	}
	
	@Override
	public void task(Integer taskId) {

	}

}
