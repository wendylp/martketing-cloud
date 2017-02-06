/*************************************************
 * @功能简述: SmstempletCountGetService实现测试类
 * @项目名称: marketing cloud
 * @see:
 * @author: zhuxuelong
 * @version: 0.0.1
 * @date: 2016-2-06
 * @复审人:
 *************************************************/
package cn.rongcapital.mkt.service.impl;

import static org.mockito.Matchers.any;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.enums.SmsTempletTypeEnum;
import cn.rongcapital.mkt.dao.SmsTempletDao;
import cn.rongcapital.mkt.service.SmstempletCountGetService;
import cn.rongcapital.mkt.vo.BaseOutput;

@RunWith(MockitoJUnitRunner.class)
public class SmstempletCountGetServiceTest {

    private SmstempletCountGetService smstempletCountGetService;

    @Mock
    private SmsTempletDao smsTempletDao;

    private List<Map<String, Object>> mapList;

    @Before
    public void setUp() throws Exception {

        mapList = new ArrayList<Map<String, Object>>();

        Map<String, Object> map1 = new HashMap<String, Object>();
        map1.put("type", SmsTempletTypeEnum.FIXED.getStatusCode());
        map1.put("count", 20);

        Map<String, Object> map2 = new HashMap<String, Object>();
        map2.put("type", SmsTempletTypeEnum.VARIABLE.getStatusCode());
        map2.put("count", 60);

        mapList.add(map1);
        mapList.add(map2);

        smstempletCountGetService = new SmstempletCountGetServiceImpl();
        Mockito.when(smsTempletDao.getTempletCountByType(any(), any())).thenReturn(mapList);

        // 把mock的dao set进入service
        ReflectionTestUtils.setField(smstempletCountGetService, "smsTempletDao", smsTempletDao);
    }


    @Test
    public void testGetSmsTempletCount() throws Exception {
        BaseOutput result = smstempletCountGetService.getSmsTempletCount("1", 1L);

        // 测试正常情况
        Assert.assertEquals(ApiErrorCode.SUCCESS.getCode(), result.getCode());
        Assert.assertEquals(ApiErrorCode.SUCCESS.getMsg(), result.getMsg());
        Assert.assertEquals(1, result.getData().size());
        Map<String, Integer> data = (Map<String, Integer>) result.getData().get(0);
        Assert.assertEquals(20, data.get("fixed").intValue());
        Assert.assertEquals(60, data.get("variable").intValue());

    }

    @After
    public void tearDown() throws Exception {}

}
