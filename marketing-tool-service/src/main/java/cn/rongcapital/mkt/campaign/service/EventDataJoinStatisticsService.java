/*************************************************
 * @功能及特点的描述简述: 事件数据接入统计接口
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：营销云系统
 * @author: 谢小良
 * @version: 版本v1.7
 * @date(创建、开发日期)：2017-3-13
 * @date(最后修改日期)：2017-3-13
 * @复审人：
 *************************************************/
package cn.rongcapital.mkt.campaign.service;

public interface EventDataJoinStatisticsService {

    /**
     * 统计事件接入数据的次数
     * @return 次数合计
     */
    long dataJoinStatisticsCount();
}
