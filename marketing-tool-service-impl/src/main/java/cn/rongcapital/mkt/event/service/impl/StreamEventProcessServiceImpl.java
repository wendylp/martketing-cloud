/*************************************************
 * @功能简述: Stream事件处理
 * @项目名称: marketing cloud
 * @see:
 * @author: zhuxuelong
 * @version: 0.0.1
 * @date: 2017/3/1
 * @复审人:
 *************************************************/
package cn.rongcapital.mkt.event.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsOperations;
import org.springframework.stereotype.Component;

import cn.rongcapital.mkt.dao.CampaignEventMapDao;
import cn.rongcapital.mkt.event.service.EventSubjectCombineService;
import cn.rongcapital.mkt.po.mongodb.Segment;
import cn.rongcapital.mkt.po.mongodb.event.EventBehavior;
import cn.rongcapital.mkt.vo.CampaignNode;

import com.alibaba.fastjson.JSON;

@Component
public class StreamEventProcessServiceImpl {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private EventSubjectCombineService eventSubjectCombineService;

    @Autowired
    private JmsOperations jmsOperations;

    @Autowired
    private CampaignEventMapDao campaignEventMapDao;

    @JmsListener(destination = "queue.streamEvents")
    public void process(String event) {
        // 0.事件对象映射校验
        EventBehavior eventbehavior = parseSafeParam(event);
        if (eventbehavior == null) {
            logger.error("stream event [{}] is illegal.", event);
            return;
        }
        // 1.根据事件编码判断是否需要主数据合并
        String eventCode = eventbehavior.getEvent().get("code").toString();
        if (!eventSubjectCombineService.needCombine(eventCode)) {
            return;
        }
        Segment segment = combineStreamData(eventbehavior);
        if (segment == null) {
            logger.error("combine master data from stream event [{}] occurs problem.", event);
            return;
        }
        List<Segment> segments = Arrays.asList(segment);
        // 2.获取活动包含事件触发的任务首节点
        int pageSizeCnt = 100;
        int pageSize = (int) Math.floor(getFirstMQNodeByEventCodeCnt(eventCode) / pageSizeCnt + 1);
        for (int i = 1; i <= pageSize; i++) {
            List<CampaignNode> campaignNodes =
                    getCampaignFirstMQNodesByEventCode(eventCode, (pageSize - 1) * pageSizeCnt, pageSizeCnt);
            for (CampaignNode campaignNode : campaignNodes) {
                // 3.创建首节点需要的队列并传输主数据
                jmsOperations.convertAndSend(campaignNode.getCampaignHeadId() + "-" + campaignNode.getItemId(),
                        segments);
            }
        }
    }

    private EventBehavior parseSafeParam(String event) {
        EventBehavior eventbehavior = null;
        try {
            eventbehavior = JSON.parseObject(event, EventBehavior.class);
            if (eventbehavior == null || eventbehavior.getEvent() == null || eventbehavior.getEvent().isEmpty()
                    || !eventbehavior.getEvent().containsKey("code")
                    || StringUtils.isBlank(eventbehavior.getEvent().get("code").toString())
                    || eventbehavior.getObject() == null || eventbehavior.getObject().isEmpty()
                    || eventbehavior.getSubject() == null || eventbehavior.getSubject().isEmpty()) {
                eventbehavior = null;
            }
        } catch (Exception e) {
            logger.error(String.format("parse stream event [%s] occurs error.", event), e);
        }
        return eventbehavior;
    }


    private Segment combineStreamData(EventBehavior eventbehavior) {
        Segment segment = new Segment();
        return segment;
    }


    private List<CampaignNode> getCampaignFirstMQNodesByEventCode(String eventCode, int index, int size) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("eventCode", eventCode);
        param.put("startIndex", index);
        param.put("pageSize", size);
        return campaignEventMapDao.getFirstMQNodeByEventCode(param);
    }

    private int getFirstMQNodeByEventCodeCnt(String eventCode) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("eventCode", eventCode);
        return campaignEventMapDao.getFirstMQNodeByEventCodeCnt(param);
    }

}
