/*************************************************
 * @功能及特点的描述简述: message（例如该类是用来做什么的）
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：营销云系统
 * @author: 谢小良
 * @version: 版本v1.7
 * @date(创建、开发日期)：2017-4-16
 * @date(最后修改日期)：2017-4-16
 * @复审人：
 *************************************************/
package cn.rongcapital.mkt.job.bbx.service.impl;

import cn.rongcapital.mkt.bbx.po.BbxCouponCodeAdd;
import cn.rongcapital.mkt.bbx.service.BbxCouponCodeAddService;
import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.enums.*;
import cn.rongcapital.mkt.common.util.DateUtil;
import cn.rongcapital.mkt.dao.bbx.BbxCouponCodeAddDao;
import cn.rongcapital.mkt.dao.material.coupon.MaterialCouponCodeDao;
import cn.rongcapital.mkt.dao.material.coupon.MaterialCouponDao;
import cn.rongcapital.mkt.job.bbx.Service.impl.BbxSynchronizeCouponServiceImpl;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.job.service.impl.mq.CampaignAutoCancelTaskService;
import cn.rongcapital.mkt.material.coupon.po.MaterialCoupon;
import cn.rongcapital.mkt.material.coupon.po.MaterialCouponCode;
import cn.rongcapital.mkt.material.coupon.vo.MaterialCouponCodeStatusUpdateVO;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.util.StringUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(PowerMockRunner.class)
public class BbxSynchronizeCouponServiceImplTest {

    private TaskService service;
    @Mock
    private BbxCouponCodeAddDao bbxCouponCodeAddDao;


    @Before
    public void setUp() throws Exception {
        service = new BbxSynchronizeCouponServiceImpl();
        Mockito.when(bbxCouponCodeAddDao.selectListCount(Mockito.any())).thenReturn(100);

        List<BbxCouponCodeAdd> list = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            //为贝贝熊同步一份优惠码的数据
            BbxCouponCodeAdd bbxCouponCode = new BbxCouponCodeAdd();
            bbxCouponCode.setActionId(ApiConstant.INT_ZERO);
            bbxCouponCode.setVipId(10000);
            bbxCouponCode.setCouponId(1);
            bbxCouponCode.setCouponMoney(20d);
            bbxCouponCode.setCanUseBeginDate( "2017-04-16");
            bbxCouponCode.setCanUserEndDate("2017-04-17");
            bbxCouponCode.setStoreCode("");
            bbxCouponCode.setCreateTime(new Date());
            bbxCouponCode.setSynchronizeable(Boolean.FALSE);
            bbxCouponCode.setSynchronizedTime(null);
            list.add(bbxCouponCode);
        }
        Mockito.when(bbxCouponCodeAddDao.selectList(Mockito.any())).thenReturn(list);
        ReflectionTestUtils.setField(service, "bbxCouponCodeAddDao", bbxCouponCodeAddDao);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void addCouponCodeToBBX() throws Exception {
        this.service.task(1);
        Mockito.verify(this.bbxCouponCodeAddDao,Mockito.times(1)).updateById(Mockito.any());
    }
}
