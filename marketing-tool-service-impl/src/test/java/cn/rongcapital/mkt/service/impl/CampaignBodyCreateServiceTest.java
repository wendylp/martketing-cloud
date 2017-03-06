/*************************************************
 * @功能简述: Service实现测试类
 * @项目名称: marketing cloud
 * @see:
 * @author:
 * @version: 0.0.1
 * @date:
 * @复审人:
 *************************************************/

package cn.rongcapital.mkt.service.impl;


import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.util.ReflectionTestUtils;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.CampaignActionSaveAudienceDao;
import cn.rongcapital.mkt.dao.CampaignActionSendH5Dao;
import cn.rongcapital.mkt.dao.CampaignActionSendPrivtDao;
import cn.rongcapital.mkt.dao.CampaignActionSendPubDao;
import cn.rongcapital.mkt.dao.CampaignActionSendSmsDao;
import cn.rongcapital.mkt.dao.CampaignActionSetTagDao;
import cn.rongcapital.mkt.dao.CampaignActionWaitDao;
import cn.rongcapital.mkt.dao.CampaignAudienceFixDao;
import cn.rongcapital.mkt.dao.CampaignAudienceTargetDao;
import cn.rongcapital.mkt.dao.CampaignBodyDao;
import cn.rongcapital.mkt.dao.CampaignDecisionPropCompareDao;
import cn.rongcapital.mkt.dao.CampaignDecisionPrvtFriendsDao;
import cn.rongcapital.mkt.dao.CampaignDecisionPubFansDao;
import cn.rongcapital.mkt.dao.CampaignDecisionTagDao;
import cn.rongcapital.mkt.dao.CampaignDecisionWechatForwardDao;
import cn.rongcapital.mkt.dao.CampaignDecisionWechatReadDao;
import cn.rongcapital.mkt.dao.CampaignDecisionWechatSentDao;
import cn.rongcapital.mkt.dao.CampaignEventMapDao;
import cn.rongcapital.mkt.dao.CampaignHeadDao;
import cn.rongcapital.mkt.dao.CampaignNodeItemDao;
import cn.rongcapital.mkt.dao.CampaignSwitchDao;
import cn.rongcapital.mkt.dao.CampaignTriggerTimerDao;
import cn.rongcapital.mkt.dao.CustomTagDao;
import cn.rongcapital.mkt.dao.ImgTextAssetDao;
import cn.rongcapital.mkt.dao.SmsMaterialDao;
import cn.rongcapital.mkt.dao.TaskScheduleDao;
import cn.rongcapital.mkt.dao.WechatAssetDao;
import cn.rongcapital.mkt.dao.WechatAssetGroupDao;
import cn.rongcapital.mkt.dao.WechatGroupDao;
import cn.rongcapital.mkt.po.CampaignActionSendSms;
import cn.rongcapital.mkt.po.CampaignEventMap;
import cn.rongcapital.mkt.po.CampaignHead;
import cn.rongcapital.mkt.po.SmsMaterial;
import cn.rongcapital.mkt.service.CampaignBodyCreateService;
import cn.rongcapital.mkt.vo.in.CampaignBodyCreateIn;
import cn.rongcapital.mkt.vo.out.CampaignBodyCreateOut;

import com.alibaba.fastjson.JSON;

@RunWith(MockitoJUnitRunner.class)
public class CampaignBodyCreateServiceTest {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private CampaignBodyCreateService campaignBodyCreateService;


    @Mock
    private CampaignHeadDao campaignHeadDao;

    @Mock
    private CampaignBodyDao campaignBodyDao;

    @Mock
    private CampaignTriggerTimerDao campaignTriggerTimerDao;

    @Mock
    private CampaignActionSaveAudienceDao campaignActionSaveAudienceDao;

    @Mock
    private CampaignActionSendH5Dao campaignActionSendH5Dao;

    @Mock
    private CampaignActionSendPrivtDao campaignActionSendPrivtDao;

    @Mock
    private CampaignActionSendPubDao campaignActionSendPubDao;

    @Mock
    private CampaignActionSetTagDao campaignActionSetTagDao;

    @Mock
    private CampaignActionWaitDao campaignActionWaitDao;

    @Mock
    private CampaignAudienceTargetDao campaignAudienceTargetDao;

