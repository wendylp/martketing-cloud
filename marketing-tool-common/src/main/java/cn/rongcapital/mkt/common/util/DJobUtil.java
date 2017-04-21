package cn.rongcapital.mkt.common.util;

import org.slf4j.Logger;

import com.alibaba.fastjson.JSONObject;

import cn.rongcapital.mkt.common.constant.ApiConstant;

public class DJobUtil {

    private static Logger logger = org.slf4j.LoggerFactory.getLogger(DJobUtil.class);
    
    public static boolean registerSchedule(String host,int port,String name,String group,String executor,String cron){
		JSONObject jso = new JSONObject();
		JSONObject keyJso = new JSONObject();
		keyJso.put("name", name);
		keyJso.put("group", group);
		jso.put("key", keyJso);
		jso.put("executor", executor);
		//e.g. "0 0 10 ? * *"
		jso.put("cron", cron);
		
		HttpUrl httpUrl = new HttpUrl();
		httpUrl.setHost(host);
		httpUrl.setPort(port);
		httpUrl.setPath("/trigger");
		httpUrl.setRequetsBody(jso.toString());
		httpUrl.setContentType(ApiConstant.CONTENT_TYPE_JSON);
		HttpClientUtil httpClientUtil;
		try{
			httpClientUtil = HttpClientUtil.getInstance();
			httpClientUtil.postExt(httpUrl);
		}catch(Exception e){
			logger.error("registerSchedule Exception:",e);
			return false;
		}
		return true;
    }
}
