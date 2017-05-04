package cn.rongcapital.mkt.service;

import java.util.List;

/**
 * @since 1.9.0
 * @author LiuQ
 * @email Liuqi@rongcapital.cn
 */
public interface SmsSyncCouponService {
	
	/**
	 * 处理短信状态:
	 * 
	 * @param campaignHeadId
	 * @param smsTaskHeadId
	 * @param smsTaskDetailIds
	 *            无效的短信数据集
	 * @return
	 */
	default public boolean processSmsStatus(Integer campaignHeadId, Long smsTaskHeadId, List<Long> smsTaskDetailIds) {
		return false;
	}
}
