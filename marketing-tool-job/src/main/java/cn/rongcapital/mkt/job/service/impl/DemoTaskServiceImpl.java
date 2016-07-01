package cn.rongcapital.mkt.job.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.util.HttpClientUtil;
import cn.rongcapital.mkt.common.util.HttpUrl;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.mongodb.DataPartyRepository;

@Service
public class DemoTaskServiceImpl implements TaskService {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private DataPartyRepository dataPartyRepository;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	public static void main(String[] args) {
		System.out.println(Calendar.getInstance().getTime().toString());
	}
	
	@Override
	public void task(Integer taskId) {
		test();
//		List<DataParty> dpList = null;
////		dpList = dataPartyRepository.findAll();
////		for(DataParty dp:dpList) {
////			System.out.println(JSON.toJSONString(dp));
////		}
////		dpList = dataPartyRepository.findByMid("101");
////		for(DataParty dp:dpList) {
////			System.out.println(JSON.toJSONString(dp));
////		}
////		dpList = mongoTemplate.find(new Query(Criteria.where("mid").is("102")),DataParty.class);
//		dpList = mongoTemplate.find(new Query(Criteria.where("mid").is(102)),DataParty.class);
////		dpList = mongoTemplate.find(new Query(Criteria.where("tagList.tagName").is("男")),DataParty.class);
//		for(DataParty dp:dpList) {
//			System.out.println(JSON.toJSONString(dp));
//		}
	}
	
	private void test() {
			HttpUrl httpUrl = new HttpUrl();
			httpUrl.setHost("test.h5plus.net");
			httpUrl.setPath(ApiConstant.DL_PUB_SEND_API_PATH + "55cbf3a3986a9b483376f279");
			HashMap<Object , Object> params = new HashMap<Object , Object>();
			params.put("pub_id", "gh_e611846d32ee");
			params.put("message_type","news");
			params.put("material_id",1297);
			List<String> fansWeixinIds = new ArrayList<String>();
			fansWeixinIds.add("ozn8st4fvXQ3oGzB__j6gMt9Va7A");
			params.put("fans_weixin_ids",JSON.toJSON(fansWeixinIds));
			httpUrl.setRequetsBody(JSON.toJSONString(params));
			httpUrl.setContentType(ApiConstant.CONTENT_TYPE_JSON);
			try {
				PostMethod postResult = HttpClientUtil.getInstance().postExt(httpUrl);
				String postResStr = postResult.getResponseBodyAsString();
				System.out.println(postResStr);
				Integer taskId = JSON.parseObject(postResStr).getJSONObject("hfive_mkt_pub_send_response").getInteger("task_id");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
}