    @Mock
    private CampaignDecisionPropCompareDao campaignDecisionPropCompareDao;

    @Mock
    private CampaignDecisionPrvtFriendsDao campaignDecisionPrvtFriendsDao;

    @Mock
    private CampaignDecisionPubFansDao campaignDecisionPubFansDao;

    @Mock
    private CampaignDecisionTagDao campaignDecisionTagDao;

    @Mock
    private CampaignDecisionWechatForwardDao campaignDecisionWechatForwardDao;

    @Mock
    private CampaignDecisionWechatReadDao campaignDecisionWechatReadDao;

    @Mock
    private CampaignDecisionWechatSentDao campaignDecisionWechatSentDao;

    @Mock
    private CampaignSwitchDao campaignSwitchDao;

    @Mock
    private CampaignNodeItemDao campaignNodeItemDao;

    @Mock
    private TaskScheduleDao taskScheduleDao;

    @Mock
    private WechatAssetDao wechatAssetDao;

    @Mock
    private WechatAssetGroupDao wechatAssetGroupDao;

    @Mock
    private WechatGroupDao wechatGroupDao;

    @Mock
    private ImgTextAssetDao imgTextAssetDao;

    @Mock
    private CustomTagDao customTagDao;

    @Mock
    private CampaignEventMapDao campaignEventMapDao;

    @Mock
    private CampaignAudienceFixDao campaignAudienceFixDao;

    @Mock
    private CampaignAudienceTargetDao CampaignAudienceTargetDao;

    @Mock
    private CampaignActionSendSmsDao campaignActionSendSmsDao;

    @Mock
    private SmsMaterialDao smsMaterialDao;

    @Before
    public void setUp() throws Exception {
        logger.info("测试：CampaignBodyCreateService 准备---------------------");
        campaignBodyCreateService = new CampaignBodyCreateServiceImpl();
    }

    /**
     * 短信节点名称为空
     * 
     */
    @Test
    public void testCampaignBodyCreate01() {
        CampaignBodyCreateIn body =
                JSON.parseObject(
                        "{\"campaign_head_id\": \"368\",\"campaign_node_chain\": [{\"info\": {},\"item_type\": \"3\",\"node_type\": \"0\"},{\"code\": \"send-sms\",\"info\": {},\"item_type\": 6,\"node_type\": 3}]}",
                        CampaignBodyCreateIn.class);

        ReflectionTestUtils.setField(campaignBodyCreateService, "campaignHeadDao", campaignHeadDao);
        List<CampaignHead> headList = new ArrayList<CampaignHead>();
        CampaignHead head = new CampaignHead();
        head.setPublishStatus(ApiConstant.CAMPAIGN_PUBLISH_STATUS_PUBLISH);
        headList.add(head);
        Mockito.when(campaignHeadDao.selectList(Mockito.any())).thenReturn(headList);
        CampaignBodyCreateOut output = campaignBodyCreateService.campaignBodyCreate(body, null);
        Assert.assertEquals(ApiErrorCode.VALIDATE_ERROR.getCode(), output.getCode());
        Assert.assertEquals(ApiErrorCode.VALIDATE_ERROR.getMsg(), output.getMsg());
    }

    /**
     * 短信节点名称为空
     * 
     */
    @Test
    public void testCampaignBodyCreate02() {
        CampaignBodyCreateIn body =
                JSON.parseObject(
                        "{\"campaign_head_id\": \"368\",\"campaign_node_chain\": [{\"info\": {},\"item_type\": \"3\",\"node_type\": \"0\"},{\"code\": \"send-sms\",\"info\": {\"name\": \"\"},\"item_type\": 6,\"node_type\": 3}]}",
                        CampaignBodyCreateIn.class);

        ReflectionTestUtils.setField(campaignBodyCreateService, "campaignHeadDao", campaignHeadDao);
        List<CampaignHead> headList = new ArrayList<CampaignHead>();
        CampaignHead head = new CampaignHead();
        head.setPublishStatus(ApiConstant.CAMPAIGN_PUBLISH_STATUS_PUBLISH);
        headList.add(head);
        Mockito.when(campaignHeadDao.selectList(Mockito.any())).thenReturn(headList);


        CampaignBodyCreateOut output = campaignBodyCreateService.campaignBodyCreate(body, null);
        Assert.assertEquals(ApiErrorCode.VALIDATE_ERROR.getCode(), output.getCode());
        Assert.assertEquals(ApiErrorCode.VALIDATE_ERROR.getMsg(), output.getMsg());
    }

