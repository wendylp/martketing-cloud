/*************************************************
 * @功能简述: 删除campaign mkt.campaign.delete
 * @项目名称: marketing cloud
 * @see: 
 * @author: yuhaixin
 * @version: 0.0.1
 * @date: 2016/6/6
 * @复审人: 
*************************************************/

package cn.rongcapital.mkt.service.impl;

import java.util.ArrayList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.CampaignActionSendSmsDao;
import cn.rongcapital.mkt.dao.CampaignHeadDao;
import cn.rongcapital.mkt.dao.SmsMaterialDao;
import cn.rongcapital.mkt.po.CampaignActionSendSms;
import cn.rongcapital.mkt.po.CampaignHead;
import cn.rongcapital.mkt.po.SmsMaterial;
import cn.rongcapital.mkt.service.CampaignDeleteService;
import cn.rongcapital.mkt.vo.BaseOutput;

@Service
public class CampaignDeleteServiceImpl implements CampaignDeleteService {
	@Autowired
	private CampaignHeadDao campaignHeadDao;
	
	@Autowired
	private CampaignActionSendSmsDao campaignActionSendSmsDao;
	
	@Autowired
	private SmsMaterialDao smsMaterialDao;
	
	/**
	 * mkt.campaign.delete
	 * @param campaignId
	 * @return BaseOutput
	 */
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public BaseOutput campaignDelete(Integer campaignId) {

		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
				ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO, null);

		CampaignHead campaignHead = new CampaignHead();
		campaignHead.setId(campaignId);
		campaignHead.setStatus(ApiConstant.TABLE_DATA_STATUS_INVALID);
		
		int rowEffected = campaignHeadDao.updateById(campaignHead);

		if (rowEffected == 0) {
			result.setMsg("不存在id为" + campaignId + "的数据");
		}else{ 
			rollbackSmsMaterialStatus(campaignId);
		}
		
		result.setTotal(result.getData().size());

		return result;
	}

	/**
	 * 活动未启动且被删除时，将活动占用的短信素材的占用状态rollback
	 */
	private void rollbackSmsMaterialStatus(Integer campaignId) {
		List<Integer> idList = new ArrayList<Integer>();
		idList.add(campaignId);
		List<CampaignHead> list = campaignHeadDao.selectListByIdListFromBasic(idList);
		CampaignHead targetC = list.get(0);
		if(targetC.getPublishStatus() == ApiConstant.CAMPAIGN_PUBLISH_STATUS_NOT_PUBLISH){ //活动未启动
			CampaignActionSendSms cass = new CampaignActionSendSms();
			cass.setCampaignHeadId(campaignId);
			List<CampaignActionSendSms> cassList = campaignActionSendSmsDao.selectList(cass);
			if(!CollectionUtils.isEmpty(cassList)){ //活动具备发短信的节点
				CampaignActionSendSms temp = cassList.get(0);
				SmsMaterial paramSmsMaterial = new SmsMaterial();
				paramSmsMaterial.setId(temp.getSmsMaterialId());
				paramSmsMaterial.setUseStatus(SmsMaterial.USE_STATUS_NO);
				smsMaterialDao.updateById(paramSmsMaterial);
			}
		}
	}

}
