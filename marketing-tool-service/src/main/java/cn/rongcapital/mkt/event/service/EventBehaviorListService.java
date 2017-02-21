package cn.rongcapital.mkt.event.service;

import cn.rongcapital.mkt.event.vo.in.EventBehavierIn;
import cn.rongcapital.mkt.event.vo.in.EventBehavierListIn;
import cn.rongcapital.mkt.event.vo.out.EventBehaviorOut;
import cn.rongcapital.mkt.vo.BaseOutput;

public interface EventBehaviorListService {

	EventBehaviorOut getEventBehavierList(EventBehavierListIn eventBehavierListIn);

	BaseOutput getEventBehavierListGet(EventBehavierIn in);

}
