/*************************************************
 * @功能及特点的描述简述: message（例如该类是用来做什么的）
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：MC(营销云系统)
 * @author:liuhaizhan
 * @version: 版本v1.6
 * @date(创建、开发日期)：2017年4月14日 
 * @date(最后修改日期)：2017年4月14日 
 * @复审人：
 *************************************************/

package cn.rongcapital.mkt.job.service.base;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.djob.JobExecutor;
import cn.rongcapital.djob.dto.JobContext;
import cn.rongcapital.mkt.job.service.impl.event.BrithDayDataSendMQ;
import cn.rongcapital.mkt.job.service.impl.event.DataPartQueryTaskImpl;
import cn.rongcapital.mkt.job.service.impl.event.SendBrithDayToEventCenter;
import cn.rongcapital.mkt.po.mongodb.DataParty;

@Service
public class BrithDayEventService implements JobExecutor {
    
    private Logger logger = LoggerFactory.getLogger(getClass()); 
    
    
    @Autowired
    DataPartQueryTaskImpl  dataPartQueryTaskImpl;
    
    @Autowired
    BrithDayDataSendMQ  brithDayDataSendMQ;
    
    @Autowired
    SendBrithDayToEventCenter sendBrithDayToEventCenter;
    
    
    Map<Integer,List<DataParty>> dataParty;
    
    public void execute(JobContext arg0) {
        // TODO Auto-generated method stub
        
        logger.info("生日关怀事Jop开始.....");
        dataParty=dataPartQueryTaskImpl.getDataBritDay();
        if(dataParty!=null && dataParty.size()>0)
        {
            
            brithDayDataSendMQ.sendMQ(dataParty);
            //sendBrithDayToEventCenter.SendBrithEventCenter(dataParty);
            
            
        }
            
        logger.info("生日关怀事Jop结束.....");
        
    }
    
}
