package cn.rongcapital.mkt.event.service;

import cn.rongcapital.mkt.event.vo.in.EventBehavierListIn;
import cn.rongcapital.mkt.event.vo.out.EventBehaviorOut;
import cn.rongcapital.mkt.vo.BaseOutput;

public interface EventBehaviorListService {

	EventBehaviorOut getEventBehavierList(EventBehavierListIn eventBehavierListIn);

	BaseOutput getEventBehavierListGet(String objectCode, Long qrcodeId, Long beginTime, Long endTime, Integer index, Integer size);

}
