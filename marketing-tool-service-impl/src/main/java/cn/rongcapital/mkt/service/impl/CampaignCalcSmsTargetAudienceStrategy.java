package cn.rongcapital.mkt.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import cn.rongcapital.mkt.common.jedis.JedisClient;
import cn.rongcapital.mkt.common.jedis.JedisException;

@Service
public class CampaignCalcSmsTargetAudienceStrategy extends AbstractCalcSmsTargetAudienceStrategy {

	public static final Integer POOL_INDEX = 3;
	
	@Override
	protected List<Long> queryDataPartyIdList(Long targetId) {
		 List<Long> dataPartyIdList = new ArrayList<>();
	        Set<String> mids = new HashSet<>();
	        try {
	            mids = JedisClient.smembers("campaigncoverid:"+targetId, POOL_INDEX);
	        } catch (JedisException e) {
	            e.printStackTrace();
	        }
	        if(CollectionUtils.isEmpty(mids)) return null;
	        for(String mid : mids){
	            dataPartyIdList.add(Long.valueOf(mid));
	        }
	        return dataPartyIdList;
	}

}
