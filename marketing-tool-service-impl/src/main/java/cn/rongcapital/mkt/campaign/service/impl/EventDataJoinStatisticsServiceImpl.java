/*************************************************
 * @功能及特点的描述简述: 事件数据接入实现类
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：营销云系统
 * @author: 谢小良
 * @version: 版本v1.7
 * @date(创建、开发日期)：2017-3-13
 * @date(最后修改日期)：2017-3-13
 * @复审人：
 *************************************************/
package cn.rongcapital.mkt.campaign.service.impl;

import cn.rongcapital.mkt.campaign.service.EventDataJoinStatisticsService;
import cn.rongcapital.mkt.mongodb.EventInvolvedDataPartyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventDataJoinStatisticsServiceImpl implements EventDataJoinStatisticsService {

    @Autowired
    private EventInvolvedDataPartyRepository eventInvolvedDataPartyRepository;

    /* (non-Javadoc)
     * @see cn.rongcapital.mkt.campaign.service.EventDataJoinStatisticsService#dataJoinStatisticsCount()
     */
    @Override
    public long dataJoinStatisticsCount() {
        return this.eventInvolvedDataPartyRepository.count();
    }
}
