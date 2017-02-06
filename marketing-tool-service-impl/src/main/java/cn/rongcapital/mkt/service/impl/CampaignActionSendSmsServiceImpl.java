package cn.rongcapital.mkt.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.enums.SmsTaskStatusEnum;
import cn.rongcapital.mkt.common.jedis.JedisClient;
import cn.rongcapital.mkt.common.jedis.JedisException;
import cn.rongcapital.mkt.dao.SmsMaterialDao;
import cn.rongcapital.mkt.po.CampaignActionSendSms;
import cn.rongcapital.mkt.po.SmsMaterial;
import cn.rongcapital.mkt.service.CampaignActionSendSmsService;
import cn.rongcapital.mkt.vo.in.SmsActivationCreateIn;
import cn.rongcapital.mkt.vo.in.SmsTargetAudienceIn;

@Service
public class CampaignActionSendSmsServiceImpl implements CampaignActionSendSmsService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	public static final Integer POOL_INDEX = 3;
	
	public static final Integer SMS_TARGET_CAMPAIGN=2;
	
	@Autowired
	public SmsMaterialDao smsMaterialDao;
	
	@Override
	public SmsActivationCreateIn getSmsActivationCreateIn(Integer campaignHeadId, String itemId,
			CampaignActionSendSms campaignActionSendSms) {
		SmsActivationCreateIn smsActivationCreateIn = new SmsActivationCreateIn();
		smsActivationCreateIn.setTaskAppType(campaignActionSendSms.getSmsCategoryType());
		smsActivationCreateIn.setTaskMaterialId(Long.parseLong(String.valueOf(campaignActionSendSms.getSmsMaterialId())));
		smsActivationCreateIn.setTaskName(campaignActionSendSms.getName());
		SmsMaterial smsMaterialTemp = new SmsMaterial();
		smsMaterialTemp.setId(campaignActionSendSms.getSmsMaterialId());
		List<SmsMaterial> smsMaterials = smsMaterialDao.selectList(smsMaterialTemp);
		if(CollectionUtils.isNotEmpty(smsMaterials)){
			SmsMaterial smsMaterial = smsMaterials.get(0);
			smsActivationCreateIn.setTaskSendType(smsMaterial.getChannelType().intValue());
			smsActivationCreateIn.setTaskSignatureId(Long.parseLong(String.valueOf(smsMaterial.getSmsSignId())));
			smsActivationCreateIn.setTaskMaterialContent(smsMaterial.getSmsTempletContent());
			
			List<SmsTargetAudienceIn> smsTargetAudienceInArrayList = new ArrayList<SmsTargetAudienceIn>();
			SmsTargetAudienceIn smsTargetAudienceIn = new SmsTargetAudienceIn();
			smsTargetAudienceIn.setTargetAudienceId(Long.parseLong(String.valueOf(campaignActionSendSms.getId())));
			smsTargetAudienceIn.setTargetAudienceName(campaignActionSendSms.getName());
			smsTargetAudienceIn.setTargetAudienceType(SMS_TARGET_CAMPAIGN);			
			smsActivationCreateIn.setSmsTargetAudienceInArrayList(smsTargetAudienceInArrayList);
		}		
		return smsActivationCreateIn;
	}

	@Override
	public void storeDataPartyIds(Set<Integer> dataPartyIds,Integer targetId) {
		String[] dataPartyIdsStr = (String[]) dataPartyIds.toArray();
		try {
			JedisClient.sadd(POOL_INDEX,"campaigncoverid:"+targetId, dataPartyIdsStr);
		} catch (JedisException e) {			
			logger.info(" campaigncoverid:"+targetId+" store into redis fail,"+e.getMessage());
		}
	}

}
