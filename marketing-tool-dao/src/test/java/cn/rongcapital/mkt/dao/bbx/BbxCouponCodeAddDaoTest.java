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
package cn.rongcapital.mkt.dao.bbx;

import cn.rongcapital.mkt.bbx.po.BbxCouponCodeAdd;
import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.util.DateUtil;
import cn.rongcapital.mkt.dao.testbase.AbstractUnitTest;
import cn.rongcapital.mkt.po.DataMember;


import org.apache.commons.collections4.CollectionUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class BbxCouponCodeAddDaoTest extends AbstractUnitTest {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private BbxCouponCodeAddDao dao;


    @Test
    public void testSuccess(){
        List<BbxCouponCodeAdd> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            //为贝贝熊同步一份优惠码的数据
            BbxCouponCodeAdd bbxCouponCode = new BbxCouponCodeAdd();
            bbxCouponCode.setActionId(ApiConstant.INT_ZERO);

            bbxCouponCode.setVipId(i+1);
            bbxCouponCode.setCouponId(i+1);
            bbxCouponCode.setCouponMoney(20d);
            bbxCouponCode.setCanUseBeginDate(DateUtil.getStringFromDate(new Date(),ApiConstant.DATE_FORMAT_yyyy_MM_dd) );
            bbxCouponCode.setCanUserEndDate(DateUtil.getStringFromDate(new Date(),ApiConstant.DATE_FORMAT_yyyy_MM_dd));
            bbxCouponCode.setStoreCode("");
            bbxCouponCode.setCreateTime(new Date());
            bbxCouponCode.setSynchronizeable(Boolean.FALSE);
            bbxCouponCode.setSynchSuccess(Boolean.FALSE);
            bbxCouponCode.setSynchronizedTime(null);
            list.add(bbxCouponCode);
        }
        int i = this.dao.batchInsert(list);
        Assert.assertEquals(100,i);
    }
    @Test
    public void testSelectCampaignSmsItemByCouponCodeId(){
        Map<String,Object> result =  this.dao.selectCampaignSmsItemByCouponId(18518757);
        Assert.assertEquals(100,result.get(""));
    }

    @Test
    public void testSelectListByMinId(){
        BbxCouponCodeAdd param = new BbxCouponCodeAdd();
        param.setSynchronizeable(Boolean.TRUE);
        int totalCount = this.dao.selectListCount(param);
        int pageSize = 5;
        int pageCount = totalCount / pageSize;
        if (totalCount % pageSize > 0) {
            pageCount = pageCount + 1;
        }
        List<BbxCouponCodeAdd> bbxCouponCodeAdds = null;
        for (int i = 0; i < pageCount; i++) {
            param.setPageSize(pageSize);
            param.setOrderField("id");
            param.setOrderFieldType("ASC");
            if(CollectionUtils.isNotEmpty(bbxCouponCodeAdds)){
                param.setId(bbxCouponCodeAdds.get(bbxCouponCodeAdds.size()-1).getId());
            }
            bbxCouponCodeAdds = this.dao.selectListByMinId(param);
            for (BbxCouponCodeAdd item : bbxCouponCodeAdds) {
                System.out.println(item.getId());
            }
        }
    }

}
