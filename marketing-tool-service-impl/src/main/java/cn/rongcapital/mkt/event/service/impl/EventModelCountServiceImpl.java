package cn.rongcapital.mkt.event.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.enums.event.EventChannelEnum;
import cn.rongcapital.mkt.dao.event.EventDao;
import cn.rongcapital.mkt.event.service.EventModelCountService;
import cn.rongcapital.mkt.event.vo.EventModelCount;
import cn.rongcapital.mkt.event.vo.out.EventModelCountOut;
import cn.rongcapital.mkt.vo.BaseOutput;

@Service
public class EventModelCountServiceImpl implements EventModelCountService{

    @Autowired
    private EventDao eventDao;
	@Override
	public BaseOutput getEventModelCountList() {
        BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
                ApiConstant.INT_ZERO, null);
		
        EventModelCountOut emCount = new EventModelCountOut(0l,0l,0l);
		List<EventModelCount> list = eventDao.getEventModelCountList();
		if (!CollectionUtils.isEmpty(list)) {
			for (EventModelCount e : list) {
				if(e != null && e.getChannel()!=null && EventChannelEnum.contains(e.getChannel())){
		 			switch (EventChannelEnum.getByCode(e.getChannel())){
						case CHANNEL1: 
							emCount.setFirstChannelCount(e.getCount()==null ? 0l: e.getCount().longValue());
							break;
						case CHANNEL2: 
							emCount.setSecondChannelCount(e.getCount()==null ? 0l: e.getCount().longValue());
							break;
						case CHANNEL3: 
							emCount.setThirdChannelCount(e.getCount()==null ? 0l: e.getCount().longValue());
							break;
					}
				}
			}
		}
		result.getData().add(emCount);
		
		return result;
	}

}
