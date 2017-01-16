/*************************************************
 * @功能及特点的描述简述: 事件注册测试类
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：营销云系统
 * @author: 谢小良
 * @version: 版本v1.6
 * @date(创建、开发日期)：2017-01-11 
 * @date(最后修改日期)：2017-01-11 
 * @复审人：
 *************************************************/
package cn.rongcapital.mkt.event.service.impl;

import java.util.ArrayList;
import java.util.List;

import cn.rongcapital.mkt.dao.event.EventObjectDao;
import cn.rongcapital.mkt.dao.event.EventSourceDao;
import cn.rongcapital.mkt.event.po.EventObject;
import cn.rongcapital.mkt.event.po.EventSource;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.util.ReflectionTestUtils;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.event.EventDao;
import cn.rongcapital.mkt.event.po.Event;
import cn.rongcapital.mkt.event.service.EventRegisterService;
import cn.rongcapital.mkt.event.vo.in.EventRegisterAttributeIn;
import cn.rongcapital.mkt.event.vo.in.EventRegisterIn;
import cn.rongcapital.mkt.vo.BaseOutput;

@RunWith(MockitoJUnitRunner.class)
public class EventRegisterServiceImplTest {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Mock
    private EventDao eventDao;

    @Mock
    private EventSourceDao eventSourceDao;

    @Mock
    private EventObjectDao eventObjectDao;

    private EventRegisterService eventRegisterService = new EventRegisterServiceImpl();

    private EventRegisterIn registerIn;

    private Event event;

    private ArrayList<EventSource> eventSources;

    private List<EventObject> eventObjects;

    /**
     * @功能描述: message
     * @throws java.lang.Exception void
     * @author xie.xiaoliang
     * @since 2017-01-11
     */
    @Before
    public void setUp() throws Exception {
        logger.info("测试：MaterialCouponCodeCheckServiceTest 准备---------------------");
        // 把mock的dao set进入service
        ReflectionTestUtils.setField(eventRegisterService, "eventDao", eventDao);
        ReflectionTestUtils.setField(eventRegisterService, "eventSourceDao", eventSourceDao);
        ReflectionTestUtils.setField(eventRegisterService, "eventObjectDao", eventObjectDao);

        // 初始化数据
        registerIn = new EventRegisterIn();
        registerIn.setName("使用优惠券");
        registerIn.setCode("USER_COUPON");
        registerIn.setObjectCode("COUPON_OBJECT");
        registerIn.setSourceCode("MC_METAREL");
        List<EventRegisterAttributeIn> items = new ArrayList<>();
        EventRegisterAttributeIn ipAddressAttr = new EventRegisterAttributeIn();
        ipAddressAttr.setName("ipAddress");
        ipAddressAttr.setLable("IP地址");
        items.add(ipAddressAttr);
        EventRegisterAttributeIn browserAttr = new EventRegisterAttributeIn();
        browserAttr.setName("browser");
        browserAttr.setLable("浏览器");
        items.add(browserAttr);
        registerIn.setAttributes(items);

        event = new Event();

        eventSources = new ArrayList<>();
        EventSource eventSource = new EventSource();
        eventSource.setId(1l);
        eventSources.add(eventSource);

        eventObjects = new ArrayList<>();
        EventObject eventObject = new EventObject();
        eventObject.setId(1l);
        eventObjects.add(eventObject);
    }

    /**
     * @功能描述: message
     * @throws java.lang.Exception void
     * @author xie.xiaoliang
     * @since 2017-01-11
     */
    @After
    public void tearDown() throws Exception {
        logger.info("测试：MaterialCouponCodeCheckServiceTest 结束---------------------");
    }

    /**
     * Test method for
     * {@link EventRegisterService#register(EventRegisterIn, boolean, boolean, boolean)}.
     */
    @Test
    public void testRegisterSuccess() {
        BaseOutput success = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
                ApiConstant.INT_ZERO, null);
        // Mock 当前库中没有相同code的事件
        Mockito.when(this.eventDao.selectList(Matchers.any(Event.class))).thenReturn(null);
        Mockito.when(this.eventDao.insert(Matchers.any(Event.class))).thenReturn(1);

        Mockito.when(this.eventSourceDao.selectList(Matchers.any(EventSource.class))).thenReturn(eventSources);

        Mockito.when(this.eventObjectDao.selectList(Matchers.any(EventObject.class))).thenReturn(eventObjects);

        BaseOutput actual = this.eventRegisterService.register(this.registerIn, false, true, false);

        Assert.assertEquals(success.getCode(), actual.getCode());
        Assert.assertEquals(success.getMsg(), actual.getMsg());


