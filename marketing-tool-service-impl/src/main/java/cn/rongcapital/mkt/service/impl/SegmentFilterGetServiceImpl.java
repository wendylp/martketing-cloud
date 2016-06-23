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
        
        // 拼mongodb 查询条件
        Query query = new Query();
        Criteria criteria = new Criteria();
        Criteria[] criterialist = new Criteria[conditions.size()];

        for (int i = 0; i < conditions.size(); i++) {
            
            Criteria criteriaand = new Criteria();
            //根据getExclude()拼条件
            
            criteriaand.andOperator(Criteria.where("tagList.tagId")
                            .is(new String(conditions.get(i).getTag_id())).and("tagId")
                            .gte(conditions.get(i).getTag_id()));
            criterialist[i] = criteriaand;
        }

        criteria.orOperator(criterialist);
        query.addCriteria(criteria);
        mongoTemplate.find(query, DataParty.class);



        BaseOutput baseOutput = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
                        ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO, null);
        // List<CustomTagWithName> data = customTagMapDao.getTagUseHeadId(Long
        // .valueOf(segmentHeadId));
        // if (data != null) {
        // List<Object> result = new ArrayList<Object>();
        // SegmentTagGetOut out = null;
        // for (CustomTagWithName tags : data) {
        // out = new SegmentTagGetOut();
        // out.setTagId(tags.getId());
        // out.setTagName(tags.getName());
        // result.add(out);
        // }
        // baseOutput.setData(result);
        // baseOutput.setTotal(result.size());
        // }
        return baseOutput;
    }

}
