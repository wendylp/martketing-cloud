/*************************************************
 * @功能及特点的描述简述: 事件注册业务接口类
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：营销云系统
 * @author: 谢小良
 * @version: 版本v1.7
 * @date(创建、开发日期)：2017-1-11
 * @date(最后修改日期)：2017-1-11
 * @复审人：
 *************************************************/
package cn.rongcapital.mkt.event.service;

import cn.rongcapital.mkt.event.vo.in.EventRegisterIn;
import cn.rongcapital.mkt.vo.BaseOutput;

public interface EventRegisterService {

    /**
     * 事件注册
     * @param eventRegisterIn
     * @param systemEvent
     * @param subscribed
     * @param unsubscribable
     * @return
     * @date(最后修改日期)：2017-1-11
     * @author xiexiaoliang
     */
    BaseOutput register(EventRegisterIn eventRegisterIn, boolean systemEvent, boolean subscribed, boolean unsubscribable);
}