    /**
     * 活动包含事件触发节点
     * 
     */
    @Test
    public void testCampaignBodyCreate03() {
        CampaignBodyCreateIn body =
                JSON.parseObject(
                        "{\"campaign_head_id\": \"368\",\"campaign_node_chain\": [{\"info\": {\"event_id\": 123,\"event_code\": \"XXX\", \"event_name\": \"XXX名称\"},\"item_type\": \"3\",\"node_type\": \"0\"},{\"code\": \"send-sms\",\"info\": {\"name\": \"短信名称\"},\"item_type\": 6,\"sms_material_id\": 6,\"node_type\": 3}]}",
                        CampaignBodyCreateIn.class);

        ReflectionTestUtils.setField(campaignBodyCreateService, "campaignHeadDao", campaignHeadDao);
        List<CampaignHead> headList = new ArrayList<CampaignHead>();
        CampaignHead head = new CampaignHead();
        head.setPublishStatus(ApiConstant.CAMPAIGN_PUBLISH_STATUS_PUBLISH);
        headList.add(head);
        Mockito.when(campaignHeadDao.selectList(Mockito.any())).thenReturn(headList);

        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                return null;
            }
        }).when(taskScheduleDao).physicalDeleteById(368);
        Mockito.when(taskScheduleDao.insert(Mockito.any())).thenReturn(1);
        ReflectionTestUtils.setField(campaignBodyCreateService, "taskScheduleDao", taskScheduleDao);


        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                return null;
            }
        }).when(campaignSwitchDao).deleteByCampaignHeadId(368);
        ReflectionTestUtils.setField(campaignBodyCreateService, "campaignSwitchDao", campaignSwitchDao);


        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                return null;
            }
        }).when(campaignDecisionWechatSentDao).deleteByCampaignHeadId(368);
        ReflectionTestUtils.setField(campaignBodyCreateService, "campaignDecisionWechatSentDao",
                campaignDecisionWechatSentDao);

        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                return null;
            }
        }).when(campaignDecisionWechatReadDao).deleteByCampaignHeadId(368);
        ReflectionTestUtils.setField(campaignBodyCreateService, "campaignDecisionWechatReadDao",
                campaignDecisionWechatReadDao);


        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                return null;
            }
        }).when(campaignDecisionWechatForwardDao).deleteByCampaignHeadId(368);
        ReflectionTestUtils.setField(campaignBodyCreateService, "campaignDecisionWechatForwardDao",
                campaignDecisionWechatForwardDao);

        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                return null;
            }
        }).when(campaignDecisionTagDao).deleteByCampaignHeadId(368);
        ReflectionTestUtils.setField(campaignBodyCreateService, "campaignDecisionTagDao", campaignDecisionTagDao);

        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                return null;
            }
        }).when(campaignDecisionPubFansDao).deleteByCampaignHeadId(368);
        ReflectionTestUtils.setField(campaignBodyCreateService, "campaignDecisionPubFansDao",
                campaignDecisionPubFansDao);

        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                return null;
            }
        }).when(campaignDecisionPrvtFriendsDao).deleteByCampaignHeadId(368);
        ReflectionTestUtils.setField(campaignBodyCreateService, "campaignDecisionPrvtFriendsDao",
                campaignDecisionPrvtFriendsDao);

        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                return null;
            }
        }).when(campaignDecisionPropCompareDao).deleteByCampaignHeadId(368);
        ReflectionTestUtils.setField(campaignBodyCreateService, "campaignDecisionPropCompareDao",
                campaignDecisionPropCompareDao);


        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                return null;
            }
        }).when(campaignActionWaitDao).deleteByCampaignHeadId(368);
        ReflectionTestUtils.setField(campaignBodyCreateService, "campaignActionWaitDao", campaignActionWaitDao);


        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                return null;
            }
        }).when(campaignActionSetTagDao).deleteByCampaignHeadId(368);
        ReflectionTestUtils.setField(campaignBodyCreateService, "campaignActionSetTagDao", campaignActionSetTagDao);


        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                return null;
            }
        }).when(campaignActionSendPubDao).deleteByCampaignHeadId(368);
        ReflectionTestUtils.setField(campaignBodyCreateService, "campaignActionSendPubDao", campaignActionSendPubDao);


        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                return null;
            }
        }).when(campaignActionSendPrivtDao).deleteByCampaignHeadId(368);
        ReflectionTestUtils.setField(campaignBodyCreateService, "campaignActionSendPrivtDao",
                campaignActionSendPrivtDao);


        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                return null;
            }
        }).when(campaignActionSendH5Dao).deleteByCampaignHeadId(368);
        ReflectionTestUtils.setField(campaignBodyCreateService, "campaignActionSendH5Dao", campaignActionSendH5Dao);


        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                return null;
            }
        }).when(campaignActionSaveAudienceDao).deleteByCampaignHeadId(368);
        ReflectionTestUtils.setField(campaignBodyCreateService, "campaignActionSaveAudienceDao",
                campaignActionSaveAudienceDao);

        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                return null;
            }
        }).when(campaignTriggerTimerDao).deleteByCampaignHeadId(368);
        ReflectionTestUtils.setField(campaignBodyCreateService, "campaignTriggerTimerDao", campaignTriggerTimerDao);


        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                return null;
            }
        }).when(campaignEventMapDao).deleteByCampaignHeadId(368);


        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                CampaignEventMap data = (CampaignEventMap) args[0];
                Assert.assertEquals("XXX", data.getEventCode());
                Assert.assertEquals(123, data.getEventId().intValue());
                Assert.assertEquals("XXX名称", data.getEventName());
                return null;
            }
        }).when(campaignEventMapDao).insert(Mockito.any());

        ReflectionTestUtils.setField(campaignBodyCreateService, "campaignEventMapDao", campaignEventMapDao);


        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                return null;
            }
        }).when(campaignBodyDao).deleteByCampaignHeadId(368);
        Mockito.when(campaignBodyDao.insert(Mockito.any())).thenReturn(1);
        ReflectionTestUtils.setField(campaignBodyCreateService, "campaignBodyDao", campaignBodyDao);


        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                return null;
            }
        }).when(campaignAudienceFixDao).deleteByCampaignHeadId(368);
        ReflectionTestUtils.setField(campaignBodyCreateService, "campaignAudienceFixDao", campaignAudienceFixDao);

        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                return null;
            }
        }).when(CampaignAudienceTargetDao).deleteByCampaignHeadId(368);
        ReflectionTestUtils.setField(campaignBodyCreateService, "CampaignAudienceTargetDao", CampaignAudienceTargetDao);

        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                return null;
            }
        }).when(campaignActionSendSmsDao).deleteByCampaignHeadId(368);
        Mockito.when(campaignActionSendSmsDao.insert(Mockito.any())).thenReturn(1);
        Mockito.when(campaignActionSendSmsDao.selectList(Mockito.any())).thenReturn(
                new ArrayList<CampaignActionSendSms>());
        ReflectionTestUtils.setField(campaignBodyCreateService, "campaignActionSendSmsDao", campaignActionSendSmsDao);

        Mockito.when(smsMaterialDao.selectList(Mockito.any())).thenReturn(new ArrayList<SmsMaterial>());
        ReflectionTestUtils.setField(campaignBodyCreateService, "campaignActionSendSmsDao", campaignActionSendSmsDao);


        Mockito.doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                return null;
            }
        }).when(campaignNodeItemDao).updateById(Mockito.any());
        ReflectionTestUtils.setField(campaignBodyCreateService, "campaignNodeItemDao", campaignNodeItemDao);


        CampaignBodyCreateOut output = campaignBodyCreateService.campaignBodyCreate(body, null);

        Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), output.getCode());
        Assert.assertEquals(ApiErrorCode.SUCCESS.getMsg(), output.getMsg());
        Assert.assertEquals(0, output.getTotal());
        Assert.assertEquals(0, output.getTotalCount());
    }



    @After
    public void tearDown() throws Exception {
        logger.info("测试：CampaignBodyCreateService 结束---------------------");
    }

}
