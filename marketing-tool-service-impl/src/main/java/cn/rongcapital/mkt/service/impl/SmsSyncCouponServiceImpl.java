package cn.rongcapital.mkt.service.impl;

import static cn.rongcapital.mkt.common.enums.SmsTempletTypeEnum.FIXED;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import cn.rongcapital.mkt.bbx.service.BbxCouponCodeAddService;
import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.dao.SmsMaterialDao;
import cn.rongcapital.mkt.dao.SmsTaskDetailDao;
import cn.rongcapital.mkt.dao.SmsTaskDetailStateDao;
import cn.rongcapital.mkt.dao.SmsTaskHeadDao;
import cn.rongcapital.mkt.po.SmsMaterial;
import cn.rongcapital.mkt.po.SmsTaskDetail;
import cn.rongcapital.mkt.po.SmsTaskHead;
import cn.rongcapital.mkt.service.MQTopicService;
import cn.rongcapital.mkt.service.SmsSyncCouponService;

/**
 * @since 1.9.0
 * @author LiuQ
 * @email Liuqi@rongcapital.cn
 */
@Service
public class SmsSyncCouponServiceImpl implements SmsSyncCouponService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private SmsMaterialDao smsMaterialDao;
	@Autowired
	private MQTopicService mqTopicService;
	@Autowired
	private SmsTaskHeadDao smsTaskHeadDao;
	@Autowired
	private SmsTaskDetailDao smsTaskDetailDao;
	@Autowired
	private SmsTaskDetailStateDao smsTaskDetailStateDao;
	@Autowired
	private BbxCouponCodeAddService couponCodeAddService;

	@Override
	public boolean processSmsStatus(Integer campaignHeadId, Long smsTaskHeadId, List<Long> smsTaskDetailIds) {
		if (smsTaskHeadId == null) {
			logger.error("无效的参数：smsTaskHeadId={}", smsTaskHeadId);
			return false;
		}
		if (smsTaskDetailIds == null) {
			logger.error("无效的数据集：smsTaskDetailIds={}", smsTaskDetailIds);
			return false;
		}
		if (smsTaskDetailIds.isEmpty()) {
			logger.info("空的数据集, 不需要调整短信任务：smsTaskDetailIds");
			return true;
		}
		List<Integer> ids = convert(smsTaskDetailIds);
		if (!this.updateSmsTaskHead(smsTaskHeadId, ids.size())) {
			return false;
		}
		logger.debug("参数列表：campaignHeadId={}, smsTaskHeadId={}, smsTaskDetailIds={}", campaignHeadId, smsTaskHeadId, ids);
		smsTaskDetailDao.batchUpdateById(ids);
		smsTaskDetailStateDao.batchUpdateByDetailId(ids);
		new Thread() {
			public void run() {
				mqTopicService.sendSmsByTaskId(smsTaskHeadId.toString());
			}
		}.start();
		return true;
	}

	public boolean updateSmsTaskHead(Long smsTaskHeadId, int len) {
		SmsTaskHead smsTaskHead = new SmsTaskHead();
		smsTaskHead.setId(smsTaskHeadId);
		List<SmsTaskHead> smsTaskHeads = smsTaskHeadDao.selectList(smsTaskHead);
		if (smsTaskHeads == null || smsTaskHeads.isEmpty() || smsTaskHeads.size() > 1) {
			logger.error("没有找到对应的唯一短信任务头信息：sms_task_head_id={}", smsTaskHeadId);
			return false;
		}
		int fail = smsTaskHeads.get(0).getSendingFailNum() + len; // 失败数
		int wait = smsTaskHeads.get(0).getWaitingNum() - len; // 等待数
		smsTaskHead.setSendingFailNum(fail > 0 ? fail : 0);
		smsTaskHead.setWaitingNum(wait > 0 ? wait : 0);
		smsTaskHeadDao.updateById(smsTaskHead);
		return true;
	}

	public List<Integer> convert(List<Long> list) {
		List<Integer> intList = new ArrayList<Integer>();
		for (Long cur : list) {
			if (cur == null) {
				continue;
			}
			intList.add(cur.intValue());
		}
		return intList;
	}

	@Override
	public void beforeProcessSmsStatus(String smsSendTaskId) {
		if (StringUtils.isBlank(smsSendTaskId)) {
			logger.error("无效的smsTaskHeadId={}", smsSendTaskId);
			return;
		}

		Long smsSendHeadId = Long.valueOf(smsSendTaskId);
		SmsTaskHead paramSmsTaskHead = new SmsTaskHead();
		paramSmsTaskHead.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		paramSmsTaskHead.setId(smsSendHeadId);
		List<SmsTaskHead> smsTaskHeads = smsTaskHeadDao.selectList(paramSmsTaskHead);
		if (CollectionUtils.isEmpty(smsTaskHeads)) {
			logger.error("没有找到对应的唯一短信任务头信息：sms_task_head_id={}", smsSendHeadId);
			return;
		}
		logger.info("taskHeadId :" + smsSendTaskId);

		SmsTaskHead targetHead = smsTaskHeads.get(0);
		SmsMaterial paramSmsMaterial = new SmsMaterial();
		paramSmsMaterial.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		paramSmsMaterial.setId(targetHead.getSmsTaskMaterialId().intValue());
		List<SmsMaterial> targetSmsMaterialList = smsMaterialDao.selectList(paramSmsMaterial);
		if (CollectionUtils.isEmpty(targetSmsMaterialList)) {
			mqTopicService.sendSmsByTaskId(smsSendTaskId);
		}
		Integer smsType = targetSmsMaterialList.get(0).getSmsType().intValue(); // 短信类型：0:固定短信,1:变量短信
		if (smsType.equals(FIXED.getStatusCode())) {
			mqTopicService.sendSmsByTaskId(smsSendTaskId);
		} else {
			Integer campaignHeadId = targetHead.getCampaignHeadId();
			String campaignItemId = targetHead.getSmsTaskCode();
			if (StringUtils.isNotBlank(campaignItemId)) {
				campaignItemId = campaignItemId.substring(campaignItemId.indexOf("-") + 1);
			}
			SmsTaskDetail smsDetail = new SmsTaskDetail();
			smsDetail.setSmsTaskHeadId(smsSendHeadId);
			smsDetail.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
			smsDetail.setPageSize(null);
			smsDetail.setStartIndex(null);
			smsDetail.setSendStatus(ApiConstant.SMS_TASK_PROCESS_STATUS_WRITING);
			List<SmsTaskDetail> smsDetailList = smsTaskDetailDao.selectList(smsDetail);
			// 同步优惠券
			couponCodeAddService.addCouponCodeToBBX(smsDetailList, campaignHeadId, smsSendHeadId, campaignItemId);
		}
	}

}
