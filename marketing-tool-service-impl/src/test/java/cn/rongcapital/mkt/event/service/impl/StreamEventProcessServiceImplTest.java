/*************************************************
 * @功能简述: Stream事件处理测试类
 * @项目名称: marketing cloud
 * @see:
 * @author: zhuxuelong
 * @version: 0.0.1
 * @date:
 * @复审人:
 *************************************************/
package cn.rongcapital.mkt.event.service.impl;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class StreamEventProcessServiceImplTest {

    private StreamEventProcessServiceImpl service;

    @Before
    public void setUp() throws Exception {
        service = new StreamEventProcessServiceImpl();
    }

    @After
    public void tearDown() throws Exception {}

    @Test
    public void testProcess() {
        fail("Not yet implemented");
    }

}
