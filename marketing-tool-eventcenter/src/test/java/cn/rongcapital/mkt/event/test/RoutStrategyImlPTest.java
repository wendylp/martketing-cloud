/*************************************************
 * @功能及特点的描述简述: message（例如该类是用来做什么的）
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：MC(营销云系统)
 * @author:liuhaizhan
 * @version: 版本v1.6
 * @date(创建、开发日期)：2017年3月3日 
 * @date(最后修改日期)：2017年3月3日 
 * @复审人：
 *************************************************/

package cn.rongcapital.mkt.event.test;

import java.util.concurrent.ExecutionException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import cn.rongcapital.mkt.common.util.CacheManage;
import cn.rongcapital.mkt.dao.event.EventDao;
import cn.rongcapital.mkt.event.po.Event;
import cn.rongcapital.mkt.event.service.RoutStrategy;
import cn.rongcapital.mkt.event.service.RoutStrategyImlp;
import junit.framework.Assert;

@RunWith(MockitoJUnitRunner.class)
public class RoutStrategyImlPTest {

    
    @Mock
    private EventDao eventDao;
    
    private RoutStrategy  routStrategy;
    @Before
    public void setUp(){
        
        routStrategy =new RoutStrategyImlp();
        ReflectionTestUtils.setField(routStrategy, "eventDao", eventDao);  
    }
    
    @Test
    public void test() throws ExecutionException
    {
        String message="{\"event\":{\"code\": \"wechat_qrcode_subscribe\","+
            "\"attributes\": {\"browser\": \"谷歌浏览44器\",\"value\": 1200 }}}";
        Event event =new Event();
        event.setObjectId(1L);
        Mockito.when(eventDao.selectByCode(Mockito.anyString())).thenReturn(event);
       boolean router= routStrategy.iFRouter(message);
       
       Assert.assertTrue(router);  //路由通过
       
        //清空缓存
       CacheManage.cache.invalidate("wechat_qrcode_subscribe");
       //refresh();
       Mockito.when(eventDao.selectByCode(Mockito.anyString())).thenReturn(null);
       
       router= routStrategy.iFRouter(message);
       
       Assert.assertFalse(router); //路由失败
       
        
    }
}
