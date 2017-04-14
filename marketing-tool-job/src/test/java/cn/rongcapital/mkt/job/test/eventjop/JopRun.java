/*************************************************
 * @功能及特点的描述简述: message（例如该类是用来做什么的）
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：MC(营销云系统)
 * @author:liuhaizhan
 * @version: 版本v1.6
 * @date(创建、开发日期)：2017年4月12日 
 * @date(最后修改日期)：2017年4月12日 
 * @复审人：
 *************************************************/

package cn.rongcapital.mkt.job.test.eventjop;

import java.time.ZoneId;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.rongcapital.mkt.job.service.impl.event.BrithDayDataSendMQ;
import cn.rongcapital.mkt.job.service.impl.event.DataPartQueryTaskImpl;
import cn.rongcapital.mkt.job.service.impl.event.SendBrithDayToEventCenter;
import cn.rongcapital.mkt.po.mongodb.DataParty;
import cn.rongcapital.mkt.testbase.AbstractUnitTest;




@RunWith(SpringJUnit4ClassRunner.class)
public class JopRun extends AbstractUnitTest {
    
    @Autowired
    private DataPartQueryTaskImpl dataPartQueryTaskImpl;

    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private BrithDayDataSendMQ  brithDayDataSendMQ;
    
    @Autowired
    private SendBrithDayToEventCenter  sendBrithDayToEventCenter;

     
    @Test
    public void test() {

        Map<Integer, List<DataParty>> dataParty = dataPartQueryTaskImpl.getDataBritDay();
        
        for (Map.Entry<Integer, List<DataParty>> entry : dataParty.entrySet()) {
            List<DataParty> list = entry.getValue();
            for (DataParty dp : list) {
                logger.info("提前的天数{},生日日期是:{},主MID:{}", entry.getKey(),
                        dp.getBirthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), dp.getMid());
            }

        }

        if(!dataParty.isEmpty()&&dataParty.size()>0)
        {
            
            brithDayDataSendMQ.sendMQ(dataParty);
            sendBrithDayToEventCenter.SendBrithEventCenter(dataParty);
            
        }
        
  
        logger.info("生日发送提醒任务结束...");
        
    }

}
