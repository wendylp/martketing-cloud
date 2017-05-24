/*************************************************
 * @功能及特点的描述简述: 定时同步贝贝熊优惠券数据
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：营销云系统
 * @author: 谢小良
 * @version: 版本v1.7
 * @date(创建、开发日期)：2017-4-13
 * @date(最后修改日期)：2017-4-13
 * @复审人：
 *************************************************/
package cn.rongcapital.mkt.job.bbx.Service.impl;

import cn.rongcapital.mkt.bbx.po.BbxCouponCodeAdd;
import cn.rongcapital.mkt.dao.bbx.BbxCouponCodeAddDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.webservice.BBXCrmWSUtils;
import cn.rongcapital.mkt.webservice.UpdateCouponResult;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class BbxSynchronizeCouponServiceImpl implements TaskService {

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private BbxCouponCodeAddDao bbxCouponCodeAddDao;

    @Override
    public void task(Integer taskId) {
        synchronizeCoupon();
    }

    @Transactional
    public void synchronizeCoupon() {
        BbxCouponCodeAdd param = new BbxCouponCodeAdd();
        param.setSynchronizeable(Boolean.FALSE);
        int totalCount = this.bbxCouponCodeAddDao.selectListCount(param);
        int pageSize = 100;
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
                //取上一次最后一条数据的ID作为下一次查询的起始位置
                param.setId(bbxCouponCodeAdds.get(bbxCouponCodeAdds.size()-1).getId());
            }
            bbxCouponCodeAdds = this.bbxCouponCodeAddDao.selectListByMinId(param);
            for (BbxCouponCodeAdd item : bbxCouponCodeAdds) {
                sendMessageToBBX(item);
            }
        }
    }

    /**
     * 循环重试发送消息
     * @param item
     */
    private void sendMessageToBBX(BbxCouponCodeAdd item) {
        try {
            logger.info("Send message to bbx crm ,content is {}", JSON.toJSON(item));
            UpdateCouponResult result = BBXCrmWSUtils.UpdateVipCoupon(item.getVipId(), item.getCouponId(), item.getActionId(), item.getCouponMoney(), item.getCanUseBeginDate(), item.getCanUserEndDate(), item.getStoreCode());
            result.setSuccess(Boolean.TRUE);
            if(result.getSuccess()){
                item.setSynchronizeable(Boolean.TRUE);
                item.setSynchSuccess(Boolean.TRUE);
                item.setSynchronizedTime(new Date());
            }else{
                item.setSynchronizeable(Boolean.TRUE);
                item.setSynchSuccess(Boolean.FALSE);
                item.setSynchronizedTime(new Date());
                item.setErrorMsg(result.getMsg());
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.info("Send coupone code is error,message is :{}",e.getMessage());
            //将同步webservice调用时，异常的情况记录，以便于重新发起同步操作
            item.setSynchronizeable(Boolean.FALSE);
            item.setSynchSuccess(Boolean.FALSE);
            item.setErrorMsg(e.getMessage());
        }finally {
            //不管是否成功，都要记录结果
            this.bbxCouponCodeAddDao.updateById(item);
        }
    }


}
