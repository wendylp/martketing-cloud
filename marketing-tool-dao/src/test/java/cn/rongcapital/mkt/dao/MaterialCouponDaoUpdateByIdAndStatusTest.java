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

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.rongcapital.mkt.common.enums.CouponStatusEnum;
import cn.rongcapital.mkt.dao.testbase.AbstractUnitTest;
import cn.rongcapital.mkt.po.MaterialCoupon;

public class MaterialCouponDaoUpdateByIdAndStatusTest extends AbstractUnitTest {

    @Autowired
    private MaterialCouponDao materialCouponDao;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {}

    /**
     * 插入status为1的数据进行更新
     * 
     */
    @Test
    public void testUpdateByIdAndStatus01() {
        MaterialCoupon coupon = new MaterialCoupon();
        coupon.setSourceCode("common");
        coupon.setType("voucher");
        coupon.setChanelCode("msm");
        coupon.setTitle("zhuxuelongtest");
        coupon.setCouponStatus(CouponStatusEnum.COUPONSTATUS_RELEASED.getCode());
        coupon.setStatus(1);
        List<MaterialCoupon> dataList = materialCouponDao.selectList(coupon);
        if (dataList.size() == 0) {
            materialCouponDao.insert(coupon);
            materialCouponDao.updateById(coupon);
        } else {
            coupon.setId(dataList.get(0).getId());
        }

        MaterialCoupon couponUpdate = new MaterialCoupon();
        couponUpdate.setId(coupon.getId());
        couponUpdate.setTitle("Updated");
        materialCouponDao.updateByIdAndStatus(couponUpdate);

        MaterialCoupon couponSearch = new MaterialCoupon();
        couponSearch.setId(coupon.getId());
        List<MaterialCoupon> list = materialCouponDao.selectList(couponSearch);
        Assert.assertEquals(1, list.size());
        Assert.assertEquals("zhuxuelongtest", list.get(0).getTitle());
    }

    /**
     * 插入status为0的数据进行更新
     * 
     */
    @Test
    public void testUpdateByIdAndStatus02() {
        MaterialCoupon coupon = new MaterialCoupon();
        coupon.setSourceCode("common");
        coupon.setType("voucher");
        coupon.setChanelCode("msm");
        coupon.setTitle("zhuxuelongtest");
        coupon.setCouponStatus(CouponStatusEnum.COUPONSTATUS_RELEASED.getCode());
        coupon.setStatus(0);
        List<MaterialCoupon> dataList = materialCouponDao.selectList(coupon);
        if (dataList.size() == 0) {
            materialCouponDao.insert(coupon);
        } else {
            coupon.setId(dataList.get(0).getId());
        }

        MaterialCoupon couponUpdate = new MaterialCoupon();
        couponUpdate.setId(coupon.getId());
        couponUpdate.setTitle("Updated");
        materialCouponDao.updateByIdAndStatus(couponUpdate);

        MaterialCoupon couponSearch = new MaterialCoupon();
        couponSearch.setId(coupon.getId());
        List<MaterialCoupon> list = materialCouponDao.selectList(couponSearch);
        Assert.assertEquals(1, list.size());
        Assert.assertEquals("Updated", list.get(0).getTitle());
    }

}
