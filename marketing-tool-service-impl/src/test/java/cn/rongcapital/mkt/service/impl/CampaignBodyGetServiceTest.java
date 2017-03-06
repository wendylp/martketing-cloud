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

import org.apache.commons.collections.CollectionUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.CampaignActionSaveAudienceDao;
import cn.rongcapital.mkt.dao.CampaignActionSendH5Dao;
import cn.rongcapital.mkt.dao.CampaignActionSendPrivtDao;
import cn.rongcapital.mkt.dao.CampaignActionSendPubDao;
import cn.rongcapital.mkt.dao.CampaignActionSetTagDao;
import cn.rongcapital.mkt.dao.CampaignActionWaitDao;
import cn.rongcapital.mkt.dao.CampaignAudienceTargetDao;
import cn.rongcapital.mkt.dao.CampaignBodyDao;
import cn.rongcapital.mkt.dao.CampaignDecisionPropCompareDao;
import cn.rongcapital.mkt.dao.CampaignDecisionPrvtFriendsDao;
import cn.rongcapital.mkt.dao.CampaignDecisionPubFansDao;
import cn.rongcapital.mkt.dao.CampaignDecisionTagDao;
import cn.rongcapital.mkt.dao.CampaignDecisionWechatForwardDao;
import cn.rongcapital.mkt.dao.CampaignDecisionWechatReadDao;
import cn.rongcapital.mkt.dao.CampaignEventMapDao;
import cn.rongcapital.mkt.dao.CampaignNodeItemDao;
import cn.rongcapital.mkt.dao.CampaignSwitchDao;
import cn.rongcapital.mkt.dao.CampaignTriggerTimerDao;
import cn.rongcapital.mkt.dao.ImgTextAssetDao;
import cn.rongcapital.mkt.dao.WechatAssetDao;
import cn.rongcapital.mkt.dao.WechatAssetGroupDao;
import cn.rongcapital.mkt.po.CampaignBody;
import cn.rongcapital.mkt.po.CampaignEventMap;
import cn.rongcapital.mkt.po.CampaignNodeItem;
import cn.rongcapital.mkt.po.CampaignSwitch;
import cn.rongcapital.mkt.po.mongodb.NodeAudience;
import cn.rongcapital.mkt.service.CampaignBodyGetService;
import cn.rongcapital.mkt.vo.out.CampaignBodyGetOut;
import cn.rongcapital.mkt.vo.out.CampaignNodeChainOut;
import cn.rongcapital.mkt.vo.out.CampaignTriggerEventOut;

@RunWith(MockitoJUnitRunner.class)
public class CampaignBodyGetServiceTest {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private CampaignBodyGetService campaignBodyGetService;


    @Mock
    private CampaignBodyDao campaignBodyDao;

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
    private CampaignSwitchDao campaignSwitchDao;

    @Mock
    private CampaignTriggerTimerDao campaignTriggerTimerDao;

    @Mock
    private CampaignNodeItemDao campaignNodeItemDao;

    @Mock
    private WechatAssetDao wechatAssetDao;

    @Mock
    private WechatAssetGroupDao wechatAssetGroupDao;

    @Mock
    private ImgTextAssetDao imgTextAssetDao;

    @Mock
    private CampaignEventMapDao campaignEventMapDao;

    @Mock
    private MongoTemplate mongoTemplate;



    @Before
    public void setUp() throws Exception {
        logger.info("测试：CampaignBodyGetService 准备---------------------");
        campaignBodyGetService = new CampaignBodyGetServiceImpl();
    }

    /**
     * 活动包含事件触发节点
     * 
     */
    @Test
    public void testCampaignBodyGet01() {

        List<CampaignBody> bodyList = new ArrayList<CampaignBody>();
        CampaignBody body = new CampaignBody();
        body.setNodeType(ApiConstant.CAMPAIGN_NODE_TRIGGER);
        body.setItemType(ApiConstant.CAMPAIGN_ITEM_EVENT_MANUAL);
        bodyList.add(body);
        Mockito.when(campaignBodyDao.selectList(Mockito.any())).thenReturn(bodyList);
        ReflectionTestUtils.setField(campaignBodyGetService, "campaignBodyDao", campaignBodyDao);

        List<CampaignNodeItem> nodeItems = new ArrayList<CampaignNodeItem>();
        CampaignNodeItem nodeItem = new CampaignNodeItem();
        nodeItem.setPtype(ApiConstant.CAMPAIGN_PARENT_NODE_PTYPE);
        nodeItems.add(nodeItem);
        Mockito.when(campaignNodeItemDao.selectList(Mockito.any())).thenReturn(nodeItems);
        ReflectionTestUtils.setField(campaignBodyGetService, "campaignNodeItemDao", campaignNodeItemDao);

        List<CampaignEventMap> mapList = new ArrayList<CampaignEventMap>();
        CampaignEventMap map = new CampaignEventMap();
        map.setEventCode("XXX");
        map.setEventName("XX名称");
        map.setEventId(123L);
        mapList.add(map);
        Mockito.when(campaignEventMapDao.selectList(Mockito.any())).thenReturn(mapList);
        ReflectionTestUtils.setField(campaignBodyGetService, "campaignEventMapDao", campaignEventMapDao);

        Mockito.when(mongoTemplate.count(Mockito.any(), Mockito.eq(NodeAudience.class))).thenReturn(1L);
        ReflectionTestUtils.setField(campaignBodyGetService, "mongoTemplate", mongoTemplate);

        List<CampaignSwitch> switchList = new ArrayList<CampaignSwitch>();
        CampaignSwitch switchItem = new CampaignSwitch();
        switchList.add(switchItem);
        Mockito.when(campaignSwitchDao.selectList(Mockito.any())).thenReturn(switchList);
        ReflectionTestUtils.setField(campaignBodyGetService, "campaignSwitchDao", campaignSwitchDao);

        CampaignBodyGetOut output = campaignBodyGetService.campaignBodyGet("123", "123", 123);

        Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), output.getCode());
        Assert.assertEquals(ApiErrorCode.SUCCESS.getMsg(), output.getMsg());
        Assert.assertEquals(1, output.getTotal());
        Assert.assertEquals(1, output.getTotalCount());
        Assert.assertTrue(CollectionUtils.isNotEmpty(output.getData()));

        CampaignNodeChainOut campaignNodeChainOut = (CampaignNodeChainOut) output.getData().get(0);
        CampaignTriggerEventOut eventOut = (CampaignTriggerEventOut) campaignNodeChainOut.getInfo();
        Assert.assertEquals(123, eventOut.getEventId().intValue());
        Assert.assertEquals("XXX", eventOut.getEventCode());
        Assert.assertEquals("XX名称", eventOut.getEventName());
    }



    @After
    public void tearDown() throws Exception {
        logger.info("测试：CampaignBodyGetService 结束---------------------");
    }

}
