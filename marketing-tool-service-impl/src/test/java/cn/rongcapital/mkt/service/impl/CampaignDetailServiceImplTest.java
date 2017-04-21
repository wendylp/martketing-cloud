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

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import cn.rongcapital.mkt.dao.CampaignBodyDao;
import cn.rongcapital.mkt.dao.CampaignHeadDao;
import cn.rongcapital.mkt.dao.CampaignNodeItemDao;
import cn.rongcapital.mkt.dao.CampaignSwitchDao;
import cn.rongcapital.mkt.dao.DataPartyDao;
import cn.rongcapital.mkt.po.CampaignBody;
import cn.rongcapital.mkt.po.CampaignHead;
import cn.rongcapital.mkt.po.CampaignNodeItem;
import cn.rongcapital.mkt.po.CampaignSwitch;
import cn.rongcapital.mkt.po.DataParty;
import cn.rongcapital.mkt.po.mongodb.CampaignDetail;
import cn.rongcapital.mkt.po.mongodb.CampaignMember;
import cn.rongcapital.mkt.po.mongodb.NodeAudience;
import cn.rongcapital.mkt.service.testbase.AbstractUnitTest;
import cn.rongcapital.mkt.vo.out.CampaignDetailOut;
import cn.rongcapital.mkt.vo.out.CampaignItemAudiencesInfo;
import cn.rongcapital.mkt.vo.out.CampaignItemInfo;

import com.alibaba.fastjson.JSONArray;

@RunWith(MockitoJUnitRunner.class)
public class CampaignDetailServiceImplTest extends AbstractUnitTest {

	private Integer campaignHeadId = 123;
	private String itemId = "456";
	private Integer campaignId = 123;
	private Integer mid = 1002;
	private final static Byte TRIGGER_TYPE = 0; // 触发类型
	private final static Byte ACTION_TYPE = 3; // 行动类型
	private final static Byte ITEM_PTYPE = 3; // 节点父类型 @see mysql.campaign_node_item.ptype

	@Mock
	private DataPartyDao dataPartyDao;
	@Mock
	private MongoTemplate mongoTemplate;
	@Mock
	private CampaignSwitchDao switchDao;
	@Mock
	private CampaignHeadDao campaignHeadDao;
	@Mock
	private CampaignBodyDao campaignBodyDao;
	@Mock
	private CampaignNodeItemDao campaignNodeItemDao;
	@InjectMocks
	private CampaignDetailServiceImpl serviceImpl;

