/*************************************************
 * @功能简述: DAO接口测试类
 * @项目名称: marketing cloud
 * @see:
 * @author: zhuxuelong
 * @version: 0.0.1
 * @date:
 * @复审人:
 *************************************************/
package cn.rongcapital.mkt.dao;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.rongcapital.mkt.dao.testbase.AbstractUnitTest;
import cn.rongcapital.mkt.po.CampaignBody;
import cn.rongcapital.mkt.po.CampaignEventMap;
import cn.rongcapital.mkt.po.CampaignHead;
import cn.rongcapital.mkt.po.CampaignSwitch;
import cn.rongcapital.mkt.vo.CampaignNode;

@RunWith(SpringJUnit4ClassRunner.class)
public class CampaignEventMapDaoTest extends AbstractUnitTest {

    @Autowired
    private CampaignEventMapDao campaignEventMapDao;

    @Autowired
    private CampaignHeadDao campaignHeadDao;

    @Autowired
    private CampaignBodyDao campaignBodyDao;

    @Autowired
    private CampaignSwitchDao campaignSwitchDao;

    @Before
    public void setUp() throws Exception {}

    @After
    public void tearDown() throws Exception {}

    /**
     * Normal
     * 
     * @author zhuxuelong
     */
    @Test
    public void testDeleteByCampaignHeadId01() {
        CampaignEventMap map = new CampaignEventMap();
        map.setCampaignHeadId(10);
        map.setEventId(12L);
        map.setEventCode("code");
        map.setEventName("name");
        map.setCreateTime(new Date());
        campaignEventMapDao.insert(map);
        List<CampaignEventMap> list = campaignEventMapDao.selectListByIdList(Arrays.asList(map.getId()));
        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());

        campaignEventMapDao.deleteByCampaignHeadId(10);

        list = campaignEventMapDao.selectListByIdList(Arrays.asList(map.getId()));
        Assert.assertNotNull(list);
        Assert.assertEquals(0, list.size());
    }

    /**
     * Normal
     * 
     * @author zhuxuelong
     */
    @Test
    public void testGetFirstMQNodeByEventCode01() {
        CampaignHead head = new CampaignHead();
        head.setPublishStatus((byte) 2);
        head.setStatus((byte) 0);
        head.setCreateTime(new Date());
        campaignHeadDao.insert(head);

        CampaignBody body1 = new CampaignBody();
        String item1 = UUID.randomUUID().toString();
        body1.setHeadId(head.getId());
        body1.setNodeType((byte) 0);
        body1.setItemType((byte) 3);
        body1.setItemId(item1);
        body1.setStatus((byte) 0);
        body1.setCreateTime(new Date());
        campaignBodyDao.insert(body1);

        CampaignBody body2 = new CampaignBody();
        String item2 = UUID.randomUUID().toString();
        body2.setHeadId(head.getId());
        body1.setNodeType((byte) 3);
        body1.setItemType((byte) 6);
        body2.setItemId(item2);
        body2.setStatus((byte) 0);
        body2.setCreateTime(new Date());
        campaignBodyDao.insert(body2);

        CampaignSwitch switchPo = new CampaignSwitch();
        switchPo.setCampaignHeadId(head.getId());
        switchPo.setItemId(item1);
        switchPo.setNextItemId(item2);
        switchPo.setStatus((byte) 0);
        switchPo.setCreateTime(new Date());
        campaignSwitchDao.insert(switchPo);

        CampaignEventMap campaignEventMap = new CampaignEventMap();
        campaignEventMap.setCampaignHeadId(head.getId());
        campaignEventMap.setEventId(233L);
        campaignEventMap.setEventCode("XX");
        campaignEventMap.setEventName("XXX");
        campaignEventMap.setStatus((byte) 0);
        campaignEventMap.setCreateTime(new Date());
        campaignEventMapDao.insert(campaignEventMap);

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("eventCode", "XX");
        List<CampaignNode> nodes = campaignEventMapDao.getFirstMQNodeByEventCode(param);

        Assert.assertTrue(CollectionUtils.isNotEmpty(nodes));
        nodes =
                nodes.stream().filter(x -> x.getCampaignHeadId().intValue() == head.getId().intValue())
                        .collect(Collectors.toList());
        Assert.assertTrue(CollectionUtils.isNotEmpty(nodes));
    }

    /**
     * Normal
     * 
     * @author zhuxuelong
     */
    @Test
    public void testGetFirstMQNodeByEventCodeCnt01() {
        CampaignHead head = new CampaignHead();
        head.setPublishStatus((byte) 2);
        head.setStatus((byte) 0);
        head.setCreateTime(new Date());
        campaignHeadDao.insert(head);

        CampaignBody body1 = new CampaignBody();
        String item1 = UUID.randomUUID().toString();
        body1.setHeadId(head.getId());
        body1.setNodeType((byte) 0);
        body1.setItemType((byte) 3);
        body1.setItemId(item1);
        body1.setStatus((byte) 0);
        body1.setCreateTime(new Date());
        campaignBodyDao.insert(body1);

        CampaignBody body2 = new CampaignBody();
        String item2 = UUID.randomUUID().toString();
        body2.setHeadId(head.getId());
        body1.setNodeType((byte) 3);
        body1.setItemType((byte) 6);
        body2.setItemId(item2);
        body2.setStatus((byte) 0);
        body2.setCreateTime(new Date());
        campaignBodyDao.insert(body2);

        CampaignSwitch switchPo = new CampaignSwitch();
        switchPo.setCampaignHeadId(head.getId());
        switchPo.setItemId(item1);
        switchPo.setNextItemId(item2);
        switchPo.setStatus((byte) 0);
        switchPo.setCreateTime(new Date());
        campaignSwitchDao.insert(switchPo);

        CampaignEventMap campaignEventMap = new CampaignEventMap();
        campaignEventMap.setCampaignHeadId(head.getId());
        campaignEventMap.setEventId(233L);
        campaignEventMap.setEventCode("XX");
        campaignEventMap.setEventName("XXX");
        campaignEventMap.setStatus((byte) 0);
        campaignEventMap.setCreateTime(new Date());
        campaignEventMapDao.insert(campaignEventMap);

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("eventCode", "XX");
        int count = campaignEventMapDao.getFirstMQNodeByEventCodeCnt(param);
        Assert.assertTrue(count >= 1);
    }

}
