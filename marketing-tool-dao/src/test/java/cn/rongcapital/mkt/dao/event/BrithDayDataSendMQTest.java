/*************************************************
 * @功能及特点的描述简述: message（例如该类是用来做什么的） 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：MC(营销云系统) @author:liuhaizhan
 * @version: 版本v1.6 @date(创建、开发日期)：2017年4月12日 @date(最后修改日期)：2017年4月12日 @复审人：
 *************************************************/

package cn.rongcapital.mkt.dao.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsOperations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.rongcapital.mkt.dao.CampaignEventMapDao;
import cn.rongcapital.mkt.dao.testbase.AbstractUnitTest;
import cn.rongcapital.mkt.po.mongodb.DataParty;
import cn.rongcapital.mkt.po.mongodb.Segment;
import cn.rongcapital.mkt.vo.CampaignNode;

@RunWith(SpringJUnit4ClassRunner.class)
public class BrithDayDataSendMQTest extends AbstractUnitTest {


    @Autowired
    private  CampaignEventMapDao campaignEventMapDao;
    
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    private ExecutorService executor = Executors.newCachedThreadPool();
    
    int pageSizeCnt = 3;

    String eventCode = "customer_birthday_care";
  
    @Autowired
    private JmsOperations jmsOperations;
    
    private  Map<Integer,List<DataParty>> mapdata;
    
    @Before
    public void set()
    {
        DataParty dt1 =new DataParty();  
        dt1.setMid(111111);
        dt1.setName("yps111");
        DataParty dt3 =new DataParty();  
        dt3.setMid(33333);
        dt3.setName("yps333");
        DataParty dt7 =new DataParty();  
        dt7.setMid(7777);
        dt7.setName("yps888");
        DataParty dt11 =new DataParty();  
        dt11.setMid(6666);
        dt11.setName("yps666");
        List<DataParty> list1=new ArrayList<DataParty>();
        List<DataParty> list3=new ArrayList<DataParty>();
        List<DataParty> list7=new ArrayList<DataParty>();
        list1.add(dt1);
        list1.add(dt11);
        list3.add(dt3);
        list7.add(dt7);
        mapdata = new HashMap<Integer,List<DataParty>>();
        mapdata.put(1, list1);
        mapdata.put(3, list3);
        mapdata.put(7, list7);
    }

    @Test
    public  void test() {
        int campainCount = getFirstMQNodeByEventCodeCnt(eventCode);
        int pageSize = (int) Math.ceil(campainCount / (float) pageSizeCnt);
       for (int i = 1; i <= pageSize; i++) {
           final int index=i;
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    List<CampaignNode> campaignNodes =
                            getCampaignFirstMQNodesByEventCode(eventCode, (index- 1) * pageSizeCnt, pageSizeCnt);
                    for (CampaignNode cn : campaignNodes) { 
                        System.out.println("消息队列是【】"+cn.getCampaignHeadId() + "-"+cn.getItemId());
                        jmsOperations.convertAndSend(cn.getCampaignHeadId() + "-"+cn.getItemId(),changeDataPartTOSegment(mapdata,cn.getCaringTime()));
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
        }
  
    }
    
      
    
    private List<Segment> changeDataPartTOSegment(Map<Integer,List<DataParty>> map,Integer time )
    {
        List<DataParty> dpy =map.get(time);
        List<Segment> sg=new ArrayList<Segment>();
        for(DataParty dpt:dpy)
        { 
            Segment sgt= new Segment();
            sgt.setDataId(dpt.getMid());
            sgt.setName(dpt.getName());
            sg.add(sgt);
        }
        return sg;
            
        
    }

    private  int getFirstMQNodeByEventCodeCnt(String eventCode) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("eventCode", eventCode);
        return campaignEventMapDao.getFirstMQNodeByEventCodeCnt(param);
    }


    private  List<CampaignNode> getCampaignFirstMQNodesByEventCode(String eventCode, int index, int size) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("eventCode", eventCode);
        param.put("startIndex", index);
        param.put("pageSize", size);
        return campaignEventMapDao.getFirstMQNodeByEventCode(param);
    }


}
