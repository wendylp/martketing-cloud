/*************************************************
 * @功能及特点的描述简述: message（例如该类是用来做什么的）
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：MC(营销云系统)
 * @author:liuhaizhan
 * @version: 版本v1.6
 * @date(创建、开发日期)：2017年6月15日 
 * @date(最后修改日期)：2017年6月15日 
 * @复审人：
 *************************************************/

package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.po.SmsTaskHead;

public interface SendSmsPrepare {

    
    SmsTaskHead  getSmSTaskHead(String taskHeadId ); 
}
