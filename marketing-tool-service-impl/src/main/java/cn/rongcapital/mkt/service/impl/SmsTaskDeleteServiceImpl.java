/**
 * 
 */
package cn.rongcapital.mkt.service.impl;

import java.util.List;

import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.SmsTaskBodyDao;
import cn.rongcapital.mkt.dao.SmsTaskHeadDao;
import cn.rongcapital.mkt.po.SmsTaskBody;
import cn.rongcapital.mkt.po.SmsTaskHead;
import cn.rongcapital.mkt.service.SmsTaskDeleteService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.SmsTaskDeleteIn;


@Service
public class SmsTaskDeleteServiceImpl implements SmsTaskDeleteService {
    
    @Autowired
    SmsTaskHeadDao smsTaskHeadDao;
    
    @Autowired
    SmsTaskBodyDao smsTaskBodyDao;

    /**
     * 接口：mkt.sms.task.delete
     * 根据任务id逻辑删除记录
     * 
     * @param body
     * @param securityContext
     * @return
     * @author shuiyangyang
     * @Date 2016.10.19
     */
    @Override
    public BaseOutput smsTaskDelete(SmsTaskDeleteIn body, SecurityContext securityContext) {
        BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
            ApiConstant.INT_ZERO, null);
        
        Long id = body.getId();
        
        SmsTaskHead smsTaskHead =new SmsTaskHead();
        smsTaskHead.setId(id);
        smsTaskHead.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
        
        List<SmsTaskHead> smsTaskHeadLists = smsTaskHeadDao.selectList(smsTaskHead);
        // 如果数据存在，则删除
        if(smsTaskHeadLists != null && smsTaskHeadLists.size() > 0) {
            smsTaskHead.setStatus(ApiConstant.TABLE_DATA_STATUS_INVALID);
            // 逻辑删除，并返回逻辑删除的数量
            int num = smsTaskHeadDao.updateById(smsTaskHead);
            
            if(num > 0) {
                result.setTotal(num);
                
                // 继续逻辑删除smsTaskBody中的数据
                SmsTaskBody smsTaskBody = new SmsTaskBody();
                smsTaskBody.setSmsTaskHeadId(id);
                smsTaskBody.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
                
                List<SmsTaskBody> smsTaskBodyLists = smsTaskBodyDao.selectList(smsTaskBody);
                if(smsTaskBodyLists != null && smsTaskBodyLists.size() > 0) {
                    for(SmsTaskBody smsTaskBodyList : smsTaskBodyLists) {
                        smsTaskBodyList.setStatus(ApiConstant.TABLE_DATA_STATUS_INVALID);
                        smsTaskBodyDao.updateById(smsTaskBodyList);
                    }
                }
            }
        }        
        return result;
    }

}
