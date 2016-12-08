package cn.rongcapital.mkt.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.SmsTempletDao;
import cn.rongcapital.mkt.service.SmstempletCountGetService;
import cn.rongcapital.mkt.vo.BaseOutput;

@Service
public class SmstempletCountGetServiceImpl implements SmstempletCountGetService {

    @Autowired
    private SmsTempletDao smsTempletDao;
	
	@Override
	public BaseOutput getSmsTempletCount(String channelType) {
		
        BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
                ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO, null);
		
		List<Map<String,String>> countMap = smsTempletDao.getTempletCountByType(channelType );
		result.getData().addAll(countMap);
		
		return result;
	}

}
