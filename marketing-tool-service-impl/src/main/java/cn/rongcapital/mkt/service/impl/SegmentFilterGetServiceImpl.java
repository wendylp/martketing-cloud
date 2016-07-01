/*************************************************
 * @功能简述: 获取细分标签漏斗计算
 * @see MktApi：
 * @author: xukun
 * @version: 1.0 @date：2016-06-28
 *************************************************/
package cn.rongcapital.mkt.service.impl;

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

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.po.mongodb.DataParty;
import cn.rongcapital.mkt.service.SegmentFilterGetService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.SegmentFilterCondition;
import cn.rongcapital.mkt.vo.in.SegmentFilterCountIn;
import heracles.data.common.annotation.ReadWrite;
import heracles.data.common.util.ReadWriteType;


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
            //map.put("tag_name",conditions.get(i).getTag_name());
            map.put("tag_name","tester");
            
            String tag_count=getPeopleCount(i,conditions);
            
            map.put("tag_count",tag_count);
                        
            result.getData().add(map);            
            
        }
        
      return result;
    }
        
    
    
    private String getPeopleCount(int idx,List<SegmentFilterCondition> conditions){
        
        Query query = new Query();
         
                
        Criteria[] criterialist = new Criteria[idx+1];
        
        
        for(int i=0;i<idx+1;i++){
            
            Criteria criteriaCond=Criteria.where("tagList.tagId").is(new String(conditions.get(i).getTag_id()));
            
            
            //根据getExclude()拼条件            
            if(conditions.get(i).getExclude().equals("1")){                                
                
                criterialist[i]=criteriaCond.not();
                
                                
            }else{
                                
                criterialist[i]=criteriaCond;
            }
                                    
        }
               
        query.addCriteria(new Criteria().andOperator(criterialist));
        
        long tag_count=mongoTemplate.count(query, DataParty.class);
        
        return  Long.toString(tag_count);     
        
    }


}
