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


import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;

import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import cn.rongcapital.mkt.event.api.EventWebService;
import cn.rongcapital.mkt.event.po.EventObject;
import cn.rongcapital.mkt.event.service.EventBehaviorService;
import cn.rongcapital.mkt.event.service.EventObjectService;
import cn.rongcapital.mkt.event.vo.out.EventListOut;
import cn.rongcapital.mkt.po.mongodb.event.EventBehavior;


@Controller
public final class EventWebServiceImpl implements EventWebService {

    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(EventWebServiceImpl.class);
    @Autowired(required = false)
    private cn.rongcapital.mkt.event.service.EventService eventService;
    
    @Autowired(required = false)
    private EventBehaviorService eventBehaviorService;
    
    @Autowired
    private EventObjectService eventObjectService;

    @Override
    public EventListOut getEventListByKeyword(@NotEmpty @QueryParam("user_token") String userToken, @NotEmpty @QueryParam("ver") String ver, @QueryParam("keyword") String keyword, @DefaultValue("1") @Min(1) @QueryParam("index") Integer index, @DefaultValue("10") @Min(1) @Max(100) @QueryParam("size") Integer size) throws Exception {
        return eventService.selectList();
    }

    /* (non-Javadoc)
     * @see cn.rongcapital.mkt.event.api.EventService#getEventBehaviorListByKeyword(java.lang.String, java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer)
     */
    @Override
    public List<EventBehavior> getEventBehaviorListByKeyword(String userToken, String ver, String keyword, Integer index,
            Integer size) throws Exception {
        LOGGER.info("=====================start get data======================");
        return eventBehaviorService.selectList();
    }

    /* (non-Javadoc)
     * @see cn.rongcapital.mkt.event.api.EventService#selectById(java.lang.Integer)
     */
    @Override
    public EventObject selectById(Integer eventObjectId) {
        LOGGER.info("=====================start get data======================");
        return this.eventObjectService.selectById(eventObjectId);
    }
}
