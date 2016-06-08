/*************************************************
 * @功能简述: 获取不同状态下的campaign列表
 * @项目名称: marketing cloud
 * @see: 
 * @author: yuhaixin
 * @version: 0.0.1
 * @date: 2016/6/7
 * @复审人: 
*************************************************/

package cn.rongcapital.mkt.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.dao.CampaignHeadDao;
import cn.rongcapital.mkt.service.CampaignProgressStatusListService;
import cn.rongcapital.mkt.vo.BaseOutput;

@Service
public class CampaignProgressStatusListServiceImpl implements
		CampaignProgressStatusListService {
	
	@Autowired
	private CampaignHeadDao campaignHeadDao;
	
	/**
	 * mkt.campaign.progressstatus.list.get
	 * @param publishStatus campaignName index size
	 * @return Object
	 */
	@Override
	public BaseOutput campaignProgressStatusList(Integer publishStatus,
			String campaignName, Integer index, Integer size) {
		Map<String,Object> paramMap = new HashMap<String, Object>();
		paramMap.put("publish_status", publishStatus);
		paramMap.put("campaign_name", campaignName);
		paramMap.put("index", index);
		paramMap.put("size", size);
		
		//根据publish_status以及campaign_name从campaign_head表中查询
		campaignHeadDao.selectCampaignProgressStatusListByPublishStatus(paramMap);
		return null;
	}
}
