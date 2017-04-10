/*************************************************
 * @功能简述: Stream事件处理测试类
 * @项目名称: marketing cloud
 * @see:
 * @author: zhuxuelong
 * @version: 0.0.1
 * @date:
 * @复审人:
 *************************************************/
package cn.rongcapital.mkt.campaign.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.jms.core.JmsOperations;
import org.springframework.test.util.ReflectionTestUtils;

import cn.rongcapital.mkt.campaign.service.EventSubjectCombineService;
import cn.rongcapital.mkt.dao.CampaignEventMapDao;
import cn.rongcapital.mkt.dao.DataPartyDao;
import cn.rongcapital.mkt.event.vo.EventSubjectCombineResult;
import cn.rongcapital.mkt.mongodb.EventInvolvedDataPartyRepository;
import cn.rongcapital.mkt.po.DataParty;
import cn.rongcapital.mkt.po.mongodb.EventInvolvedDataParty;
import cn.rongcapital.mkt.po.mongodb.Segment;
import cn.rongcapital.mkt.vo.CampaignNode;

@RunWith(MockitoJUnitRunner.class)
public class StreamEventProcessServiceImplTest {

    private StreamEventProcessServiceImpl service;

    @Mock
    private EventSubjectCombineService eventSubjectCombineService;

    @Mock
    private CampaignEventMapDao campaignEventMapDao;

    @Mock
    private JmsOperations jmsOperations;

    @Mock
    private EventInvolvedDataPartyRepository eventInvolvedDataPartyRepository;
    
    @Mock
    private DataPartyDao dataPartyDao;
    
    @Before
    public void setUp() throws Exception {
        service = new StreamEventProcessServiceImpl();
    }

    @After
    public void tearDown() throws Exception {}


    /**
     * eventbehavior == null
     */
    @Test
    public void testProcess01() {
        String event = "{}";
        service.process(event);
    }

    /**
     * eventbehavior.getEvent() == null
     */
    @Test
    public void testProcess02() {
        String event = "{\"event\":{},\"subject\":{\"openid\":\"12345\"}}";
        service.process(event);
    }

    /**
     * !eventbehavior.getEvent().containsKey("code")
     */
    @Test
    public void testProcess03() {
        String event = "{\"event\":{\"attributes\" :{}},\"subject\":{\"openid\":\"12345\"}}";
        service.process(event);
    }

    /**
     * StringUtils.isBlank(eventbehavior.getEvent().get("code").toString()
     */
    @Test
    public void testProcess04() {
        String event = "{\"event\":{\"code\" :\"\"},\"subject\":{\"openid\":\"12345\"}}";
        service.process(event);
    }

    /**
     * eventbehavior.getSubject() == null || eventbehavior.getSubject().isEmpty()
     */
    @Test
    public void testProcess05() {
        String event = "{\"event\":{\"code\" :\"123\"}, \"subject\":{}}";
        service.process(event);
    }

    /**
     * parse stream event occurs error
     */
    @Test
    public void testProcess06() {
        String event = "{123455}";
        service.process(event);
    }

    /**
     * 主数据不合并
     */
    @Test
    public void testProcess07() {
        String event = "{\"event\":{\"code\" :\"XXX\"},\"subject\":{\"openid\":\"12345\"}}";
        Mockito.when(eventSubjectCombineService.needCombine("XXX")).thenReturn(false);
        ReflectionTestUtils.setField(service, "eventSubjectCombineService", eventSubjectCombineService);
        service.process(event);
    }

    /**
     * 主数据合并(返回合并结果为空)
     */
    @Test
    public void testProcess07_1() {
        String event = "{\"event\":{\"code\" :\"XXX\"},\"subject\":{\"openid\":\"12345\"}}";
        Mockito.when(eventSubjectCombineService.needCombine("XXX")).thenReturn(true);
        Mockito.when(eventSubjectCombineService.combineStreamData(Mockito.any())).thenReturn(null);
        ReflectionTestUtils.setField(service, "eventSubjectCombineService", eventSubjectCombineService);
        service.process(event);
    }
    
    /**
     * 主数据合并(返回合并结果MID为空)
     */
    @Test
    public void testProcess07_2() {
        String event = "{\"event\":{\"code\" :\"XXX\"},\"subject\":{\"openid\":\"12345\"}}";
        Mockito.when(eventSubjectCombineService.needCombine("XXX")).thenReturn(true);
        EventSubjectCombineResult result = new EventSubjectCombineResult();
        Mockito.when(eventSubjectCombineService.combineStreamData(Mockito.any())).thenReturn(result);
        ReflectionTestUtils.setField(service, "eventSubjectCombineService", eventSubjectCombineService);
        service.process(event);
    }
    
    /**
     * 主数据合并(DataParty不存在)
     */
    @Test
    public void testProcess07_3() {
        String event = "{\"event\":{\"code\" :\"XXX\"},\"subject\":{\"openid\":\"12345\"}}";
        Mockito.when(eventSubjectCombineService.needCombine("XXX")).thenReturn(true);
        EventSubjectCombineResult result = new EventSubjectCombineResult();
        result.setMid("12");
        Mockito.when(eventSubjectCombineService.combineStreamData(Mockito.any())).thenReturn(result);
        Mockito.when(dataPartyDao.getDataById(12)).thenReturn(null);
        ReflectionTestUtils.setField(service, "eventSubjectCombineService", eventSubjectCombineService);
        ReflectionTestUtils.setField(service, "dataPartyDao", dataPartyDao);
        service.process(event);
    }

