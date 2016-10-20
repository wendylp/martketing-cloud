package cn.rongcapital.mkt.service.impl;

import javax.ws.rs.core.SecurityContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.service.SmsMessageSendTestService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.SmsMessageSendTestIn;
@Service
public class SmsMessageSendTestServiceImpl implements SmsMessageSendTestService{
    
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 接口：mkt.sms.message.send.test 
     * 根据传入的手机号和短信内容进行测试发送
     * 
     * @param body
     * @param securityContext
     * @return
     * @author shuiyangyang
     * @Date 2016.10.20
     */
    @Override
    public BaseOutput messageSendTest(SmsMessageSendTestIn body,
                    SecurityContext securityContext) {
        BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
                        ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO, null);
        logger.info("发送手机号：{} || 信息内容：{}", body.getReceiveMobiles(), body.getSendMessage());
        String receiveMobiles = body.getReceiveMobiles();
        String[] receiveMobileArrays = receiveMobiles.split(",");
        String sendMessage = body.getSendMessage();
        
        return result;
    }

}
