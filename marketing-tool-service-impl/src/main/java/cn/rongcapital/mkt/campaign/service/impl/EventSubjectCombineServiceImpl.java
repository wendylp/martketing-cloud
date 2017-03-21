/*************************************************
 * @功能简述: 根据事件编码判断是否进行主数据合并
 * @项目名称: marketing cloud
 * @see:
 * @author: zhuxuelong
 * @version: 0.0.1
 * @date: 2017/3/1
 * @复审人:
 *************************************************/
package cn.rongcapital.mkt.campaign.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.campaign.service.EventSubjectCombineService;
import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.util.HttpClientUtil;
import cn.rongcapital.mkt.common.util.HttpUrl;
import cn.rongcapital.mkt.po.mongodb.Segment;
import cn.rongcapital.mkt.po.mongodb.event.EventBehavior;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

@Service
public class EventSubjectCombineServiceImpl implements EventSubjectCombineService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${host.header.addr}")
    private String host;

    @Value("${mkt.event.idmapping.get}")
    private String mappingUri;

    @Override
    public boolean needCombine(String eventCode) {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public Segment combineStreamData(EventBehavior eventbehavior) {
        Segment segment = new Segment();
        segment.setDataId(RandomUtils.nextInt(1000000));
        segment.setName(UUID.randomUUID().toString());
        return segment;
    }

//    @Override
//    public Segment combineStreamData(EventBehavior eventbehavior) {
//        String eventCode = eventbehavior.getEvent().get("code").toString();
//        Map<String, Object> subject = eventbehavior.getSubject();
//        String mobile = null;
//        String email = null;
//        String qq = null;
//        String name = null;
//        String source = null;
//        String strategy = null;
//        Segment segment = null;
//        switch (eventCode) {
//        // STREAM申请试用表单 MC申请试用表单
//            case "apply_submit_ruixuesoft":
//                mobile = (String) subject.get("o_mc_contact_soc_mobile_phone");
//                email = (String) subject.get("o_mc_contact_soc_mail");
//                qq = (String) subject.get("o_mc_contact_soc_qq");
//                name = (String) subject.get("o_mc_contact_attr_name");
//                source = (String) subject.get("o_mc_contact_sys_source");
//                strategy = "EventStrategy";
//                break;
//            default:
//                logger.error("combine stream master data [%s] meet illegal event code.", eventCode);
//                return segment;
//        }
//        try {
//            HttpClientUtil http = HttpClientUtil.getInstance();
//            HttpUrl httpUrl = new HttpUrl();
//            httpUrl.setHost(host);
//            httpUrl.setPath(mappingUri);
//            httpUrl.setContentType(ApiConstant.CONTENT_TYPE_JSON);
//            HashMap<Object, Object> params = new HashMap<Object, Object>();
//            if(StringUtils.isNotBlank(mobile)){
//                params.put("mobile", mobile);
//            }
//            if(StringUtils.isNotBlank(email)){
//                params.put("email", email);
//            }
//            if(StringUtils.isNotBlank(qq)){
//                params.put("qq", qq);
//            }
//            if(StringUtils.isNotBlank(name)){
//                params.put("name", name);
//            }
//            if(StringUtils.isNotBlank(source)){
//                params.put("source", source);
//            }
//            params.put("strategy", strategy);
//            httpUrl.setRequetsBody(JSON.toJSONString(params));
//            PostMethod postResult = http.postExt(httpUrl);
//            String response = postResult.getResponseBodyAsString();
//            if (StringUtils.isNotBlank(response)) {
//                JSONObject jsonObj = JSON.parseObject(response);
//                if (jsonObj != null && jsonObj.containsKey("mid")) {
//                    segment = new Segment();
//                    segment.setDataId(jsonObj.getInteger("mid"));
//                }
//            }
//        } catch (Exception e) {
//            logger.error(String.format("combine stream master data [%s] occurs error.", eventbehavior.getSubject()), e);
//        }
//        return segment;
//    }



}
