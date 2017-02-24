package cn.rongcapital.mkt.service.impl;

import java.lang.reflect.Array;
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
import cn.rongcapital.mkt.dao.CampaignHeadDao;
import cn.rongcapital.mkt.dao.SmsMaterialDao;
import cn.rongcapital.mkt.po.CampaignActionSendSms;
import cn.rongcapital.mkt.po.CampaignHead;
import cn.rongcapital.mkt.po.SmsMaterial;
import cn.rongcapital.mkt.service.CampaignActionSendSmsService;
import cn.rongcapital.mkt.vo.in.SmsActivationCreateIn;
import cn.rongcapital.mkt.vo.in.SmsTargetAudienceIn;

@Service
public class CampaignActionSendSmsServiceImpl implements CampaignActionSendSmsService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	public static final Integer POOL_INDEX = 3;
	
	public static final Integer SMS_TARGET_CAMPAIGN=2;
	
	public static final Integer SMS_TASK_TYPE_CAMPAIGN=1;
	
	public static final String SMS_TASK_NAME="（活动）";
	
	
	
	@Autowired
	public SmsMaterialDao smsMaterialDao;
	
	@Autowired
	public CampaignHeadDao campaignHeadDao;
	
	@Override
	public SmsActivationCreateIn getSmsActivationCreateIn(Integer campaignHeadId, String itemId,
			CampaignActionSendSms campaignActionSendSms,Set<Integer> dataPartyIds) {
		SmsActivationCreateIn smsActivationCreateIn = new SmsActivationCreateIn();
		smsActivationCreateIn.setTaskAppType(campaignActionSendSms.getSmsCategoryType());
		smsActivationCreateIn.setTaskMaterialId(Long.parseLong(String.valueOf(campaignActionSendSms.getSmsMaterialId())));
		CampaignHead campaignHeadTemp = new CampaignHead();
		campaignHeadTemp.setId(campaignHeadId);
		List<CampaignHead> campaignHeads = campaignHeadDao.selectList(campaignHeadTemp);
		CampaignHead campaignHead = campaignHeads.get(0);
		smsActivationCreateIn.setTaskName(SMS_TASK_NAME+campaignHead.getName()+"_"+campaignActionSendSms.getName());
		String smsTaskCode = campaignHeadId+"-"+itemId;
		smsActivationCreateIn.setSmsTaskCode(smsTaskCode);
		smsActivationCreateIn.setSmsTaskType(SMS_TASK_TYPE_CAMPAIGN);
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
			smsTargetAudienceInArrayList.add(smsTargetAudienceIn);
			smsActivationCreateIn.setSmsTargetAudienceInArrayList(smsTargetAudienceInArrayList);
		}
		smsActivationCreateIn.setDataPartyIds(dataPartyIds);
		return smsActivationCreateIn;
	}

	@Override
	public void storeDataPartyIds(Set<Integer> dataPartyIds,Long targetId) {
		try {
			if(dataPartyIds!=null&&dataPartyIds.size()>0){
				String[] dataPartyIdsStr = new String[dataPartyIds.size()];
				List<String> dataPartyIdsL = new ArrayList<String>();
				for(Integer dataPartyId:dataPartyIds){
					String dataPartyIdStr = String.valueOf(dataPartyId);
					dataPartyIdsL.add(dataPartyIdStr);					
				}
				dataPartyIdsStr=dataPartyIdsL.toArray(dataPartyIdsStr);
				JedisClient.sadd(POOL_INDEX,"campaigncoverid:"+targetId, dataPartyIdsStr);
			}
			
		} catch (JedisException e) {			
			logger.info(" campaigncoverid:"+targetId+" store into redis fail,"+e.getMessage());
		}
	}

}
