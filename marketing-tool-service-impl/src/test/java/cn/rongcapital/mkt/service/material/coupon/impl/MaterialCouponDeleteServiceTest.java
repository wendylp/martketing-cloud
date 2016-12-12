/*************************************************
* @功能及特点的描述简述: message（例如该类是用来做什么的）
* 该类被编译测试过
* @see （与该类关联的类）：
* @对应项目名称：MC
* @author: liuhaizhan
* @version: 版本
* @date(创建、开发日期)：2016年12月7日
* 最后修改日期：2016年12月7日
* @复审人:
*************************************************/
package cn.rongcapital.mkt.service.material.coupon.impl;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import cn.rongcapital.mkt.material.coupon.dao.MaterialCouponCodeDao;
import cn.rongcapital.mkt.material.coupon.dao.MaterialCouponDao;
import cn.rongcapital.mkt.material.coupon.po.MaterialCoupon;
import cn.rongcapital.mkt.material.coupon.service.MaterialCouponDeleteService;
import cn.rongcapital.mkt.material.coupon.service.impl.MaterialCouponDeleteServiceImpl;
import cn.rongcapital.mkt.vo.BaseOutput;

@RunWith(MockitoJUnitRunner.class)
public class MaterialCouponDeleteServiceTest {

    private MaterialCouponDeleteService materialCouponDeleteService;

    @Mock
    private MaterialCouponDao materialCouponDao;

    @Mock
    private MaterialCouponCodeDao materialCouponCodeDao;
    private Long id = 1l;

    @Before
    public void set() {
        MaterialCoupon mcp = new MaterialCoupon();
        mcp.setCouponStatus("released");
        materialCouponDeleteService = new MaterialCouponDeleteServiceImpl();
        Mockito.when(materialCouponDao.selectOneCoupon(id)).thenReturn(mcp);
        ReflectionTestUtils.setField(materialCouponDeleteService, "materialCouponDao", materialCouponDao);
        ReflectionTestUtils.setField(materialCouponDeleteService, "materialCouponCodeDao", materialCouponCodeDao);

    }

    @Test
    public void del() {
        BaseOutput baseOutput = new BaseOutput(1, "非未使用状态,不可操作!", 1, null);
        BaseOutput actual = (BaseOutput) materialCouponDeleteService.Delete(id);
        Assert.assertEquals(baseOutput.getMsg(), actual.getMsg());
        // Assert.assertSame(baseOutput, actual);
    }

}
