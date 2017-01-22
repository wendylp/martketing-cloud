/*************************************************
 * @功能及特点的描述简述: API接口的Controller事件类
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：营销云系统
 * @author: 谢小良
 * @version: 版本v1.6
 * @date(创建、开发日期)：2017-1-7
 * @date(最后修改日期)：2017-1-7
 * @复审人：
 *************************************************/
package cn.rongcapital.mkt.event.api.web.impl;


import java.util.Date;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import cn.rongcapital.mkt.event.api.EventWebService;
import cn.rongcapital.mkt.event.service.EventBehaviorListService;
import cn.rongcapital.mkt.event.service.EventGeneralGetService;
import cn.rongcapital.mkt.event.service.EventObjectPropsListService;
import cn.rongcapital.mkt.event.service.EventObjectSaveService;
import cn.rongcapital.mkt.event.service.EventRegisterService;
import cn.rongcapital.mkt.event.service.EventSourceSaveService;
import cn.rongcapital.mkt.event.service.EventSubscribeService;
import cn.rongcapital.mkt.event.vo.in.EventBehavierListIn;
import cn.rongcapital.mkt.event.vo.in.EventObjectVo;
import cn.rongcapital.mkt.event.vo.in.EventRegisterIn;
import cn.rongcapital.mkt.event.vo.in.EventSourceVo;
import cn.rongcapital.mkt.event.vo.in.EventSubscribeInput;
import cn.rongcapital.mkt.event.vo.out.EventBehaviorOut;
import cn.rongcapital.mkt.vo.BaseOutput;


@Controller
public final class EventWebServiceImpl implements EventWebService {

    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(EventWebServiceImpl.class);

    
    @Autowired
    private EventGeneralGetService eventGeneralGetService;
    
    @Autowired
    private EventObjectPropsListService eventObjectPropsListService;
    
    @Autowired
    private EventObjectSaveService eventObjectSaveService;
    
    @Autowired
    private EventSourceSaveService eventSourceSaveService;

    @Autowired
    private EventRegisterService eventRegisterService;

    @Autowired
    private EventSubscribeService eventSubscribeService;

    @Autowired
	private EventBehaviorListService eventBehavierListService;

    /*
     * (non-Javadoc)
     * 
     * @see cn.rongcapital.mkt.event.api.EventWebService#eventSubscribe(long, boolean)
     */
    @Override
    public BaseOutput eventSubscribe(EventSubscribeInput input) {
        return this.eventSubscribeService.eventSubscribe(input.getEventId(), input.isSubscribe());
    }

    @Override
    public BaseOutput eventRegister(@Valid EventRegisterIn registerIn) {
        //设置当前事件为非预制事件、订阅事件、可以取消订阅事件
        return this.eventRegisterService.register(registerIn, false, true, false);
    }

    @Override
    public BaseOutput getEventGeneral(Long eventId) {
        return eventGeneralGetService.getEventGeneral(eventId);
    }

    @Override
    public BaseOutput getEventObjProps(Long eventObjectId) {
        return eventObjectPropsListService.getEventObjProps(eventObjectId);
    }

    @Override
    public BaseOutput saveEventObj(EventObjectVo event) {
        return eventObjectSaveService.saveEventObj(event);
    }

    @Override
    public BaseOutput saveEventSource(EventSourceVo source) {
        return eventSourceSaveService.saveEventSource(source);
    }

	@Override
	public EventBehaviorOut getEventBehavierList(EventBehavierListIn eventBehavierListIn) {
		return eventBehavierListService.getEventBehavierList(eventBehavierListIn);
	}

	@Override
	public BaseOutput getEventBehavierListGet(String objectCode, Long qrcodeId, Long beginTime, Long endTime, Integer index, Integer size) {
		return eventBehavierListService.getEventBehavierListGet(objectCode, qrcodeId, beginTime, endTime, index, size);
	}
}