        // Mock 当前库中没有相同code的事件,返回的结果是size 为0 的list
        Mockito.when(this.eventDao.selectList(Matchers.any(Event.class))).thenReturn(new ArrayList<Event>());
        Mockito.when(this.eventDao.insert(Matchers.any(Event.class))).thenReturn(1);
        BaseOutput actual1 = this.eventRegisterService.register(this.registerIn, false, true, false);

        Assert.assertEquals(success.getCode(), actual1.getCode());
        Assert.assertEquals(success.getMsg(), actual1.getMsg());
    }

    /**
     * Test method for
     * {@link EventRegisterService#register(EventRegisterIn, boolean, boolean, boolean)}.
     */
    @Test
    public void testRegisterEventSourceOrObjectIsNull() {
        BaseOutput success = new BaseOutput(ApiErrorCode.DB_ERROR_TABLE_DATA_NOT_EXIST.getCode(),
                ApiErrorCode.DB_ERROR_TABLE_DATA_NOT_EXIST.getMsg(), ApiConstant.INT_ZERO, null);
        // Mock 当前库中没有相应Source code的事件
        Mockito.when(this.eventDao.selectList(Matchers.any(Event.class))).thenReturn(null);
        Mockito.when(this.eventDao.insert(Matchers.any(Event.class))).thenReturn(1);

        Mockito.when(this.eventSourceDao.selectList(Matchers.any(EventSource.class))).thenReturn(null);

        Mockito.when(this.eventObjectDao.selectList(Matchers.any(EventObject.class))).thenReturn(eventObjects);

        BaseOutput actual = this.eventRegisterService.register(this.registerIn, false, true, false);

        Assert.assertEquals(success.getCode(), actual.getCode());
        Assert.assertEquals(success.getMsg(), actual.getMsg());

        // Mock 当前库中Source 有查询结果，但是size为0
        Mockito.when(this.eventDao.selectList(Matchers.any(Event.class))).thenReturn(null);
        Mockito.when(this.eventDao.insert(Matchers.any(Event.class))).thenReturn(1);

        Mockito.when(this.eventSourceDao.selectList(Matchers.any(EventSource.class)))
                .thenReturn(new ArrayList<EventSource>());

        Mockito.when(this.eventObjectDao.selectList(Matchers.any(EventObject.class))).thenReturn(eventObjects);

        actual = this.eventRegisterService.register(this.registerIn, false, true, false);

        Assert.assertEquals(success.getCode(), actual.getCode());
        Assert.assertEquals(success.getMsg(), actual.getMsg());


        // Mock 当前库中没有相应object code的事件
        Mockito.when(this.eventSourceDao.selectList(Matchers.any(EventSource.class))).thenReturn(eventSources);

        Mockito.when(this.eventObjectDao.selectList(Matchers.any(EventObject.class))).thenReturn(null);

        actual = this.eventRegisterService.register(this.registerIn, false, true, false);

        Assert.assertEquals(success.getCode(), actual.getCode());
        Assert.assertEquals(success.getMsg(), actual.getMsg());

        // Mock 当前库中有相应object code的记录，但是查询 size 为0
        Mockito.when(this.eventSourceDao.selectList(Matchers.any(EventSource.class))).thenReturn(eventSources);

        Mockito.when(this.eventObjectDao.selectList(Matchers.any(EventObject.class)))
                .thenReturn(new ArrayList<EventObject>());

        actual = this.eventRegisterService.register(this.registerIn, false, true, false);

        Assert.assertEquals(success.getCode(), actual.getCode());
        Assert.assertEquals(success.getMsg(), actual.getMsg());

    }



    /**
     * Test method for
     * {@link EventRegisterService#register(EventRegisterIn, boolean, boolean, boolean)}.
     */
    @Test
    public void testRegisterCodeExist() {
        BaseOutput expect = new BaseOutput(ApiErrorCode.BIZ_ERROR_EVENT_IS_EXIST.getCode(),
                ApiErrorCode.BIZ_ERROR_EVENT_IS_EXIST.getMsg(), ApiConstant.INT_ZERO, null);
        // Mock 当前库中有相同code的事件
        List<Event> events = new ArrayList<>();
        events.add(event);
        Mockito.when(this.eventDao.selectList(Matchers.any(Event.class))).thenReturn(events);
        BaseOutput actual = this.eventRegisterService.register(this.registerIn, false, true, false);

        Assert.assertEquals(expect.getCode(), actual.getCode());
        Assert.assertEquals(expect.getMsg(), actual.getMsg());

    }
}
