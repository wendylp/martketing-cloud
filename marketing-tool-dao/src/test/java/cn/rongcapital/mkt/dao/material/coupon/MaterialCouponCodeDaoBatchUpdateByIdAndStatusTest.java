/*************************************************
 * @功能简述: DAO接口测试类
 * @项目名称: marketing cloud
 * @see:
 * @author: zhuxuelong
 * @version: 0.0.1
 * @date:
 * @复审人:
 *************************************************/
package cn.rongcapital.mkt.dao.material.coupon;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.rongcapital.mkt.dao.material.coupon.MaterialCouponCodeDao;
import cn.rongcapital.mkt.dao.testbase.AbstractUnitTest;
import cn.rongcapital.mkt.material.coupon.po.MaterialCouponCode;

public class MaterialCouponCodeDaoBatchUpdateByIdAndStatusTest extends AbstractUnitTest {

    @Autowired
    private MaterialCouponCodeDao materialCouponCodeDao;

    @Before
    public void setUp() throws Exception {}

    @After
    public void tearDown() throws Exception {}

    /**
     * 插入status为1的数据进行更新
     * 
     */
    @Test
    public void test01() {
        List<MaterialCouponCode> codeTestList = new ArrayList<MaterialCouponCode>();
        for (int i = 0; i < 50; i++) {
            MaterialCouponCode coupon = new MaterialCouponCode();
            coupon.setCouponId(9L);
            coupon.setCode(UUID.randomUUID().toString().substring(0, 20));
            coupon.setUser("");
            coupon.setReleaseStatus("unreleased");
            coupon.setStatus((byte) 1);
            List<MaterialCouponCode> dataList = materialCouponCodeDao.selectList(coupon);
            if (dataList.size() == 0) {
                materialCouponCodeDao.insert(coupon);
                materialCouponCodeDao.updateById(coupon);
            } else {
                coupon.setId(dataList.get(0).getId());
            }
            codeTestList.add(coupon);
        }
        Assert.assertEquals(50, codeTestList.size());

        List<MaterialCouponCode> codeList = new ArrayList<MaterialCouponCode>();
        for (MaterialCouponCode testCode : codeTestList) {
            MaterialCouponCode couponUpdate = new MaterialCouponCode();
            couponUpdate.setId(testCode.getId());
            couponUpdate.setUser("123456");;
            couponUpdate.setReleaseStatus("received");
            codeList.add(couponUpdate);
        }
        Assert.assertEquals(50, codeList.size());
        materialCouponCodeDao.batchUpdateByIdAndStatus(codeList);

        for (MaterialCouponCode testCode : codeTestList) {
            MaterialCouponCode couponSearch = new MaterialCouponCode();
            couponSearch.setId(testCode.getId());
            List<MaterialCouponCode> list = materialCouponCodeDao.selectList(couponSearch);
            Assert.assertEquals(1, list.size());
            Assert.assertEquals("", list.get(0).getUser());
            Assert.assertEquals("unreleased", list.get(0).getReleaseStatus());
        }
    }

    /**
     * 插入status为0的数据进行更新
     * 
     */
    @Test
    public void test02() {
        List<MaterialCouponCode> codeTestList = new ArrayList<MaterialCouponCode>();
        for (int i = 0; i < 50; i++) {
            MaterialCouponCode coupon = new MaterialCouponCode();
            coupon.setCouponId(9L);
            coupon.setCode(UUID.randomUUID().toString().substring(0, 20));
            coupon.setUser("");
            coupon.setReleaseStatus("unreleased");
            coupon.setStatus((byte) 0);
            List<MaterialCouponCode> dataList = materialCouponCodeDao.selectList(coupon);
            if (dataList.size() == 0) {
                materialCouponCodeDao.insert(coupon);
            } else {
                coupon.setId(dataList.get(0).getId());
            }
            codeTestList.add(coupon);
        }
        Assert.assertEquals(50, codeTestList.size());

        List<MaterialCouponCode> codeList = new ArrayList<MaterialCouponCode>();
        for (MaterialCouponCode testCode : codeTestList) {
            MaterialCouponCode couponUpdate = new MaterialCouponCode();
            couponUpdate.setId(testCode.getId());
            couponUpdate.setUser("123456");;
            couponUpdate.setReleaseStatus("received");
            codeList.add(couponUpdate);
        }
        Assert.assertEquals(50, codeList.size());
        materialCouponCodeDao.batchUpdateByIdAndStatus(codeList);

        for (MaterialCouponCode testCode : codeTestList) {
            MaterialCouponCode couponSearch = new MaterialCouponCode();
            couponSearch.setId(testCode.getId());
            List<MaterialCouponCode> list = materialCouponCodeDao.selectList(couponSearch);
            Assert.assertEquals(1, list.size());
            Assert.assertEquals("123456", list.get(0).getUser());
            Assert.assertEquals("received", list.get(0).getReleaseStatus());
        }
    }

}
