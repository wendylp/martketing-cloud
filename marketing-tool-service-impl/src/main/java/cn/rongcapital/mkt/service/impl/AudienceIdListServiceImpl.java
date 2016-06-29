/*************************************************
 * @功能简述: AudienceListService实现类
 * @see: MkyApi
 * @author: 杨玉麟
 * @version: 1.0
 * @date: 2016/6/6
 *************************************************/


package cn.rongcapital.mkt.service.impl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.AudienceListDao;
import cn.rongcapital.mkt.dao.AudienceListPartyMapDao;
import cn.rongcapital.mkt.dao.SegmentationHeadDao;

import cn.rongcapital.mkt.po.AudienceList;
import cn.rongcapital.mkt.po.AudienceListPartyMap;
import cn.rongcapital.mkt.po.SegmentationHead;
import cn.rongcapital.mkt.service.AudienceIdListService;

import cn.rongcapital.mkt.vo.BaseOutput;
import heracles.data.common.annotation.ReadWrite;
import heracles.data.common.util.ReadWriteType;

@Service
public class AudienceIdListServiceImpl implements AudienceIdListService {
    @Autowired
    AudienceListDao audienceListDao;

    @Autowired
    AudienceListPartyMapDao audienceListPartyMapDao;


    @Override
    @ReadWrite(type = ReadWriteType.READ)
    public BaseOutput audienceIdList(String userToken,String audience_ids,String audience_type) {
        
        String[] aud_idList=audience_ids.split(",");
        
        BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
                        ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO, null);
        
        List<String> audienceList=new ArrayList<String>();   
        
        List<String> audPartyIdList =new ArrayList<String>();
        
        if(aud_idList[0].equals("0")){
            
            audPartyIdList = audienceListPartyMapDao.selectPartyIdList(audienceList);
            
        }else{
                 
            
            for(String aud_id : aud_idList){                
                audienceList.add(aud_id);                 
            }
                                    
            audPartyIdList = audienceListPartyMapDao.selectPartyIdList(audienceList);
            
        }
        
        result.getData().add(audPartyIdList);
       
        result.setTotal(result.getData().size());
       
        return result;
    }



}