	@Before
	public void setUp() {
		DataParty dp1 = new DataParty();
		dp1.setId(123);
		dp1.setMobile("13888888881");
		dp1.setWxCode("webchartcode1");
		DataParty dp2 = new DataParty();
		dp2.setId(456);
		dp2.setMobile("13888888882");
		dp2.setWxCode("webchartcode2");
		DataParty dp3 = new DataParty();
		dp3.setId(789);
		dp3.setMobile("13888888883");
		dp3.setWxCode("webchartcode3");
		Mockito.when(dataPartyDao.selectDataPartyList(Arrays.asList(123, 456, 789))).thenReturn(Arrays.asList(dp1, dp2, dp3));

		NodeAudience au1 = new NodeAudience();
		au1.setDataId(123);
		NodeAudience au2 = new NodeAudience();
		au2.setDataId(456);
		NodeAudience au3 = new NodeAudience();
		au3.setDataId(789);

		Mockito.when(mongoTemplate.find(Mockito.any(Query.class), Mockito.anyObject())).thenReturn(Arrays.asList(au1, au2, au3));
		Mockito.when(mongoTemplate.count(Mockito.any(Query.class), Mockito.anyObject())).thenReturn(3L);

		CampaignDetail detail = new CampaignDetail();
		detail.setCampaignId(campaignId);
		detail.setIsHaveSubTable(0);
		detail.setItemId(itemId);
		detail.setCampaignEndTime(new Date());
		detail.setCampaignStartTime(new Date());
		detail.setIsHaveSubTable(0);
		detail.setItemType(0);
		detail.setCampaignName("活动");
		detail.setCampaignMemberNum(1000);
		Mockito.when(mongoTemplate.findOne(Mockito.any(Query.class), Mockito.anyObject())).thenReturn(detail);

		CampaignSwitch query = new CampaignSwitch();
		query.setCampaignHeadId(campaignHeadId);
		query.setItemId(itemId);
		CampaignSwitch switch1 = new CampaignSwitch();
		switch1.setNextItemId("789");
		Mockito.when(switchDao.selectList(Mockito.anyObject())).thenReturn(Arrays.asList(switch1));

		CampaignHead ch1 = new CampaignHead();
		ch1.setId(123);
		ch1.setName("123");
		ch1.setStartTime(new Date());
		ch1.setEndTime(new Date());
		CampaignHead ch2 = new CampaignHead();
		ch2.setId(456);
		ch2.setName("456");
		ch2.setStartTime(new Date());
		ch2.setEndTime(new Date());
		CampaignHead ch3 = new CampaignHead();
		ch3.setId(789);
		ch3.setName("789");
		ch3.setStartTime(new Date());
		ch3.setEndTime(new Date());
		Mockito.when(campaignHeadDao.selectCampaignProgressStatusListByPublishStatus(Mockito.anyObject()))//
				.thenReturn(Arrays.asList(ch1, ch2, ch3));
		Mockito.when(campaignHeadDao.selectList(Mockito.any(CampaignHead.class))).thenReturn(Arrays.asList(ch1, ch2, ch3));
		

		CampaignBody cb1 = new CampaignBody();
		cb1.setItemType(TRIGGER_TYPE);
		cb1.setItemId("123");
		cb1.setItemType((byte) 5);
		CampaignBody cb2 = new CampaignBody();
		cb2.setItemType(TRIGGER_TYPE);
		cb2.setItemId("456");
		cb2.setItemType((byte) 5);
		CampaignBody cb3 = new CampaignBody();
		cb3.setItemType(TRIGGER_TYPE);
		cb3.setItemId("789");
		cb3.setItemType((byte) 5);
		Mockito.when(campaignBodyDao.selectList(Mockito.anyObject())).thenReturn(Arrays.asList(cb1, cb2, cb3));

		CampaignNodeItem cni = new CampaignNodeItem();
		cni.setName("微信订阅");
		Mockito.when(campaignNodeItemDao.selectList(Mockito.anyObject())).thenReturn(Arrays.asList(cni));
	}

	@Test
	public void campaignDetailTest() {
		CampaignDetailOut campaignDetailOut = this.serviceImpl.campaignDetail("123");
		System.out.println(JSONArray.toJSONString(campaignDetailOut));
	}

	@Test
	public void selectCampaignAudiencesTotalCountTest() {
		Integer totalCount = this.serviceImpl.selectCampaignAudiencesTotalCount(campaignHeadId);
		System.out.println("totalCount:" + totalCount);
	}

	@Test
	public void selectCampaignItemInfoListTest() {
		List<CampaignItemInfo> campaignItemInfos = this.serviceImpl.selectCampaignItemInfoList(campaignHeadId);
		for (CampaignItemInfo cur : campaignItemInfos) {
			System.out.println("type:" + cur.getType());
			for (CampaignItemAudiencesInfo cur1 : cur.getAudiences()) {
				System.out.println("---mid:" + cur1.getMid() + ",mobile:" + cur1.getMobile() + ",openid:" + cur1.getOpenid());
			}
		}
	}

	@Test
	public void selectCampaignItemAudiencesInfoListTest() {
		List<CampaignItemAudiencesInfo> aus = this.serviceImpl.selectCampaignItemAudiencesInfoList(campaignHeadId, itemId);
		for (CampaignItemAudiencesInfo cur : aus) {
			System.out.println("mid:" + cur.getMid() + ",mobile:" + cur.getMobile() + ",openid:" + cur.getOpenid());
		}
	}

	@Test
	public void selectMidListTest() {
		List<Integer> ids = serviceImpl.selectMidList(123, "456");
		System.out.println(ids);
	}

