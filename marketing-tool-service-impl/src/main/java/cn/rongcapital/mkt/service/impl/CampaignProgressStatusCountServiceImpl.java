/*************************************************
 * @功能简述: CampaignProgressstatusCountService实现类
 * @项目名称: marketing cloud
 * @see: 
 * @author: yuhaixin
 * @version: 0.0.1
 * @date: 2016/6/6
 * @复审人: 
*************************************************/

package cn.rongcapital.mkt.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.dao.CampaignHeadDao;
import cn.rongcapital.mkt.service.CampaignProgressStatusCountService;
import cn.rongcapital.mkt.vo.BaseOutput;

@Service
public class CampaignProgressStatusCountServiceImpl implements
		CampaignProgressStatusCountService {
	@Autowired
	private  CampaignHeadDao campaignHeadDao;
	 
	
	@Override
	public BaseOutput campaignProgressStatusCountGet() {
		Map<String,Object> campaignHeadCount = campaignHeadDao.selectCampaignHeadCountGroupByPublishStatus();
		return null;
	}

}
