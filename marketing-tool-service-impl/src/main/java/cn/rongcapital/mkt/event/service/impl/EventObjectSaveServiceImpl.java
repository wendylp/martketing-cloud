/*************************************************
 * @功能简述: 事件客体注册
 * @项目名称: marketing cloud
 * @see:
 * @author: zhuxuelong
 * @version: 0.0.1
 * @date: 2017/1/9
 * @复审人:
 *************************************************/
package cn.rongcapital.mkt.event.service.impl;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.event.EventObjectDao;
import cn.rongcapital.mkt.event.po.EventObject;
import cn.rongcapital.mkt.event.service.EventObjectSaveService;
import cn.rongcapital.mkt.event.vo.in.EventObjectVo;
import cn.rongcapital.mkt.vo.BaseOutput;

@Service
public class EventObjectSaveServiceImpl implements EventObjectSaveService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private EventObjectDao eventObjectDao;

    @Override
    public BaseOutput saveEventObj(EventObjectVo event) {
        BaseOutput result =
                new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO,
                        null);
        EventObject eventObjPo = new EventObject();
        eventObjPo.setCode(event.getCode());
        eventObjPo.setStatus((byte) 0);
        if (CollectionUtils.isNotEmpty(eventObjectDao.selectList(eventObjPo))) {
            logger.error("event object code {} already exist.", eventObjPo.getCode());
            return new BaseOutput(ApiErrorCode.BIZ_ERROR_EVENT_OBJECT_CODE_ALREADY_EXIST.getCode(),
                    ApiErrorCode.BIZ_ERROR_EVENT_OBJECT_CODE_ALREADY_EXIST.getMsg(), ApiConstant.INT_ZERO, null);
        }

        eventObjPo.setName(event.getName());
        eventObjPo.setInstanceNameLabel(event.getInstanceNameLabel());
        eventObjPo.setInstanceNameProp(event.getInstanceNameProp());
        if (CollectionUtils.isNotEmpty(event.getAttributes())) {
            eventObjPo.setAttributes(JSON.toJSONString(event.getAttributes()));
        }
        eventObjPo.setSystemObject(false);
        eventObjectDao.insert(eventObjPo);
        return result;
    }

}
