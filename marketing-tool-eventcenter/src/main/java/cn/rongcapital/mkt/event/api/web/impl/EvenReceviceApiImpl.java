/*************************************************
 * @功能及特点的描述简述: message（例如该类是用来做什么的）
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：MC(营销云系统)
 * @author:liuhaizhan
 * @version: 版本v1.6
 * @date(创建、开发日期)：2017年1月10日 
 * @date(最后修改日期)：2017年1月10日 
 * @复审人：
 *************************************************/

package cn.rongcapital.mkt.event.api.web.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import cn.rongcapital.mkt.event.activator.EventConstant;
import cn.rongcapital.mkt.event.api.EventReceviceApi;
import cn.rongcapital.mkt.event.service.EventDispatcher;
import cn.rongcapital.mkt.event.service.StreamEventSend;
import cn.rongcapital.mkt.vo.BaseOutput;

@Controller
public class EvenReceviceApiImpl implements EventReceviceApi {

    
   /* @Autowired
    private EventReceviceService eventReceviceService;*/
    
    
    
    @Autowired
    private EventDispatcher eventDispatcher;
    
    
    @Autowired
    private StreamEventSend streamEventSend;
    
    
    
    
    
    /* (non-Javadoc)
     * @see cn.rongcapital.mkt.event.api.EventReceviceApi#eventReceive(cn.rongcapital.mkt.event.vo.in.EventSend)
     */
    @Override
    public BaseOutput eventReceive(String eventSend) {
        // TODO Auto-generated method stub
        BaseOutput base =new BaseOutput(0,"success",1,null);
        //eventDispatcher.dispatch("MARKETING", eventSend);
        eventDispatcher.dispatch(EventConstant.EVENT_MK, eventSend);
        return base;
    }

   

    /* (non-Javadoc)
     * @see cn.rongcapital.mkt.event.api.EventReceviceApi#weixineventReceive(cn.rongcapital.mkt.event.vo.in.EventSend)
     */
    @Override
    public BaseOutput weixineventReceive(String eventSend) {
        // TODO Auto-generated method stub
        return null;
    }



    /* (non-Javadoc)
     * @see cn.rongcapital.mkt.event.api.EventReceviceApi#postTestData(java.lang.String)
     */
    @Override
    public BaseOutput postTestData(String eventSend) {
        // TODO Auto-generated method stub
        BaseOutput base =new BaseOutput(0,"success",1,null);
        streamEventSend.sendDate(eventSend);
        return base;
    }

}