	@Test
	public void selectCampaignSwitchTest() {
		CampaignSwitch campaignSwitch = this.serviceImpl.selectCampaignSwitch(campaignHeadId, itemId);
		System.out.println("next item id: " + campaignSwitch.getNextItemId());
	}

	@Test
	public void selectCampaignHeadTest() {
		List<CampaignHead> campaignHeads = this.serviceImpl.selectCampaignHead("123");
		for (CampaignHead cur : campaignHeads) {
			System.out.println("id:" + cur.getId() + ", name:" + cur.getName());
		}
	}

	@Test
	public void selectPassItemNodTotalCountTest() {
		Integer totalCount = this.serviceImpl.selectPassItemNodTotalCount(123, "456");
		System.out.println(totalCount.intValue());
	}

	@Test
	public void selectTriggerNameTest() {
		String name = this.serviceImpl.selectTriggerName(ITEM_PTYPE, ACTION_TYPE);
		System.out.println("trigger_name:" + name);
	}

	@Test
	public void selectCampaignItemListTest() {
		List<CampaignBody> campaignBodies = this.serviceImpl.selectCampaignItemList(campaignHeadId, TRIGGER_TYPE);
		for (CampaignBody cur : campaignBodies) {
			System.out.println("itemType：" + cur.getItemType() + ", itemId:" + cur.getItemId());
		}

	}

	@Test
	public void selectDataPartyTest() {
		List<DataParty> dataParties = this.serviceImpl.selectDataParty(Arrays.asList(123, 456, 789));
		for (DataParty cur : dataParties) {
			System.out.println(cur.getId() + ", " + cur.getMobile() + ", " + cur.getWxCode());
		}
	}

	@Test
	public void saveCampaignDetailTest() {
		serviceImpl.saveCampaignDetail(campaignId);
	}

	@Test
	public void saveCampaignMemberTest() {
		CampaignMember member = new CampaignMember();
		member.setCouponId(12345);
		member.setDealSum(1002.1);
		member.setIsBuy(0);
		member.setIsRespond(0);
		member.setIsTouch(0);
		member.setMemberId(0);
		member.setMid(1002);
		member.setOpenId("123");
		member.setOrderId(1003L);
		member.setPhone("15647236987");
		member.setUpdateTime(new Date());
		member.setWxId("gh_what");
		serviceImpl.saveCampaignMember(campaignId, itemId, mid);
	}

	@Test
	public void updateCampaignDetailSubTableStatusTest() {
		serviceImpl.updateCampaignDetailSubTableStatus(campaignId, itemId, 1);
	}

	@Test
	public void selectCampaignDetailTest() {
		CampaignDetail detail = serviceImpl.selectCampaignDetail(campaignId, itemId);
		System.out.println(detail);
	}

	@Test
	public void updateCampaignMemberCouponIdTest() {
		serviceImpl.updateCampaignMemberCouponId(campaignId, itemId, mid, 6001, 6002);
		serviceImpl.updateCampaignMemberCouponId(campaignId, itemId, null, 6001, 6002);
		serviceImpl.updateCampaignMemberCouponId(campaignId, "", mid, 6001, 6002);
		serviceImpl.updateCampaignMemberCouponId(null, "", mid, 6001, 6002);
	}

	@Test
	public void testCreateKey() {
		Assert.assertEquals("创建key测试失败", serviceImpl.createKey(campaignId, itemId), "key:" + campaignId + ":" + itemId);
		Assert.assertNull("创建key测试失败", serviceImpl.createKey(campaignId, ""));
		Assert.assertNull("创建key测试失败", serviceImpl.createKey(campaignId, null));
		Assert.assertNull("创建key测试失败", serviceImpl.createKey(null, null));
	}

	@Test
	public void testMap() {
		CampaignDetail detail = new CampaignDetail();
		Map<String, CampaignDetail> cache = new HashMap<String, CampaignDetail>();
		cache.put("ok", detail);
		System.out.println(cache);
		detail.setIsHaveSubTable(1);
		System.out.println(cache);
	}

}
