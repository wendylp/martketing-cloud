/*************************************************
 * @功能简述: 根据系统最末级标签组ID查询出标签内容列表的业务类实现
 * @see MktApi：
 * @author: xuning
 * @version: 1.0
 * @date：2016-06-07
 *************************************************/
package cn.rongcapital.mkt.service.impl;

import heracles.data.common.annotation.ReadWrite;
import heracles.data.common.util.ReadWriteType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.TagDao;
import cn.rongcapital.mkt.po.mongodb.DataParty;
import cn.rongcapital.mkt.po.mongodb.Tag;
import cn.rongcapital.mkt.service.SegmentTagnameTagCountService;
import cn.rongcapital.mkt.vo.BaseOutput;

@Service
public class SegmentTagnameTagCountServiceImpl implements SegmentTagnameTagCountService{
	
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private MongoTemplate mongoTemplate;
	
	@Override
	@ReadWrite(type=ReadWriteType.READ)
	public BaseOutput getTagCountById(String tagIds) {
	    
	    String ids[]=tagIds.split(",");
	    
	    
	    
	    BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
	                    ApiErrorCode.SUCCESS.getMsg(),
	                    ApiConstant.INT_ZERO,null);
	    
	    result.setTotal(ids.length);
	    
	    for(String tagid : ids){
	        
	        List<DataParty> restList =mongoTemplate.find(new Query(Criteria.where("tagList.tagId").is(tagid)),DataParty.class);
	        
	        Map<String,Object> map = new HashMap<String,Object>();
	        
	        map.put("tag_id",tagid);
	        
	        int count=restList.size();	        
	        map.put("tag_count", count);	        
	        
	        List<Tag> tagList = restList.get(0).getTagList();
	        for(Tag tag : tagList){
	            
	            if(tagid.equals(tag.getTagId())){
	                map.put("tag_name", tag.getTagName());
	                break;
	            }
	        }
                    
            
            result.getData().add(map);
	        
	    }

		return result;
	}
}
