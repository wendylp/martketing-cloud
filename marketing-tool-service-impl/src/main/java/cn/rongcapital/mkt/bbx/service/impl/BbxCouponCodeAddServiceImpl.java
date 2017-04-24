/*************************************************
 * @功能及特点的描述简述: 贝贝熊优惠码同步处理Service
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：营销云系统
 * @author: 谢小良
 * @version: 版本v1.7
 * @date(创建、开发日期)：2017-4-11
 * @date(最后修改日期)：2017-4-11
 * @复审人：
 *************************************************/
package cn.rongcapital.mkt.bbx.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;

import cn.rongcapital.mkt.bbx.po.BbxCouponCodeAdd;
import cn.rongcapital.mkt.bbx.po.BbxCouponRule;
import cn.rongcapital.mkt.bbx.po.TBBXMember;
import cn.rongcapital.mkt.bbx.po.TBBXOrderPayDetail;
import cn.rongcapital.mkt.bbx.po.TBBXTransactionHeadAndDetail;
import cn.rongcapital.mkt.bbx.service.BbxCouponCodeAddService;
import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.enums.MaterialCouponCodeReleaseStatusEnum;
import cn.rongcapital.mkt.common.enums.MaterialCouponCodeVerifyStatusEnum;
import cn.rongcapital.mkt.common.util.DateUtil;
import cn.rongcapital.mkt.dao.bbx.BbxCouponCodeAddDao;
import cn.rongcapital.mkt.dao.material.coupon.MaterialCouponCodeDao;
import cn.rongcapital.mkt.dao.material.coupon.MaterialCouponDao;
import cn.rongcapital.mkt.job.service.base.SpringDataPageable;
import cn.rongcapital.mkt.material.coupon.po.MaterialCoupon;
import cn.rongcapital.mkt.material.coupon.po.MaterialCouponCode;
import cn.rongcapital.mkt.material.coupon.vo.MaterialCouponCodeStatusUpdateVO;
import cn.rongcapital.mkt.service.CampaignDetailService;

@Service
public class BbxCouponCodeAddServiceImpl implements BbxCouponCodeAddService {

    @Autowired
    private MaterialCouponCodeDao materialCouponCodeDao;
    @Autowired
    private MaterialCouponDao materialCouponDao;
    @Autowired
    private BbxCouponCodeAddDao bbxCouponCodeAddDao;
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private MaterialCouponCodeDao couponCodeDao;
    
    @Autowired
    private CampaignDetailService campaignDetailService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    @Transactional
    public void addCouponCodeToBBX(List<MaterialCouponCodeStatusUpdateVO> voList) {
        List<BbxCouponCodeAdd> list = new ArrayList<>();

        for (MaterialCouponCodeStatusUpdateVO vo : voList) {
            //发送失败的数据不同步给贝贝熊
            if(MaterialCouponCodeReleaseStatusEnum.UNRECEIVED.getCode().equals( vo.getStatus())){
                continue;
            }
            MaterialCouponCode couponCode = this.getCouponIdByCodeId(vo.getId());
            MaterialCoupon coupon = this.getCouponById(couponCode.getCouponId());
            
            Map<String,Object> campasignMap = this.bbxCouponCodeAddDao.selectCampaignSmsItemByCouponId(vo.getId());

            Long campsignId = null;
            Long itemId = null;
            if(campasignMap !=null) {
                 campsignId = (Long) campasignMap.get("campaignHeadId");
                 itemId = (Long) campasignMap.get("itemId");
            }

            //为贝贝熊同步一份优惠码的数据
            BbxCouponCodeAdd bbxCouponCode = new BbxCouponCodeAdd();
            bbxCouponCode.setActionId(ApiConstant.INT_ZERO);
            // 需要根据电话号码将相应的会员号码找到
            TBBXMember member = mongoTemplate.findOne(new Query(Criteria.where("phone").is(couponCode.getUser())), TBBXMember.class);
            if(member == null){
                continue;
            }
            bbxCouponCode.setVipId(member.getMemberid());
            //只有贝贝熊的数据是这么处理的，优惠券rule字段存储着贝贝熊crm系统中的couponId
            String rule = coupon.getRule();
            BbxCouponRule couponRule = JSON.parseObject(rule, BbxCouponRule.class);
            bbxCouponCode.setCouponId((int) couponRule.getCouponid());
            bbxCouponCode.setCouponMoney(coupon.getAmount().doubleValue());
            bbxCouponCode.setCanUseBeginDate(DateUtil.getStringFromDate(coupon.getStartTime(),ApiConstant.DATE_FORMAT_yyyy_MM_dd) );
            bbxCouponCode.setCanUserEndDate(DateUtil.getStringFromDate(coupon.getEndTime(),ApiConstant.DATE_FORMAT_yyyy_MM_dd));
            bbxCouponCode.setStoreCode("");
            bbxCouponCode.setCreateTime(new Date());
            bbxCouponCode.setSynchronizeable(Boolean.FALSE);
            bbxCouponCode.setSynchSuccess(Boolean.FALSE);
            bbxCouponCode.setSynchronizedTime(null);
            bbxCouponCode.setPhone(vo.getUser());
            bbxCouponCode.setMainId(String.valueOf( member.getMid()));
            bbxCouponCode.setCampsignId(campsignId);
            bbxCouponCode.setItemId(itemId);
            bbxCouponCode.setCouponCodeId(couponCode.getId());
            bbxCouponCode.setChecked(false);
            list.add(bbxCouponCode);
        }

        //分页后插入数据库，防止一次插入过多数据
        int pageSize = 1000;
        List<List<BbxCouponCodeAdd>> partitionList = Lists.partition(list, pageSize);
        for (List<BbxCouponCodeAdd> item : partitionList) {
            bbxCouponCodeAddDao.batchInsert(item);
            
            for (BbxCouponCodeAdd bbxCouponCodeAdd : item) {
                //仅同步以活动发送出去的优惠券信息，短信任务发送的不进行同步
                if(bbxCouponCodeAdd.getCampsignId() != null){
                    this.campaignDetailService.updateCampaignMemberCouponId(bbxCouponCodeAdd.getCampsignId().intValue(),String.valueOf( bbxCouponCodeAdd.getItemId()),Integer.valueOf( bbxCouponCodeAdd.getMainId()), 0, bbxCouponCodeAdd.getCouponId());
                }
            }
        }
    }

