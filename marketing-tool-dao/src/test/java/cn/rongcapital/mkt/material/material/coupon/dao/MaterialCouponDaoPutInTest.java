/*************************************************
* @功能及特点的描述简述: 返回投放优惠券统计
* 该类被编译测试过
* @see （与该类关联的类）：
* @对应项目名称：MC
* @author: liuhaizhan
* @version: 版本
* @date(创建、开发日期)：2016年12月9日
* 最后修改日期：2016年12月9日
* @复审人:
*************************************************/
package cn.rongcapital.mkt.material.material.coupon.dao;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.rongcapital.mkt.dao.testbase.AbstractUnitTest;
import cn.rongcapital.mkt.material.coupon.dao.MaterialCouponDao;

@RunWith(SpringJUnit4ClassRunner.class)
public class MaterialCouponDaoPutInTest extends AbstractUnitTest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MaterialCouponDao materialCouponDao;

    @Test
    public void test() {
        List<Map> map = materialCouponDao.getPutInCoupon(12l);

        for (Map data : map) {
            logger.info(data.get("status").toString());
            logger.info(data.get("rest_count").toString());
            logger.info(data.get("tjcount").toString());
        }

    }
}
