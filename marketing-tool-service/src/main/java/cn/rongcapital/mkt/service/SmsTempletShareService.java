/*************************************************
 * @功能简述: 短信模板分享
 * @项目名称: marketing cloud
 * @see:
 * @author: zhuxuelong
 * @version: 0.0.1
 * @date: 2017/2/4
 * @复审人:
 *************************************************/
package cn.rongcapital.mkt.service;


import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.sms.in.SmsTempletShareIn;

public interface SmsTempletShareService {
    /**
     * 短信模板分享
     * 
     * @param smsTempletShareIn 分享信息
     * @return BaseOutput
     * @author zhuxuelong
     * @date: 2017/2/4
     */
    public BaseOutput shareSmsTemplet(SmsTempletShareIn smsTempletShareIn);
}
