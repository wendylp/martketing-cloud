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
package cn.rongcapital.mkt.dao.material.coupon;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.rongcapital.mkt.dao.material.coupon.MaterialCouponCodeDao;
import cn.rongcapital.mkt.dao.material.coupon.MaterialCouponDao;
import cn.rongcapital.mkt.dao.testbase.AbstractUnitTest;
import cn.rongcapital.mkt.material.coupon.po.MaterialCoupon;
import cn.rongcapital.mkt.material.coupon.po.MaterialCouponCode;

@RunWith(SpringJUnit4ClassRunner.class)
public class MaterialCouponDeleteDaoTest extends AbstractUnitTest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MaterialCouponDao materialCouponDao;

    @Autowired
    private MaterialCouponCodeDao materialCouponCodeDao;

    MaterialCoupon mc = null;

    @Before
    public void setUp() {
        mc = new MaterialCoupon();
        mc.setId(20l);
        mc.setStatus((byte) 0);
    }

    @Test
    public void test() {
        Long id = 1l; // 是否存在
       // Assert.assertNotNull(materialCouponDao.selectOneCoupon(id));
    }

    @Test
    public void update() {

        materialCouponDao.updateById(mc);
        materialCouponCodeDao.updateByCouponId(mc.getId());
    }

}
