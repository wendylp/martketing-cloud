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

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.rongcapital.mkt.common.enums.MaterialCouponStatusEnum;
import cn.rongcapital.mkt.dao.testbase.AbstractUnitTest;
import cn.rongcapital.mkt.po.MaterialCoupon;
import cn.rongcapital.mkt.po.MaterialCouponCode;
import cn.rongcapital.mkt.vo.out.MaterialCouponCodeVerifyListOut;

public class MaterialCouponCodeDaoGetVerifyListTest extends AbstractUnitTest {

    @Autowired
    private MaterialCouponCodeDao materialCouponCodeDao;

    @Autowired
    private MaterialCouponDao materialCouponDao;

    private static final Date now = new Date();

    private DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    private Long couponId;

    private List<MaterialCouponCode> codeList = null;
    private MaterialCoupon coupon = null;

    @Before
    public void setUp() throws Exception {
        codeList = new ArrayList<MaterialCouponCode>();
        coupon = new MaterialCoupon();
        coupon.setSourceCode("common");
        coupon.setType("voucher");
        coupon.setChannelCode("sms");
        coupon.setTitle(UUID.randomUUID().toString());
        coupon.setCouponStatus(MaterialCouponStatusEnum.RELEASED.getCode());
        coupon.setStatus(Byte.valueOf("0"));
        coupon.setEndTime(now);
        coupon.setAmount(BigDecimal.valueOf(10));
        List<MaterialCoupon> dataList = materialCouponDao.selectList(coupon);
        if (dataList.size() == 0) {
            materialCouponDao.insert(coupon);
        } else {
            coupon.setId(dataList.get(0).getId());
        }
        couponId = coupon.getId();
        // unreleased/unverify
        MaterialCouponCode couponCode = new MaterialCouponCode();
        couponCode.setCouponId(coupon.getId());
        couponCode.setCode(UUID.randomUUID().toString().substring(0, 20));
        couponCode.setReleaseStatus("unreleased");
        couponCode.setVerifyStatus("unverify");
        couponCode.setStatus((byte) 0);
        List<MaterialCouponCode> couponCodeList = materialCouponCodeDao.selectList(couponCode);
        if (couponCodeList.size() == 0) {
            materialCouponCodeDao.insert(couponCode);
        }
        codeList.add(couponCode);
        // unreceived/unverify
        couponCode = new MaterialCouponCode();
        couponCode.setCouponId(coupon.getId());
        couponCode.setCode(UUID.randomUUID().toString().substring(0, 20));
        couponCode.setUser("zhu");
        couponCode.setReleaseStatus("unreceived");
        couponCode.setVerifyStatus("unverify");
        couponCode.setStatus((byte) 0);
        couponCodeList = materialCouponCodeDao.selectList(couponCode);
        if (couponCodeList.size() == 0) {
            materialCouponCodeDao.insert(couponCode);
        }
        codeList.add(couponCode);
        // received/unverify
        couponCode = new MaterialCouponCode();
        couponCode.setCouponId(coupon.getId());
        couponCode.setCode(UUID.randomUUID().toString().substring(0, 20));
        couponCode.setUser("xue");
        couponCode.setReleaseStatus("received");
        couponCode.setVerifyStatus("unverify");
        couponCode.setStatus((byte) 0);
        couponCodeList = materialCouponCodeDao.selectList(couponCode);
        if (couponCodeList.size() == 0) {
            materialCouponCodeDao.insert(couponCode);
        }
        codeList.add(couponCode);
        // received/verified
        couponCode = new MaterialCouponCode();
        couponCode.setCouponId(coupon.getId());
        couponCode.setCode(UUID.randomUUID().toString().substring(0, 20));
        couponCode.setUser("long");
        couponCode.setReleaseStatus("received");
        couponCode.setVerifyStatus("verified");
        couponCode.setVerifyTime(now);
        couponCode.setStatus((byte) 0);
        couponCodeList = materialCouponCodeDao.selectList(couponCode);
        if (couponCodeList.size() == 0) {
            materialCouponCodeDao.insert(couponCode);
        }
        codeList.add(couponCode);
        // received/fail
        couponCode = new MaterialCouponCode();
        couponCode.setCouponId(coupon.getId());
        couponCode.setCode(UUID.randomUUID().toString().substring(0, 20));
        couponCode.setUser("XXX");
        couponCode.setReleaseStatus("received");
        couponCode.setVerifyStatus("fail");
        couponCode.setVerifyTime(now);
        couponCode.setStatus((byte) 0);
        couponCodeList = materialCouponCodeDao.selectList(couponCode);
        if (couponCodeList.size() == 0) {
            materialCouponCodeDao.insert(couponCode);
        }
        codeList.add(couponCode);
    }

