/*************************************************
 * @功能及特点的描述简述: message（例如该类是用来做什么的） 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：MC(营销云系统) @author:liuhaizhan
 * @version: 版本v1.6 @date(创建、开发日期)：2017年4月12日 @date(最后修改日期)：2017年4月12日 @复审人：
 *************************************************/

package cn.rongcapital.mkt.job.service.impl.event;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsOperations;
import org.springframework.stereotype.Component;

import cn.rongcapital.mkt.dao.CampaignEventMapDao;
import cn.rongcapital.mkt.po.mongodb.DataParty;
import cn.rongcapital.mkt.po.mongodb.Segment;
import cn.rongcapital.mkt.vo.CampaignNode;

@Component
public class BrithDayDataSendMQ {

    @Autowired
    private CampaignEventMapDao campaignEventMapDao;

    private Logger logger = LoggerFactory.getLogger(getClass());

    private ExecutorService executor = Executors.newCachedThreadPool();

    private final String eventCode = "customer_birthday_care";

    int pageSizeCnt = 3;

    @Autowired
    private JmsOperations jmsOperations;

    public void sendMQ(final Map<Integer, List<DataParty>> datamap) {
        logger.info("生日队列发送开始...");
        long begin = System.currentTimeMillis();
        int campainCount = getFirstMQNodeByEventCodeCnt(eventCode);
        int pageSize = (int) Math.ceil(campainCount / (float) pageSizeCnt);
        for (int i = 1; i <= pageSize; i++) {
            final int index = i;
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    List<CampaignNode> campaignNodes =
                            getCampaignFirstMQNodesByEventCode(eventCode, (index - 1) * pageSizeCnt, pageSizeCnt);
                    for (CampaignNode cn : campaignNodes) {
                        jmsOperations.convertAndSend(cn.getCampaignHeadId() + "-" + cn.getItemId(),
                                changeDataPartTOSegment(datamap, cn.getCaringTime()));
                        logger.info("消息队列名称:{},时间周期:{}", +cn.getCampaignHeadId() + "-" + cn.getItemId(),cn.getCaringTime());
                    }

                }
            });
        }
        executor.shutdown();

        try {
            executor.awaitTermination(1, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            executor.shutdownNow();
        }

        long end = System.currentTimeMillis();
        logger.info("生日队列发送成功结束耗时：{}", end - begin);


    }


    private List<Segment> changeDataPartTOSegment(Map<Integer, List<DataParty>> map, Integer time) {
        List<DataParty> dpy = map.get(time);
        List<Segment> sg = new ArrayList<Segment>();
        if (CollectionUtils.isNotEmpty(dpy)) {
            for (DataParty dpt : dpy) {
           logger.info("提前生日天数:{},生日日期是:{},主MID:{}",time,
         dpt.getBirthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), dpt.getMid());
                Segment sgt = new Segment();
                sgt.setDataId(dpt.getMid());
                sgt.setName(dpt.getName());
                sg.add(sgt);
            }
        }
        return sg;


    }

    private int getFirstMQNodeByEventCodeCnt(String eventCode) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("eventCode", eventCode);
        return campaignEventMapDao.getFirstMQNodeByEventCodeCnt(param);
    }


    private List<CampaignNode> getCampaignFirstMQNodesByEventCode(String eventCode, int index, int size) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("eventCode", eventCode);
        param.put("startIndex", index);
        param.put("pageSize", size);
        return campaignEventMapDao.getFirstMQNodeByEventCode(param);
    }

}
