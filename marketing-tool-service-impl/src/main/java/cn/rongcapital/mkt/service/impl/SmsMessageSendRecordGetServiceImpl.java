/**
 * 
 */
package cn.rongcapital.mkt.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.util.DateUtil;
import cn.rongcapital.mkt.dao.SmsTaskDetailDao;
import cn.rongcapital.mkt.po.MessageSendRecordGetOut;
import cn.rongcapital.mkt.po.SmsTaskDetail;
import cn.rongcapital.mkt.service.SmsMessageSendRecordGetService;
import cn.rongcapital.mkt.vo.BaseOutput;

/**
 * @author shuiyangyang
 *
 */
@Service
public class SmsMessageSendRecordGetServiceImpl implements SmsMessageSendRecordGetService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SmsTaskDetailDao smsTaskDetailDao;

    /**
     * 返回当前任务的发送记录列表（可以按照手机号查询）
     * 
     * @param smsTaskHeadId
     * @param receiveMobile
     * @return
     * @author shuiyangyang
     * @Date 2016.10.18
     */
    @Override
    public BaseOutput messageSendRecordGet(Long smsTaskHeadId, String receiveMobile, Integer index, Integer size) {

        BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
                ApiConstant.INT_ZERO, null);

        SmsTaskDetail smsTaskDetail = new SmsTaskDetail();
        smsTaskDetail.setSmsTaskHeadId(smsTaskHeadId);
        if (receiveMobile != null && receiveMobile.length() > 0) {
            smsTaskDetail.setReceiveMobile(receiveMobile);
        }
        smsTaskDetail.setStartIndex(index);
        smsTaskDetail.setPageSize(size);

        List<MessageSendRecordGetOut> messageSendRecordGetOutLists =
                smsTaskDetailDao.messageSendRecordGet(smsTaskDetail);

        if (messageSendRecordGetOutLists != null && messageSendRecordGetOutLists.size() > 0) {
            result.setTotal(messageSendRecordGetOutLists.size());
            result.getData().addAll(messageSendRecordGetOutLists);
        }

        return result;
    }


}
