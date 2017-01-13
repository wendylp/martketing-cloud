/*************************************************
 * @功能及特点的描述简述: message（例如该类是用来做什么的）
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：MC(营销云系统)
 * @author:liuhaizhan
 * @version: 版本v1.6
 * @date(创建、开发日期)：2017年1月9日 
 * @date(最后修改日期)：2017年1月9日 
 * @复审人：
 *************************************************/

package cn.rongcapital.mkt.dao.event;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.rongcapital.mkt.dao.testbase.AbstractUnitTest;
import cn.rongcapital.mkt.event.po.Event;
@RunWith(SpringJUnit4ClassRunner.class)
public class EventSelectByCodeTest extends AbstractUnitTest{

    
    private static final Logger logger = LoggerFactory.getLogger(EventSelectByCodeTest.class);
    
    @Autowired
    private EventDao eventDao;
    
    
    
    // 先插入数据然后再测试
    @Before
    public void insertData()
    {
        
    }
    @Test
    public void test()
    {
        String code="mc-qrcode-subscribe";
        Event event =eventDao.selectByCode(code); 
        logger.info("eventId :{}",event.getId());
    }
}
