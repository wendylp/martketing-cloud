/*************************************************
 * @功能简述: 获取细分关联的标签
 * @see MktApi：
 * @author: 朱学龙
 * @version: 1.0 @date：2016-06-07
 *************************************************/
package cn.rongcapital.mkt.service.impl;

import heracles.data.common.annotation.ReadWrite;
import heracles.data.common.util.ReadWriteType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.SecurityContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.CustomTagMapDao;
import cn.rongcapital.mkt.po.CustomTagWithName;
import cn.rongcapital.mkt.po.mongodb.DataParty;
import cn.rongcapital.mkt.po.mongodb.Tag;
import cn.rongcapital.mkt.service.SegmentFilterGetService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.SegmentFilterCondition;
import cn.rongcapital.mkt.vo.in.SegmentFilterCountIn;
import cn.rongcapital.mkt.vo.out.SegmentTagGetOut;

@Service
public class SegmentFilterGetServiceImpl implements SegmentFilterGetService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MongoTemplate mongoTemplate;

    @ReadWrite(type = ReadWriteType.READ)
    @Override
    public BaseOutput getSegmentFilterCount(SegmentFilterCountIn body, SecurityContext securityContext) {

               
        List<SegmentFilterCondition> conditions=body.getConditions();
        
        int count=conditions.size();
        
        BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
                        ApiErrorCode.SUCCESS.getMsg(),
                        ApiConstant.INT_ZERO,null);
        
        result.setTotal(count);
        
        
        for(int i=0;i<count;i++){
            
            Map<String,Object> map = new HashMap<String,Object>();
            
            map.put("tag_id",conditions.get(i).getTag_id());
            map.put("tag_name",conditions.get(i).getTag_name());
            
            String tag_count=getPeopleCount(i,conditions);
            
            map.put("tag_count",tag_count);
                        
            result.getData().add(map);            
            
        }
        
      return result;
        
//        
//        
//        
//        if(count==1){
//            
//            map.put("tag_id",conditions.get(0).getTag_id());
//            map.put("tag_name",conditions.get(0).getTag_name());
//            String tag_count=getPeopleCount(criterialist);
//            map.put("tag_count",tag_count);
//            
//            result.getData().add(map);
//        }
//                
//        
//        if(count==2){
//            
//            map.put("tag_id",conditions.get(0).getTag_id());
//            map.put("tag_name",conditions.get(0).getTag_name());
//            String tag_count=getPeopleCount(criterialist);
//            map.put("tag_count",tag_count);
//            
//            result.getData().add(map);
//        }
//        
//        
//        
//        
//        
//        
//        // 拼mongodb 查询条件
//        Query query = new Query();
//        Criteria criteria = new Criteria();
//        Criteria[] criterialist = new Criteria[conditions.size()];
//        int count=conditions.size();
//        logger.debug("conditions count="+count);
//        
//        List<DataParty> restList=new ArrayList<DataParty>(count);
//        
//        
//        if(count==1){
//            
//            
//            for (int i = 0; i < count; i++) {
//                
//                Criteria criteriaAnd = new Criteria();
//                //根据getExclude()拼条件
//                
//                if(conditions.get(i).getExclude().equals("0")){
//                    
//                    criteriaAnd.andOperator(Criteria.where("tagList.tagId").is(new String(conditions.get(i).getTag_id())));
//                                    
//                }else{
//                    
//                    criteriaAnd.andOperator(Criteria.where("tagList.tagId").ne(new String(conditions.get(i).getTag_id())));
//                }
//                
//                criterialist[i] = criteriaAnd;
//            }
//
//            criteria.orOperator(criterialist);
//            query.addCriteria(criteria);
//            restList =mongoTemplate.find(query, DataParty.class);
//                    
//            
//        }else if(count==2){
//            
//        
//            
//        }else if(count==3){
//            
//        
//            
//            
//        }else{
//            
//            logger.debug("conditions count 非法");
//        }
//        
//
//        for (int i = 0; i < count; i++) {
//            
//            Criteria criteriaAnd = new Criteria();
//            //根据getExclude()拼条件
//            
//            if(conditions.get(i).getExclude().equals("0")){
//                
//                criteriaAnd.andOperator(Criteria.where("tagList.tagId").is(new String(conditions.get(i).getTag_id())));
//                                
//            }else{
//                
//                criteriaAnd.andOperator(Criteria.where("tagList.tagId").ne(new String(conditions.get(i).getTag_id())));
//            }
//            
//            criterialist[i] = criteriaAnd;
//        }
//
//        criteria.orOperator(criterialist);
//        query.addCriteria(criteria);
//        List<DataParty> restList =mongoTemplate.find(query, DataParty.class);
        
//        Map<String,Object> map = new HashMap<String,Object>();
//        
//        map.put("tag_id",tagid);
//        
//        int count=restList.size();          
//        map.put("tag_count", count);            
//        
//        List<Tag> tagList = restList.get(0).getTagList();
//        for(Tag tag : tagList){
//            
//            if(tagid.equals(tag.getTagId())){
//                map.put("tag_name", tag.getTagName());
//                break;
//            }
//        }
//        
//        result.getData().add(map);
        
//    }
    
    
    
    }
    
    
//    private Criteria[] buildConditions(List<SegmentFilterCondition> conditions){
//        
//        Criteria[] criterialist = new Criteria[conditions.size()];
//        int count=conditions.size();
//        
//        for (int i = 0; i < count; i++) {
//            
//            Criteria criteriaAnd = new Criteria();
//            
//            //根据getExclude()拼条件            
//            if(conditions.get(i).getExclude().equals("0")){
//                
//                criteriaAnd.andOperator(Criteria.where("tagList.tagId").is(new String(conditions.get(i).getTag_id())));
//                                
//            }else{
//                
//                criteriaAnd.andOperator(Criteria.where("tagList.tagId").ne(new String(conditions.get(i).getTag_id())));
//            }
//            
//            criterialist[i] = criteriaAnd;
//        }
//        
//        return criterialist;
//              
//        
//    }
    
    
    private String getPeopleCount(int idx,List<SegmentFilterCondition> conditions){
        
        Query query = new Query();
        Criteria criteria = new Criteria(); 
        
        Criteria[] criterialist = new Criteria[idx+1];
        
        
        for(int i=0;i<idx+1;i++){
            
            Criteria criteriaAnd = new Criteria();
            
            //根据getExclude()拼条件            
            if(conditions.get(i).getExclude().equals("0")){
                
                criteriaAnd.andOperator(Criteria.where("tagList.tagId").is(new String(conditions.get(i).getTag_id())));
                                
            }else{
                
                criteriaAnd.andOperator(Criteria.where("tagList.tagId").ne(new String(conditions.get(i).getTag_id())));
            }
            
            criterialist[i] = criteriaAnd;
                        
        }
        
        criteria.andOperator(criterialist);
        query.addCriteria(criteria);
        long tag_count=mongoTemplate.count(query, DataParty.class);
        return  Long.toString(tag_count);     
        
    }


}
