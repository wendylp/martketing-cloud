/*************************************************
 * @功能简述: 取消短信模板分享
 * @项目名称: marketing cloud
 * @see:
 * @author: zhuxuelong
 * @version: 0.0.1
 * @date: 2017/2/4
 * @复审人:
 *************************************************/
package cn.rongcapital.mkt.service.impl;

import heracles.data.common.annotation.ReadWrite;
import heracles.data.common.util.ReadWriteType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dataauth.service.DataAuthService;
import cn.rongcapital.mkt.service.SmsTempletCancelShareService;
import cn.rongcapital.mkt.service.SmsTempletService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.sms.in.SmsTempletCancelShareIn;

@Service
public class SmsTempletCancelShareServiceImpl implements SmsTempletCancelShareService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DataAuthService dataAuthService;

    @Autowired
    private SmsTempletService smsTempletService;

    @Override
    @ReadWrite(type = ReadWriteType.WRITE)
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public BaseOutput cancelShareSmsTemplet(SmsTempletCancelShareIn smsTempleCancelShareIn) {
        // 验证数据有效性
        if (smsTempletService.smsTempletValidate(smsTempleCancelShareIn.getResourceId().intValue())) {
            logger.debug("table sms_templet data[id:{}] not exist.", smsTempleCancelShareIn.getResourceId());
            return new BaseOutput(ApiErrorCode.DB_ERROR_TABLE_DATA_NOT_EXIST.getCode(),
                    ApiErrorCode.DB_ERROR_TABLE_DATA_NOT_EXIST.getMsg(), ApiConstant.INT_ZERO, null);
        }
        // 取消分享
        logger.debug("organization{} unsharing sms templet:{} share_id:{}.", smsTempleCancelShareIn.getOrgId(),
                smsTempleCancelShareIn.getResourceId(), smsTempleCancelShareIn.getShareId());
        BaseOutput result =
                new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO,
                        null);
        dataAuthService.unshare(smsTempleCancelShareIn.getShareId());
        logger.debug("organization{} unshared sms templet:{} share_id:{}.", smsTempleCancelShareIn.getOrgId(),
                smsTempleCancelShareIn.getResourceId(), smsTempleCancelShareIn.getShareId());
        return result;
    }

}
