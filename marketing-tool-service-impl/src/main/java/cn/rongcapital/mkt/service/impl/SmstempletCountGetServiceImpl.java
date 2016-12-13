package cn.rongcapital.mkt.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.enums.SmsTempletTypeEnum;
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
		
		List<Map<String,Object>> countMaps = smsTempletDao.getTempletCountByType(channelType );
		
		Map<String,Integer> resultMap = new HashMap<String,Integer>();
		
		Integer fixedCount = 0;
		Integer variableCount = 0;
		
		for(Map<String,Object> countMap : countMaps){
			
			Object type= countMap.get("type");
			
			if(SmsTempletTypeEnum.FIXED.getStatusCode() == Integer.valueOf(type.toString())){
				 
				fixedCount = Integer.valueOf(countMap.get("count").toString());
				 
			}else if(SmsTempletTypeEnum.VARIABLE.getStatusCode() == type){
				variableCount = Integer.valueOf(countMap.get("count").toString());
			}
			
		}
		
		resultMap.put(SmsTempletTypeEnum.FIXED.getStatusCode()+"", fixedCount);
		resultMap.put(SmsTempletTypeEnum.VARIABLE.getStatusCode()+"", variableCount);
		
		result.getData().add(resultMap);
		
		return result;
	}

}
