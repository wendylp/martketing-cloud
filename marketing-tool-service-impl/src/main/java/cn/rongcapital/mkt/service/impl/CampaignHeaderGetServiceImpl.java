package cn.rongcapital.mkt.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.CampaignHeadDao;
import cn.rongcapital.mkt.po.CampaignHead;
import cn.rongcapital.mkt.service.CampaignHeaderGetService;
import cn.rongcapital.mkt.vo.BaseOutput;

@Service
public class CampaignHeaderGetServiceImpl implements CampaignHeaderGetService {
	
	@Autowired
	CampaignHeadDao campaignHeadDao;

	@Override
	public Object campaignHeaderGet(String userToken, String ver, int campaignHeadId) {
		CampaignHead t = new CampaignHead();
		t.setId(campaignHeadId);
		t.setStatus((byte)ApiConstant.INT_ZERO);
		List<CampaignHead> list = campaignHeadDao.selectList(t);
		
		BaseOutput out = new BaseOutput(ApiConstant.INT_ZERO,ApiErrorCode.SUCCESS.getMsg(),ApiConstant.INT_ZERO,null);
		
		Map<String,Object> map = new HashMap<String,Object>();
		if(null != list && list.size() > 0){
			CampaignHead obj = list.get(0);
			map.put("campaign_name", obj.getName());
			map.put("publish_status", obj.getPublishStatus());
			out.getData().add(map);
		}
		out.setTotal(out.getData().size());
		return Response.ok().entity(out).build();
	}

}
