package cn.rongcapital.mkt.unittest.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.rongcapital.mkt.service.CampaignDeleteService;
import cn.rongcapital.mkt.unittest.AbstractUnitService;

public class ServiceUnitTestDemo extends AbstractUnitService{

    @Autowired
    private CampaignDeleteService campaignDeleteService;

    @Test
	public void campaignDeleteTest(){
		campaignDeleteService.campaignDelete(1);
	}
}
