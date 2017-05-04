package cn.rongcapital.mkt.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.dao.SmsTaskDetailDao;
import cn.rongcapital.mkt.dao.SmsTaskDetailStateDao;
import cn.rongcapital.mkt.dao.SmsTaskHeadDao;
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
	private MQTopicService mqTopicService;
	@Autowired
	private SmsTaskHeadDao smsTaskHeadDao;
	@Autowired
	private SmsTaskDetailDao smsTaskDetailDao;
	@Autowired
	private SmsTaskDetailStateDao smsTaskDetailStateDao;

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
		smsTaskHead.setSendingFailNum(smsTaskHeads.get(0).getSendingFailNum() + len);
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

}
