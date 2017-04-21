package cn.rongcapital.mkt.unittest.service.bbx;

import cn.rongcapital.mkt.bbx.po.TBBXMember;
import cn.rongcapital.mkt.bbx.service.BbxCouponCodeAddService;
import cn.rongcapital.mkt.bbx.service.impl.BbxCouponCodeAddServiceImpl;
import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.enums.*;
import cn.rongcapital.mkt.dao.bbx.BbxCouponCodeAddDao;
import cn.rongcapital.mkt.dao.material.coupon.MaterialCouponCodeDao;
import cn.rongcapital.mkt.dao.material.coupon.MaterialCouponDao;
import cn.rongcapital.mkt.job.bbx.Service.impl.BbxSynchronizeCouponServiceImpl;
import cn.rongcapital.mkt.job.service.base.SpringDataPageable;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.material.coupon.po.MaterialCoupon;
import cn.rongcapital.mkt.material.coupon.po.MaterialCouponCode;
import cn.rongcapital.mkt.material.coupon.service.MaterialCouponCodeCheckService;
import cn.rongcapital.mkt.material.coupon.service.MaterialCouponCodeStatusUpdateService;
import cn.rongcapital.mkt.material.coupon.vo.MaterialCouponCodeStatusUpdateVO;
import cn.rongcapital.mkt.unittest.AbstractUnitTest;
import cn.rongcapital.mkt.vo.BaseOutput;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*************************************************
 * @功能及特点的描述简述: message（例如该类是用来做什么的）
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：营销云系统
 * @author: 谢小良
 * @version: 版本v1.7
 * @date(创建、开发日期)：2017-4-14
 * @date(最后修改日期)：2017-4-14
 * @复审人：
 *************************************************/

public class BbxCouponCodeAddServiceImplTest extends AbstractUnitTest {

    @Autowired
    private BbxCouponCodeAddService service;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private MaterialCouponCodeStatusUpdateService materialCouponCodeStatusUpdateService;

    @Autowired
    private MaterialCouponCodeDao materialCouponCodeDao;
    @Autowired
    private ApplicationContext cotext;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void addCouponCodeToBBX() throws Exception {

        List<MaterialCouponCodeStatusUpdateVO> voList = new ArrayList<>();


        MaterialCouponCode paramCode = new MaterialCouponCode();
        paramCode.setCouponId(275l);
        List<MaterialCouponCode> materialCouponCodes = this.materialCouponCodeDao.selectList(paramCode);
        // 需要根据电话号码将相应的会员号码找到
        SpringDataPageable pageable = new SpringDataPageable();
        List<Sort.Order> orders = new ArrayList<Sort.Order>();
        orders.add(new Sort.Order(Sort.Direction.DESC, "_id"));
        Sort sort = new Sort(orders);
        Query query = new Query();
        if (null != sort) {
            query.with(sort);
        }
        pageable.setSort(sort);

        pageable.setPagenumber(0);
        //每页条数
        pageable.setPagesize(materialCouponCodes.size());
        List<TBBXMember> members = mongoTemplate.find(query.with(pageable), TBBXMember.class);

        for (int i = 0; i < materialCouponCodes.size(); i++) {
            MaterialCouponCodeStatusUpdateVO vo = new MaterialCouponCodeStatusUpdateVO();
            vo.setId(materialCouponCodes.get(i).getId());
            vo.setStatus(MaterialCouponCodeReleaseStatusEnum.RECEIVED.getCode());
            vo.setUser(members.get(i).getPhone());
            voList.add(vo);
        }

        materialCouponCodeStatusUpdateService.updateMaterialCouponCodeStatus(voList);
        service.addCouponCodeToBBX(voList);
    }

    @Test
    public void sychBBXCoupon(){
        Object serviceBean = cotext.getBean(getServiceName("BbxSynchronizeCouponServiceImpl"));
        if (serviceBean instanceof TaskService) {
            TaskService taskService = (TaskService) serviceBean;
            taskService.task(1);
        }
    }

    private String getServiceName(String serviceName) {
        char serviceNameChar[] = serviceName.toCharArray();
        serviceNameChar[0] = Character.toLowerCase(serviceNameChar[0]);
        String sname = String.valueOf(serviceNameChar);
        return sname;
    }

}