    /**
     * 无包含事件触发的活动
     */
    @Test
    public void testProcess08() {
        String event =
                "{\"event\":{\"code\" :\"apply_submit_stream\"},\"subject\":{\"o_mc_contact_soc_mobile_phone\":\"12345\",\"o_mc_contact_soc_mail\":\"12345\"}}";
        Mockito.when(eventSubjectCombineService.needCombine("XXX")).thenReturn(true);

        Mockito.when(eventSubjectCombineService.combineStreamData(Mockito.any())).thenReturn(new EventSubjectCombineResult());
        ReflectionTestUtils.setField(service, "eventSubjectCombineService", eventSubjectCombineService);

        Mockito.when(campaignEventMapDao.getFirstMQNodeByEventCodeCnt(Mockito.any())).thenReturn(0);
        ReflectionTestUtils.setField(service, "campaignEventMapDao", campaignEventMapDao);

        ReflectionTestUtils.setField(service, "dataPartyDao", dataPartyDao);
        Mockito.when(dataPartyDao.getDataById(Mockito.any())).thenReturn(new DataParty());
        ReflectionTestUtils.setField(service, "dataPartyDao", dataPartyDao);
        
        service.process(event);
    }

    /**
     * 包含事件触发的活动(不翻页)
     */
    @Test
    public void testProcess09() {
        String event = "{\"event\":{\"code\" :\"XXX\"},\"subject\":{\"openid\":\"12345\"}}";
        Mockito.when(eventSubjectCombineService.needCombine("XXX")).thenReturn(true);
        EventSubjectCombineResult segment = new EventSubjectCombineResult();
        segment.setMid("3");
        Mockito.when(eventSubjectCombineService.combineStreamData(Mockito.any())).thenReturn(segment);
        ReflectionTestUtils.setField(service, "eventSubjectCombineService", eventSubjectCombineService);

        Mockito.when(campaignEventMapDao.getFirstMQNodeByEventCodeCnt(Mockito.any())).thenReturn(10);

        List<CampaignNode> nodes = new ArrayList<CampaignNode>();
        for (int i = 0; i < 10; i++) {
            CampaignNode node = new CampaignNode();
            node.setCampaignHeadId(Long.valueOf(100 + i));
            node.setItemId("XXXX" + i);
            nodes.add(node);
        }
        Mockito.when(campaignEventMapDao.getFirstMQNodeByEventCode(Mockito.any())).thenReturn(nodes);
        ReflectionTestUtils.setField(service, "campaignEventMapDao", campaignEventMapDao);

        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                return null;
            }
        }).when(jmsOperations).convertAndSend(Mockito.anyString(), Mockito.anyCollectionOf(Segment.class));
        ReflectionTestUtils.setField(service, "jmsOperations", jmsOperations);
        
        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                return null;
            }
        }).when(eventInvolvedDataPartyRepository).insert(Mockito.any(EventInvolvedDataParty.class));
        ReflectionTestUtils.setField(service, "eventInvolvedDataPartyRepository", eventInvolvedDataPartyRepository);
        Mockito.when(dataPartyDao.getDataById(Mockito.any())).thenReturn(new DataParty());
        ReflectionTestUtils.setField(service, "dataPartyDao", dataPartyDao);
        service.process(event);
    }

    /**
     * 包含事件触发的活动(翻页)
     */
    @Test
    public void testProcess10() {
        String event = "{\"event\":{\"code\" :\"XXX\"},\"subject\":{\"openid\":\"12345\"}}";
        Mockito.when(eventSubjectCombineService.needCombine("XXX")).thenReturn(true);
        EventSubjectCombineResult segment = new EventSubjectCombineResult();
        segment.setMid("3");
        Mockito.when(eventSubjectCombineService.combineStreamData(Mockito.any())).thenReturn(segment);
        ReflectionTestUtils.setField(service, "eventSubjectCombineService", eventSubjectCombineService);

        Mockito.when(campaignEventMapDao.getFirstMQNodeByEventCodeCnt(Mockito.any())).thenReturn(105);

        List<CampaignNode> nodes = new ArrayList<CampaignNode>();
        for (int i = 0; i < 105; i++) {
            CampaignNode node = new CampaignNode();
            node.setCampaignHeadId(Long.valueOf(100 + i));
            node.setItemId("XXXX" + i);
            nodes.add(node);
        }
        Mockito.when(campaignEventMapDao.getFirstMQNodeByEventCode(Mockito.any())).thenReturn(nodes);
        ReflectionTestUtils.setField(service, "campaignEventMapDao", campaignEventMapDao);

        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                return null;
            }
        }).when(jmsOperations).convertAndSend(Mockito.anyString(), Mockito.anyCollectionOf(Segment.class));
        ReflectionTestUtils.setField(service, "jmsOperations", jmsOperations);
        
        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                return null;
            }
        }).when(eventInvolvedDataPartyRepository).insert(Mockito.any(EventInvolvedDataParty.class));
        ReflectionTestUtils.setField(service, "eventInvolvedDataPartyRepository", eventInvolvedDataPartyRepository);
        Mockito.when(dataPartyDao.getDataById(Mockito.any())).thenReturn(new DataParty());
        ReflectionTestUtils.setField(service, "dataPartyDao", dataPartyDao);
        service.process(event);
    }

}
