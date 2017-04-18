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

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.enums.StatusEnum;
import cn.rongcapital.mkt.common.util.NumUtil;
import cn.rongcapital.mkt.dao.SmsTempletDao;
import cn.rongcapital.mkt.dataauth.interceptor.DataAuthUnshare;
import cn.rongcapital.mkt.dataauth.interceptor.ParamType;
import cn.rongcapital.mkt.dataauth.service.DataAuthService;
import cn.rongcapital.mkt.po.SmsTemplet;
import cn.rongcapital.mkt.service.SmsTempletCancelShareService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.sms.in.SmsTempletCancelShareIn;
import heracles.data.common.annotation.ReadWrite;
import heracles.data.common.util.ReadWriteType;

@Service
public class SmsTempletCancelShareServiceImpl implements SmsTempletCancelShareService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DataAuthService dataAuthService;

    @Autowired
    private SmsTempletDao smsTempletDao;

    @Override
    @ReadWrite(type = ReadWriteType.WRITE)
    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @DataAuthUnshare(shareId = "#smsTempleCancelShareIn.shareIds",orgId="",type = ParamType.SpEl)
    public BaseOutput cancelShareSmsTemplet(SmsTempletCancelShareIn smsTempleCancelShareIn) {
        // 验证数据有效性
        if (smsTempletValidate(smsTempleCancelShareIn.getResourceId().intValue())) {
            logger.debug("table sms_templet data[id:{}] not exist.", smsTempleCancelShareIn.getResourceId());
            return new BaseOutput(ApiErrorCode.DB_ERROR_TABLE_DATA_NOT_EXIST.getCode(),
                    ApiErrorCode.DB_ERROR_TABLE_DATA_NOT_EXIST.getMsg(), ApiConstant.INT_ZERO, null);
        }
        BaseOutput result =
                new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO,
                        null);
        //通过代码实现取消分享权限
        // 取消分享
/*        smsTempleCancelShareIn.getShareIds().forEach(item -> {
            logger.debug("unsharing sms templet:{} share_id:{}.", smsTempleCancelShareIn.getResourceId(), item);
            dataAuthService.unshare(item);
            logger.debug("unshared sms templet:{} share_id:{}.", smsTempleCancelShareIn.getResourceId(), item);
        });*/
        return result;
    }

    /**
     * 验证模板是否存在
     * 
     * @param id
     * @return
     */
    private boolean smsTempletValidate(Integer id) {
        SmsTemplet smsTemplet = new SmsTemplet();
        smsTemplet.setId(id);
        smsTemplet.setStatus(NumUtil.int2OneByte(StatusEnum.ACTIVE.getStatusCode()));
        List<SmsTemplet> smsTempletList = smsTempletDao.selectList(smsTemplet);
        if (CollectionUtils.isNotEmpty(smsTempletList)) {
            return false;
        } else {
            return true;
        }
    }

}
