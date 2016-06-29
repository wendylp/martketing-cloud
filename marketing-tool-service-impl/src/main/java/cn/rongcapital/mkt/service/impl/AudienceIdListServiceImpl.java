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
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.AudienceListDao;
import cn.rongcapital.mkt.dao.AudienceListPartyMapDao;
import cn.rongcapital.mkt.dao.SegmentationHeadDao;

import cn.rongcapital.mkt.po.AudienceList;
import cn.rongcapital.mkt.po.AudienceListPartyMap;
import cn.rongcapital.mkt.po.SegmentationHead;
import cn.rongcapital.mkt.po.mongodb.Segment;
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
    
    
    @Autowired
    private MongoTemplate mongoTemplate;


    @Override
    @ReadWrite(type = ReadWriteType.READ)
    public BaseOutput audienceIdList(String userToken,String audience_ids,String audience_type) {
        
        List<Integer> audienceList=new ArrayList<Integer>();   
        
        List<String> audPartyIdList =new ArrayList<String>();
        
        List<Segment> segmentList=new ArrayList<Segment>();
        
        String[] aud_idList=audience_ids.split(",");
        
        for(String aud_id : aud_idList){                
            audienceList.add(Integer.parseInt(aud_id));                 
        }
        
        
        BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
                        ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO, null);
        
        //细分人群
        if(audience_type.equals("0")){
            
            if(aud_idList[0].equals("0")){
                
                segmentList=mongoTemplate.findAll(Segment.class,"segment");
                
            }else{
                                
                segmentList=mongoTemplate.find(new Query(Criteria.where("segmentationHeadId").in(audienceList)), Segment.class);
                
            }
            
            for(Segment segment : segmentList){
                
                audPartyIdList.add(segment.getMappingKeyid());
            }
                        
        }
        
        
      //固定人群
        if(audience_type.equals("1")){
        
            
            if(aud_idList[0].equals("0")){
                
                audPartyIdList = audienceListPartyMapDao.selectPartyIdList(null);
                
            }else{
                                        
                audPartyIdList = audienceListPartyMapDao.selectPartyIdList(audienceList);
                
            }
        }
        
        
        
        
        result.getData().add(audPartyIdList);
       
        result.setTotal(audPartyIdList.size());
       
        return result;
    }



}
