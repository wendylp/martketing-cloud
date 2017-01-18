/*************************************************
 * @功能及特点的描述简述: 事件注册接口实现类
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：营销云系统
 * @author: 谢小良
 * @version: 版本v1.7
 * @date(创建、开发日期)：2017-1-11
 * @date(最后修改日期)：2017-1-11
 * @复审人：
 *************************************************/
package cn.rongcapital.mkt.event.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.event.EventDao;
import cn.rongcapital.mkt.dao.event.EventObjectDao;
import cn.rongcapital.mkt.dao.event.EventSourceDao;
import cn.rongcapital.mkt.event.po.Event;
import cn.rongcapital.mkt.event.po.EventObject;
import cn.rongcapital.mkt.event.po.EventSource;
import cn.rongcapital.mkt.event.service.EventRegisterService;
import cn.rongcapital.mkt.event.vo.in.EventRegisterIn;
import cn.rongcapital.mkt.vo.BaseOutput;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EventRegisterServiceImpl implements EventRegisterService {

    @Autowired
    private EventDao eventDao;

    @Autowired
    private EventSourceDao eventSourceDao;

    @Autowired
    private EventObjectDao eventObjectDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public BaseOutput register(EventRegisterIn eventRegisterIn, boolean systemEvent, boolean subscribed,
            boolean unsubscribable) {

        BaseOutput success = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
                ApiConstant.INT_ZERO, null);
        
        final Event event = new Event();

        BeanUtils.copyProperties(eventRegisterIn, event);
        
        // 根据事件源code获取事件源信息
        final EventSource eventSource = new EventSource();
        eventSource.setCode(eventRegisterIn.getSourceCode());
        eventSource.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
        List<EventSource> eventSources = this.eventSourceDao.selectList(eventSource);
        if (eventSources != null && eventSources.size() > 0) {
            event.setSourceId(eventSources.get(0).getId());
        } else {
            return new BaseOutput(ApiErrorCode.DB_ERROR_TABLE_DATA_NOT_EXIST.getCode(),
                    ApiErrorCode.DB_ERROR_TABLE_DATA_NOT_EXIST.getMsg(), ApiConstant.INT_ONE, null);
        }
        
     // 根据事件客体code获取事件客体信息
        EventObject eventObject = new EventObject();
        eventObject.setCode(eventRegisterIn.getObjectCode());
        eventObject.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
        List<EventObject> eventObjects = this.eventObjectDao.selectList(eventObject);
        if (eventObjects != null && eventObjects.size() > 0) {
            event.setObjectId(eventObjects.get(0).getId());
        } else {
            return new BaseOutput(ApiErrorCode.DB_ERROR_TABLE_DATA_NOT_EXIST.getCode(),
                    ApiErrorCode.DB_ERROR_TABLE_DATA_NOT_EXIST.getMsg(), ApiConstant.INT_ONE, null);
        }
        
        Event eventP = new Event();
        eventP.setCode(eventRegisterIn.getCode());
        eventP.setSourceId(event.getSourceId());
        eventP.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
        final List<Event> events = this.eventDao.selectList(eventP);
        if (events != null && events.size() > 0) {
            return new BaseOutput(ApiErrorCode.BIZ_ERROR_EVENT_IS_EXIST.getCode(),
                    ApiErrorCode.BIZ_ERROR_EVENT_IS_EXIST.getMsg(), ApiConstant.INT_ZERO, null);
        } else {
            // 设置系统预制事件标识
            event.setSystemEvent(systemEvent);
            // 设置是否订阅标识
            event.setSubscribed(subscribed);
            // 设置是否可以取消订阅标识
            event.setUnsubscribable(unsubscribable);
            // 将事件属性值Json to String
            event.setAttributes(JSON.toJSONString(eventRegisterIn.getAttributes()));
            // 插入数据
            this.eventDao.insert(event);
            return success;
        }
    }
}