    @After
    public void tearDown() throws Exception {}

    /**
     * ID
     */
    @Test
    public void test01() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", couponId);
        paramMap.put("index", 0);
        paramMap.put("size", 10);
        Assert.assertEquals(5, materialCouponCodeDao.getCouponCodeVerifyListCnt(paramMap));
        List<MaterialCouponCodeVerifyListOut> list = materialCouponCodeDao.getCouponCodeVerifyList(paramMap);
        for (int i = 0; i < 5; i++) {
            MaterialCouponCodeVerifyListOut actual = list.get(i);
            MaterialCouponCode expect = codeList.get(i);
            Assert.assertEquals(coupon.getAmount().longValue(), actual.getAmount().longValue());
            Assert.assertEquals(coupon.getChannelCode(), actual.getChannelCode());
            Assert.assertEquals(expect.getCode(), actual.getCode());
            Assert.assertEquals(expect.getId(), actual.getId());
            Assert.assertEquals(expect.getVerifyStatus(), actual.getStatus());
            Assert.assertEquals(expect.getUser(), actual.getUser());
            if (i < 3) {
                Assert.assertNull(actual.getVerifyTime());
            } else {
                Assert.assertEquals(format.format(now.getTime()), format.format(actual.getVerifyTimeDate()));
            }
        }
    }

    /**
     * User
     */
    @Test
    public void test02() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", couponId);
        paramMap.put("user", "long");
        paramMap.put("index", 0);
        paramMap.put("size", 10);
        Assert.assertEquals(1, materialCouponCodeDao.getCouponCodeVerifyListCnt(paramMap));
        List<MaterialCouponCodeVerifyListOut> list = materialCouponCodeDao.getCouponCodeVerifyList(paramMap);
        MaterialCouponCodeVerifyListOut actual = list.get(0);
        MaterialCouponCode expect = codeList.get(3);
        Assert.assertEquals(coupon.getAmount().longValue(), actual.getAmount().longValue());
        Assert.assertEquals(coupon.getChannelCode(), actual.getChannelCode());
        Assert.assertEquals(expect.getCode(), actual.getCode());
        Assert.assertEquals(expect.getId(), actual.getId());
        Assert.assertEquals(expect.getVerifyStatus(), actual.getStatus());
        Assert.assertEquals(expect.getUser(), actual.getUser());
        Assert.assertEquals(format.format(now.getTime()), format.format(actual.getVerifyTimeDate()));
    }

    /**
     * receive_status
     */
    @Test
    public void test03() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", couponId);
        paramMap.put("releaseStatus", "received");
        paramMap.put("index", 0);
        paramMap.put("size", 10);
        Assert.assertEquals(3, materialCouponCodeDao.getCouponCodeVerifyListCnt(paramMap));
        List<MaterialCouponCodeVerifyListOut> list = materialCouponCodeDao.getCouponCodeVerifyList(paramMap);

        MaterialCouponCodeVerifyListOut actual = list.get(0);
        MaterialCouponCode expect = codeList.get(2);
        Assert.assertEquals(coupon.getAmount().longValue(), actual.getAmount().longValue());
        Assert.assertEquals(coupon.getChannelCode(), actual.getChannelCode());
        Assert.assertEquals(expect.getCode(), actual.getCode());
        Assert.assertEquals(expect.getId(), actual.getId());
        Assert.assertEquals(expect.getVerifyStatus(), actual.getStatus());
        Assert.assertEquals(expect.getUser(), actual.getUser());
        // Assert.assertEquals(format.format(now.getTime()), format.format(actual.getVerifyTimeDate()));
        Assert.assertNull(actual.getVerifyTime());


        actual = list.get(1);
        expect = codeList.get(3);
        Assert.assertEquals(coupon.getAmount().longValue(), actual.getAmount().longValue());
        Assert.assertEquals(coupon.getChannelCode(), actual.getChannelCode());
        Assert.assertEquals(expect.getCode(), actual.getCode());
        Assert.assertEquals(expect.getId(), actual.getId());
        Assert.assertEquals(expect.getVerifyStatus(), actual.getStatus());
        Assert.assertEquals(expect.getUser(), actual.getUser());
        Assert.assertEquals(format.format(now.getTime()), format.format(actual.getVerifyTimeDate()));


        actual = list.get(2);
        expect = codeList.get(4);
        Assert.assertEquals(coupon.getAmount().longValue(), actual.getAmount().longValue());
        Assert.assertEquals(coupon.getChannelCode(), actual.getChannelCode());
        Assert.assertEquals(expect.getCode(), actual.getCode());
        Assert.assertEquals(expect.getId(), actual.getId());
        Assert.assertEquals(expect.getVerifyStatus(), actual.getStatus());
        Assert.assertEquals(expect.getUser(), actual.getUser());
        Assert.assertEquals(format.format(now.getTime()), format.format(actual.getVerifyTimeDate()));
    }

    /**
     * verify_status
     */
    @Test
    public void test04() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", couponId);
        paramMap.put("verifyStatus", "fail");
        paramMap.put("index", 0);
        paramMap.put("size", 10);
        Assert.assertEquals(1, materialCouponCodeDao.getCouponCodeVerifyListCnt(paramMap));
        List<MaterialCouponCodeVerifyListOut> list = materialCouponCodeDao.getCouponCodeVerifyList(paramMap);

        MaterialCouponCodeVerifyListOut actual = list.get(0);
        MaterialCouponCode expect = codeList.get(4);
        Assert.assertEquals(coupon.getAmount().longValue(), actual.getAmount().longValue());
        Assert.assertEquals(coupon.getChannelCode(), actual.getChannelCode());
        Assert.assertEquals(expect.getCode(), actual.getCode());
        Assert.assertEquals(expect.getId(), actual.getId());
        Assert.assertEquals(expect.getVerifyStatus(), actual.getStatus());
        Assert.assertEquals(expect.getUser(), actual.getUser());
        Assert.assertEquals(format.format(now.getTime()), format.format(actual.getVerifyTimeDate()));
    }


    /**
     * expire_status(expired)
     */
    @Test
    public void test05() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", couponId);
        paramMap.put("expireStatus", "expired");
        Calendar date = Calendar.getInstance();
        date.setTime(new Date());
        date.add(Calendar.DATE, 2);
        paramMap.put("expireTime", date.getTime());
        paramMap.put("index", 0);
        paramMap.put("size", 10);
        Assert.assertEquals(5, materialCouponCodeDao.getCouponCodeVerifyListCnt(paramMap));
        List<MaterialCouponCodeVerifyListOut> list = materialCouponCodeDao.getCouponCodeVerifyList(paramMap);

        for (int i = 0; i < 5; i++) {
            MaterialCouponCodeVerifyListOut actual = list.get(i);
            MaterialCouponCode expect = codeList.get(i);
            Assert.assertEquals(coupon.getAmount().longValue(), actual.getAmount().longValue());
            Assert.assertEquals(coupon.getChannelCode(), actual.getChannelCode());
            Assert.assertEquals(expect.getCode(), actual.getCode());
            Assert.assertEquals(expect.getId(), actual.getId());
            Assert.assertEquals(expect.getVerifyStatus(), actual.getStatus());
            Assert.assertEquals(expect.getUser(), actual.getUser());
            if (i < 3) {
                Assert.assertNull(actual.getVerifyTime());
            } else {
                Assert.assertEquals(format.format(now.getTime()), format.format(actual.getVerifyTimeDate()));
            }
        }
    }

    /**
     * expire_status(unexpired)
     */
    @Test
    public void test05_1() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", couponId);
        paramMap.put("expireStatus", "unexpired");
        Calendar date = Calendar.getInstance();
        date.setTime(new Date());
        date.add(Calendar.DATE, -2);
        paramMap.put("expireTime", date.getTime());
        paramMap.put("index", 0);
        paramMap.put("size", 10);
        Assert.assertEquals(5, materialCouponCodeDao.getCouponCodeVerifyListCnt(paramMap));
        List<MaterialCouponCodeVerifyListOut> list = materialCouponCodeDao.getCouponCodeVerifyList(paramMap);

        for (int i = 0; i < 5; i++) {
            MaterialCouponCodeVerifyListOut actual = list.get(i);
            MaterialCouponCode expect = codeList.get(i);
            Assert.assertEquals(coupon.getAmount().longValue(), actual.getAmount().longValue());
            Assert.assertEquals(coupon.getChannelCode(), actual.getChannelCode());
            Assert.assertEquals(expect.getCode(), actual.getCode());
            Assert.assertEquals(expect.getId(), actual.getId());
            Assert.assertEquals(expect.getVerifyStatus(), actual.getStatus());
            Assert.assertEquals(expect.getUser(), actual.getUser());
            if (i < 3) {
                Assert.assertNull(actual.getVerifyTime());
            } else {
                Assert.assertEquals(format.format(now.getTime()), format.format(actual.getVerifyTimeDate()));
            }
        }
    }

    /**
     * index/size
     */
    @Test
    public void test06() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", couponId);
        paramMap.put("expireStatus", "unexpired");
        Calendar date = Calendar.getInstance();
        date.setTime(new Date());
        date.add(Calendar.DATE, -2);
        paramMap.put("expireTime", date.getTime());
        paramMap.put("index", 2);
        paramMap.put("size", 2);
        Assert.assertEquals(5, materialCouponCodeDao.getCouponCodeVerifyListCnt(paramMap));
        List<MaterialCouponCodeVerifyListOut> list = materialCouponCodeDao.getCouponCodeVerifyList(paramMap);

        for (int i = 2; i < 4; i++) {
            MaterialCouponCodeVerifyListOut actual = list.get(i - 2);
            MaterialCouponCode expect = codeList.get(i);
            Assert.assertEquals(coupon.getAmount().longValue(), actual.getAmount().longValue());
            Assert.assertEquals(coupon.getChannelCode(), actual.getChannelCode());
            Assert.assertEquals(expect.getCode(), actual.getCode());
            Assert.assertEquals(expect.getId(), actual.getId());
            Assert.assertEquals(expect.getVerifyStatus(), actual.getStatus());
            Assert.assertEquals(expect.getUser(), actual.getUser());
            if (i < 3) {
                Assert.assertNull(actual.getVerifyTime());
            } else {
                Assert.assertEquals(format.format(now.getTime()), format.format(actual.getVerifyTimeDate()));
            }
        }
    }

    /**
     * all
     */
    @Test
    public void test07() {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", couponId);
        paramMap.put("user", "long");
        paramMap.put("releaseStatus", "received");
        paramMap.put("verifyStatus", "verified");
        paramMap.put("expireStatus", "expired");
        Calendar date = Calendar.getInstance();
        date.setTime(new Date());
        date.add(Calendar.DATE, 2);
        paramMap.put("expireTime", date.getTime());
        paramMap.put("index", 0);
        paramMap.put("size", 10);
        Assert.assertEquals(1, materialCouponCodeDao.getCouponCodeVerifyListCnt(paramMap));
        List<MaterialCouponCodeVerifyListOut> list = materialCouponCodeDao.getCouponCodeVerifyList(paramMap);
        MaterialCouponCodeVerifyListOut actual = list.get(0);
        MaterialCouponCode expect = codeList.get(3);
        Assert.assertEquals(coupon.getAmount().longValue(), actual.getAmount().longValue());
        Assert.assertEquals(coupon.getChannelCode(), actual.getChannelCode());
        Assert.assertEquals(expect.getCode(), actual.getCode());
        Assert.assertEquals(expect.getId(), actual.getId());
        Assert.assertEquals(expect.getVerifyStatus(), actual.getStatus());
        Assert.assertEquals(expect.getUser(), actual.getUser());
        Assert.assertEquals(format.format(now.getTime()), format.format(actual.getVerifyTimeDate()));
    }
}
