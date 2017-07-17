package cn.rongcapital.mkt.unittest.service;

import java.util.ArrayList;
import java.util.List;

import javax.jms.JMSException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.rongcapital.mkt.po.SmsTaskBody;
import cn.rongcapital.mkt.service.SmsActivationCreateOrUpdateService;
import cn.rongcapital.mkt.unittest.AbstractUnitTest;
import cn.rongcapital.mkt.vo.in.SmsActivationCreateIn;
import cn.rongcapital.mkt.vo.in.SmsTargetAudienceIn;

public class CampaignActionSendSmsTaskTest extends AbstractUnitTest{

    @Autowired
    private SmsActivationCreateOrUpdateService service;
	
    @Test
	public void campaignActionSendSmsTaskTest(){
    	
        SmsActivationCreateIn smsActivationCreateIn = new SmsActivationCreateIn();
        smsActivationCreateIn.setTaskName("短信活动单元测试");
        smsActivationCreateIn.setTaskSignatureId(Long.valueOf(1));
        smsActivationCreateIn.setTaskMaterialId(Long.valueOf(1));
        smsActivationCreateIn.setTaskMaterialContent("单元测试发送短信");
        smsActivationCreateIn.setTaskSendType(0);
        smsActivationCreateIn.setCampaignHeadId(166);
        smsActivationCreateIn.setTaskAppType(0);
        smsActivationCreateIn.setSmsTaskType(1);
        smsActivationCreateIn.setSmsTaskCode("111222");
        //smsActivationCreateIn.setOrgId(0L);
    	
        List<SmsTargetAudienceIn> list = new ArrayList<SmsTargetAudienceIn>();
        
        SmsTargetAudienceIn entry = new SmsTargetAudienceIn();
        entry.setTargetAudienceId(1L);
        entry.setTargetAudienceName("1230");
        entry.setTargetAudienceType(2);
        list.add(entry);        
        smsActivationCreateIn.setSmsTargetAudienceInArrayList(list);
        
		try {
			service.createOrUpdateSmsActivation(smsActivationCreateIn);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
	}
    
}
