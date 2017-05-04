/*************************************************
 * @功能简述: 保存固定人群服务实现类
 * @项目名称: marketing cloud
 * @see:
 * @author: zhuxuelong
 * @version: 0.0.1
 * @date: 2017/4/12
 * @复审人:
 *************************************************/
package cn.rongcapital.mkt.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.dao.AudienceListPartyMapDao;
import cn.rongcapital.mkt.dao.SmsTaskDetailDao;
import cn.rongcapital.mkt.service.MQTopicService;
import cn.rongcapital.mkt.service.SmsSyncCouponService;

@Service
public class SmsSyncCouponServiceImpl implements SmsSyncCouponService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
	private MQTopicService mqTopicService;
	@Autowired
	private SmsTaskDetailDao smsTaskDetailDao;

    @Autowired
    private AudienceListPartyMapDao audienceListPartyMapDao;

	@Override
	public boolean processSmsStatus(Integer campaignHeadId, Long smsTaskHeadId, List<Long> smsTaskDetailIds) {

		return true;
	}

}