    @Override
    @Transactional
    public void verifyCouponCode() {

        Query query = new Query();
        //查询所有未核销的数据，进行相应的核销操作
        Criteria payCriteria = new Criteria();
        payCriteria.orOperator(Criteria.where("checked").exists(false), Criteria.where("checked").is(false));

        List<Sort.Order> orders = new ArrayList<Sort.Order>();
        orders.add(new Sort.Order(Sort.Direction.DESC, "_id"));
        Sort sort = new Sort(orders);
        if (null != sort) {
            query.with(sort);
        }
        query.addCriteria(payCriteria);
        SpringDataPageable pageable = new SpringDataPageable();
        //排序
        pageable.setSort(sort);
        //查询出一共的条数
        long count = this.mongoTemplate.count(query, TBBXOrderPayDetail.class);
        int pageSize = 100;
        long pageCount = count/100;
        if(count % 100 >0){
            pageCount++;
        }

        for (int i = 0; i <pageCount; i++) {
            //开始页
            pageable.setPagenumber(i*pageSize);
            //每页条数
            pageable.setPagesize(pageSize);

            List<TBBXOrderPayDetail> payDetails = this.mongoTemplate.find(query.with(pageable), TBBXOrderPayDetail.class);

            for (TBBXOrderPayDetail detail: payDetails) {
                TBBXTransactionHeadAndDetail head = this.mongoTemplate.findOne(new Query(Criteria.where("orderid").is(detail.getOrderid())), TBBXTransactionHeadAndDetail.class);
                if (head != null) {
                    long memberId = head.getMemberid();
                    long couponId = detail.getCouponid();
                    long couponCodeId = 0;
                    String phone = "";

                    //根据贝贝熊couponId会员电话号码查询相应的优惠码
                    BbxCouponCodeAdd bbxCouponCodeAddParam = new BbxCouponCodeAdd();
                    bbxCouponCodeAddParam.setVipId((int) memberId);
                    bbxCouponCodeAddParam.setCouponId((int) couponId);
                    bbxCouponCodeAddParam.setChecked(false);
                    List<BbxCouponCodeAdd> bbxCouponCodeAdds = this.bbxCouponCodeAddDao.selectList(bbxCouponCodeAddParam);

                    if (CollectionUtils.isNotEmpty(bbxCouponCodeAdds)) {
                        BbxCouponCodeAdd bbxCouponCodeAdd = bbxCouponCodeAdds.get(0);
                        couponCodeId = bbxCouponCodeAdd.getCouponCodeId();
                        phone = bbxCouponCodeAdd.getPhone();
                        
                        MaterialCouponCode code = new MaterialCouponCode();
                       
                        code.setId(couponCodeId);
                        code.setUser(phone);
                        code.setReleaseStatus(MaterialCouponCodeReleaseStatusEnum.RECEIVED.getCode());
                        code.setVerifyStatus(MaterialCouponCodeVerifyStatusEnum.VERIFIED.getCode());
                        code.setVerifyTime(DateUtil.getDateFromString(head.getSaletime(),"yyyy-MM-dd HH:mm:ss") );
                        this.couponCodeDao.updateByIdAndStatus(code);
                        
                      //仅同步以活动发送出去的优惠券信息，短信任务发送的不进行同步
                        if(bbxCouponCodeAdd.getCampsignId() != null){
                            this.campaignDetailService.updateCampaignMemberCouponId(bbxCouponCodeAdd.getCampsignId().intValue(),String.valueOf( bbxCouponCodeAdd.getItemId()),Integer.valueOf( bbxCouponCodeAdd.getMainId()), 1, bbxCouponCodeAdd.getCouponId());
                        }
                    }
                }

                //不管是否已经核销完毕都要将checked置为true;
                mongoTemplate.updateFirst(new Query(new Criteria("id").is(detail.getId())),
                        new Update().set("checked", true), TBBXOrderPayDetail.class);
            }
        }
    }

    /**
     * 获取优惠码的详细信息
     *
     * @param couponCodeId
     * @return
     */
    private MaterialCouponCode getCouponIdByCodeId(Long couponCodeId) {
        MaterialCouponCode materialCouponCode = null;
        if (couponCodeId != null) {
            MaterialCouponCode code = new MaterialCouponCode();
            code.setId(couponCodeId);
            List<MaterialCouponCode> resultList = materialCouponCodeDao.selectList(code);
            if (CollectionUtils.isNotEmpty(resultList)) {
                materialCouponCode = resultList.get(0);
            }
        }
        return materialCouponCode;
    }

    /**
     * 获取优惠券的详细信息
     *
     * @param couponId
     * @return
     */
    private MaterialCoupon getCouponById(Long couponId) {
        MaterialCoupon materialCoupon = null;
        if (couponId != null) {
            materialCoupon = new MaterialCoupon();
            materialCoupon.setId(couponId);
            List<MaterialCoupon> materialCoupons = materialCouponDao.selectList(materialCoupon);
            if (CollectionUtils.isNotEmpty(materialCoupons)) {
                materialCoupon = materialCoupons.get(0);
            }
        }
        return materialCoupon;
    }

}
