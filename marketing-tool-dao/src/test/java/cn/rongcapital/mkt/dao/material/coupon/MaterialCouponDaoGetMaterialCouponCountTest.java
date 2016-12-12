/*************************************************
 * @功能简述: DAO接口测试类
 * @项目名称: marketing cloud
 * @see:
 * @author: zhuxuelong
 * @version: 0.0.1
 * @date: 2016-12-07
 * @复审人:
 *************************************************/
package cn.rongcapital.mkt.dao.material.coupon;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.rongcapital.mkt.common.enums.MaterialCouponChannelCodeEnum;
import cn.rongcapital.mkt.common.enums.MaterialCouponSourceCodeEnum;
import cn.rongcapital.mkt.common.enums.MaterialCouponStatusEnum;
import cn.rongcapital.mkt.common.enums.MaterialCouponTypeEnum;
import cn.rongcapital.mkt.dao.material.coupon.MaterialCouponDao;
import cn.rongcapital.mkt.dao.testbase.AbstractUnitTest;
import cn.rongcapital.mkt.material.coupon.po.MaterialCoupon;

public class MaterialCouponDaoGetMaterialCouponCountTest extends AbstractUnitTest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MaterialCouponDao materialCouponDao;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        MaterialCoupon coupon = new MaterialCoupon();
        coupon.setSourceCode(MaterialCouponSourceCodeEnum.COMMON.getCode());
        coupon.setType(MaterialCouponTypeEnum.VOUCHER.getCode());
        coupon.setChannelCode(MaterialCouponChannelCodeEnum.SMS.getCode());
        coupon.setTitle("zhuxuelongtest");
        coupon.setCouponStatus(MaterialCouponStatusEnum.RELEASED.getCode());
        coupon.setStatus(Byte.valueOf("0"));
        int count = materialCouponDao.selectListCount(coupon);
        if (count == 0) {
            materialCouponDao.insert(coupon);
        }
        coupon = new MaterialCoupon();
        coupon.setSourceCode(MaterialCouponSourceCodeEnum.COMMON.getCode());
        coupon.setType(MaterialCouponTypeEnum.VOUCHER.getCode());
        coupon.setChannelCode(MaterialCouponChannelCodeEnum.SMS.getCode());
        coupon.setTitle("zhuxuelongtest");
        coupon.setStatus(Byte.valueOf("0"));
        coupon.setCouponStatus(MaterialCouponStatusEnum.RELEASING.getCode());
        count = materialCouponDao.selectListCount(coupon);
        if (count == 0) {
            materialCouponDao.insert(coupon);
        }
        coupon = new MaterialCoupon();
        coupon.setSourceCode(MaterialCouponSourceCodeEnum.COMMON.getCode());
        coupon.setType(MaterialCouponTypeEnum.VOUCHER.getCode());
        coupon.setChannelCode(MaterialCouponChannelCodeEnum.SMS.getCode());
        coupon.setTitle("zhuxuelongtest");
        coupon.setStatus(Byte.valueOf("0"));
        coupon.setCouponStatus(MaterialCouponStatusEnum.UNUSED.getCode());
        count = materialCouponDao.selectListCount(coupon);
        if (count == 0) {
            materialCouponDao.insert(coupon);
        }
        coupon = new MaterialCoupon();
        coupon.setSourceCode(MaterialCouponSourceCodeEnum.COMMON.getCode());
        coupon.setType(MaterialCouponTypeEnum.VOUCHER.getCode());
        coupon.setChannelCode(MaterialCouponChannelCodeEnum.SMS.getCode());
        coupon.setTitle("zhuxuelongtest");
        coupon.setStatus(Byte.valueOf("0"));
        coupon.setCouponStatus(MaterialCouponStatusEnum.USED.getCode());
        count = materialCouponDao.selectListCount(coupon);
        if (count == 0) {
            materialCouponDao.insert(coupon);
        }
        logger.info("测试: MaterialCouponDao 开始---------------------");
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
        logger.info("测试: MaterialCouponDao 结束---------------------");
    }

    /**
     * 获取指定条件的优惠券的数量(只传递渠道)
     * 
     */
    @Test
    public void testGetMaterialCouponCount01() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("chanelCode", MaterialCouponChannelCodeEnum.SMS.getCode());
        Assert.assertTrue(materialCouponDao.getMaterialCouponCount(paramMap) >= 4);
    }

    /**
     * 获取指定条件的优惠券的数量(只传递查询关键字)
     * 
     */
    @Test
    public void testGetMaterialCouponCount02() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("title", "zhuxuelongtest");
        Assert.assertTrue(materialCouponDao.getMaterialCouponCount(paramMap) >= 4);
    }

    /**
     * 获取指定条件的优惠券的数量(只传递状态)
     * 
     */
    @Test
    public void testGetMaterialCouponCount03() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("couponStatus", MaterialCouponStatusEnum.RELEASED.getCode());
        Assert.assertTrue(materialCouponDao.getMaterialCouponCount(paramMap) >= 1);
    }

    /**
     * 获取指定条件的优惠券的数量(传递查询关键字+渠道)
     * 
     */
    @Test
    public void testGetMaterialCouponCount04() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("chanelCode", MaterialCouponChannelCodeEnum.SMS.getCode());
        paramMap.put("title", "zhuxuelongtest");
        Assert.assertTrue(materialCouponDao.getMaterialCouponCount(paramMap) >= 4);
    }

    /**
     * 获取指定条件的优惠券的数量(传递查询关键字+渠道+状态)
     * 
     */
    @Test
    public void testGetMaterialCouponCount05() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("chanelCode", MaterialCouponChannelCodeEnum.SMS.getCode());
        paramMap.put("title", "zhuxuelongtest");
        paramMap.put("couponStatus", MaterialCouponStatusEnum.RELEASED.getCode());
        Assert.assertTrue(materialCouponDao.getMaterialCouponCount(paramMap) >= 1);
    }

    /**
     * 获取指定条件的优惠券的数量(不传递)
     * 
     */
    @Test
    public void testGetMaterialCouponCount06() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        Assert.assertTrue(materialCouponDao.getMaterialCouponCount(paramMap) >= 4);
    }

}
