package cn.rongcapital.mkt.dao;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.dao.testbase.AbstractUnitTest;
import cn.rongcapital.mkt.po.SmsTemplet;

@RunWith(SpringJUnit4ClassRunner.class)
public class SmsTempletDaoTest extends AbstractUnitTest {

    
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SmsTempletDao smsTempletDao;
    
    int total = 3;
    SmsTemplet[] smsTemplet = new SmsTemplet[total];
    
    @Before
    public void setUp() throws Exception {
        
        Byte[] channelType = {0,1,2};
        Byte[] type = {0, 1, 2};
        Byte[] auditStatus = {0, 0, 0};
        String[] content = {"模板内容测试12", "模板内容测试13", "模板内容测试4"} ;
        
        for(int i =0 ; i < total; i++) {
            smsTemplet[i] = new SmsTemplet(channelType[i], type[i],auditStatus[i], content[i]);
            smsTempletDao.insert(smsTemplet[i]);
        }
        
    }
    
    @Test
    public void testSelectList() {
        
        List<SmsTemplet> smsTempletLists;
        SmsTemplet smsTempletTest = new SmsTemplet();
        smsTempletTest.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
        
        // 测试查询所有
        smsTempletLists = smsTempletDao.selectList(smsTempletTest);
        Assert.assertEquals(total, smsTempletLists.size());
        
        // 测试模糊查询1
        smsTempletTest.setContent("模板内容测试");
        smsTempletLists = smsTempletDao.selectList(smsTempletTest);
        Assert.assertEquals(3, smsTempletLists.size());
        Assert.assertEquals(smsTemplet[0].getContent(), smsTempletLists.get(0).getContent());
        
        // 测试模糊查询2
        smsTempletTest.setContent("4");
        smsTempletTest.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
        smsTempletLists = smsTempletDao.selectList(smsTempletTest);
        Assert.assertEquals(1, smsTempletLists.size());
        Assert.assertEquals(smsTemplet[2].getContent(), smsTempletLists.get(0).getContent());
        
        
        
    }
    
    @After
    public void tearDown() throws Exception {
        // 逻辑删除数据
        for(int i =0 ; i < total; i++) {
            smsTemplet[i].setStatus(ApiConstant.TABLE_DATA_STATUS_INVALID);
            smsTempletDao.updateById(smsTemplet[i]);
        }
        
    }
}
