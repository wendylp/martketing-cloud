/*************************************************
 * @功能简述: 事件接入数据统计次数
 * @项目名称: marketing cloud
 * @see:
 * @author: xie.xiaoliang
 * @version: 1.8.0
 * @date:
 * @复审人:
 *************************************************/

package cn.rongcapital.mkt.campaign.service.impl;

import cn.rongcapital.mkt.campaign.service.EventDataJoinStatisticsService;
import cn.rongcapital.mkt.mongodb.EventInvolvedDataPartyRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;
@RunWith(MockitoJUnitRunner.class)
public class EventDataJoinStatisticsServiceImplTest {
    @Mock
    private EventInvolvedDataPartyRepository eventInvolvedDataPartyRepository;

    private EventDataJoinStatisticsService service;


    @Before
    public void before() throws Exception {
        service = new EventDataJoinStatisticsServiceImpl();
    }

    @After
    public void after() throws Exception {}

    /**
     * 
     * Method: dataJoinStatisticsCount()
     * 
     */
    @Test
    public void testDataJoinStatisticsCount() throws Exception {
        long result = 3l;
        Mockito.when(eventInvolvedDataPartyRepository.count()).thenReturn(result);
        ReflectionTestUtils.setField(service, "eventInvolvedDataPartyRepository", eventInvolvedDataPartyRepository);
        long actrul =  service.dataJoinStatisticsCount();
        Assert.assertEquals(result,actrul);
    }

}
