package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.ReauthWechatAccountIn;

/**
 * Created by Yunfeng on 2016-6-23.
 */
public interface ReauthWechatAccountService {

    BaseOutput reauthWechatAccount(ReauthWechatAccountIn reauthWechatAccountIn);
}
