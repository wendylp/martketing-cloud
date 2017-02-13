package cn.rongcapital.mkt.tag.service.impl;

import org.apache.commons.httpclient.methods.PostMethod;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.util.HttpClientUtil;
import cn.rongcapital.mkt.common.util.HttpUrl;
import cn.rongcapital.mkt.tag.service.TagSyncEventObjectService;
import cn.rongcapital.mkt.tag.vo.in.TagSyncEventObjectIn;
import cn.rongcapital.mkt.vo.BaseOutput;

/*************************************************
 * @功能及特点的描述简述: 通过事件客体打标签
 *
 * @see （与该类关联的类): CustomtagCreateServiceImpl
 * @对应项目名称: MC系统
 * @author: 丛树林
 * @version: v1.7 @date(创建、开发日期): 2017-1-20 最后修改日期: 2017-1-20
 * @复审人: 丛树林
 *************************************************/
@Service
@PropertySource("classpath:${conf.dir}/application-api.properties")
public class TagSyncEventObjectServiceImpl implements TagSyncEventObjectService {
	@Autowired
	Environment env;

	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 功能描述：根据客体标示获得事件客体对象
	 * 
	 * @param TagSyncEventObjectIn
	 *            事件客体标示
	 * 
	 * @return BaseOutput
	 */
	public BaseOutput getEventBehavierListGet(TagSyncEventObjectIn tagSyncEventObjectIn) {
		String eventReceiveUrl = env.getProperty("mkt.event.behavior.get");
		String hostHeaderAddr = env.getProperty("host.header.addr");

		ObjectMapper mapper = new ObjectMapper();

		String json = null;
		try {
			json = mapper.writeValueAsString(tagSyncEventObjectIn);
			logger.info("object convert json：{}", json);
		} catch (Exception e) {
			logger.error("object convert json Exception:", e);
		}

		HttpUrl httpUrl = new HttpUrl();
		httpUrl.setHost(hostHeaderAddr);
		httpUrl.setPath(eventReceiveUrl);
		httpUrl.setRequetsBody(json);
		httpUrl.setContentType(ApiConstant.CONTENT_TYPE_JSON);
		HttpClientUtil httpClientUtil;
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);
		try {
			httpClientUtil = HttpClientUtil.getInstance();
			PostMethod postResult = httpClientUtil.postExt(httpUrl);
			String postResStr = postResult.getResponseBodyAsString();
			logger.info("EventCenter response:{}", postResStr);
			result = JSON.parseObject(postResStr, BaseOutput.class);
			String msg = result.getMsg();
			if (!msg.equals("success")) {
				logger.info("EventObject sync tag to EventCenter fail");
			}
		} catch (Exception e) {
			logger.error("EventObject sync tag Exception:", e);
		}
		return result;
	}
}
