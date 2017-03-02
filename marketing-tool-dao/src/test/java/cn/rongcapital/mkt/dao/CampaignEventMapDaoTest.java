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
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.rongcapital.mkt.dao.testbase.AbstractUnitTest;
import cn.rongcapital.mkt.po.CampaignEventMap;

@RunWith(SpringJUnit4ClassRunner.class)
public class CampaignEventMapDaoTest extends AbstractUnitTest {

    @Autowired
    private CampaignEventMapDao campaignEventMapDao;

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
    public void testGetFirstMQNodeByEventCode01() {}

    /**
     * Normal
     * 
     * @author zhuxuelong
     */
    @Test
    public void testGetFirstMQNodeByEventCodeCnt01() {}

}
