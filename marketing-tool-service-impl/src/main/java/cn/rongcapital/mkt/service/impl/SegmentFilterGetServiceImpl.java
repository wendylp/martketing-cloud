/*************************************************
 * @功能简述: 获取细分标签漏斗计算
 * @see MktApi：
 * @author: xukun
 * @version: 1.0 @date：2016-06-28
 *************************************************/
package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.po.mongodb.DataParty;
import cn.rongcapital.mkt.po.mongodb.Segment;
import cn.rongcapital.mkt.service.SegmentFilterGetService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.SegmentCountFilterIn;
import cn.rongcapital.mkt.vo.in.SegmentFilterCondition;
import cn.rongcapital.mkt.vo.in.SegmentFilterCountIn;
import cn.rongcapital.mkt.vo.out.SegmentAreaCountOut;
import cn.rongcapital.mkt.vo.out.SegmentGenderCountOut;
import cn.rongcapital.mkt.vo.out.SegmentProvinceCountOut;
import cn.rongcapital.mkt.vo.out.SegmentReceiveCountOut;
import heracles.data.common.annotation.ReadWrite;
import heracles.data.common.util.ReadWriteType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.ws.rs.core.SecurityContext;
import java.util.*;


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
        BaseOutput result = newSuccessBaseOutput();
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
    }

    @Override
    public BaseOutput segmentGenderCountList(SegmentCountFilterIn input) {
        BaseOutput result = newSuccessBaseOutput();
        Criteria midCriteria = getDataPartyCriteria(input.getSegmentHeadIds());
        if (midCriteria == null) {
            return result;
        }
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(midCriteria),
                Aggregation.group("gender").count().as("populationCount"),
                Aggregation.project("populationCount", "sex"));
        AggregationResults<SegmentGenderCountOut> aggregationResults =  mongoTemplate.aggregate(
                aggregation, DataParty.class, SegmentGenderCountOut.class);
        setBaseOut(result, aggregationResults.getMappedResults());
        return result;
    }

    @Override
    public BaseOutput segmentProvinceCountList(SegmentCountFilterIn input) {
        BaseOutput result = newSuccessBaseOutput();
        Criteria midCriteria = getDataPartyCriteria(input.getSegmentHeadIds());
        if (midCriteria == null) {
            return result;
        }
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(midCriteria),
                Aggregation.group("provice").count().as("populationCount"),
                Aggregation.project("populationCount", "provice"));
        AggregationResults<SegmentProvinceCountOut> aggregationResults =  mongoTemplate.aggregate(
                aggregation, DataParty.class, SegmentProvinceCountOut.class);
        List<SegmentProvinceCountOut> provinceCountOutList = aggregationResults.getMappedResults();

        Aggregation areaAggregation = Aggregation.newAggregation(
                Aggregation.match(midCriteria),
                Aggregation.group("citizenship").count().as("populationCount"),
                Aggregation.project("populationCount", "citizenship"));
        AggregationResults<SegmentAreaCountOut> areaAggregationResults =  mongoTemplate.aggregate(
                areaAggregation, DataParty.class, SegmentAreaCountOut.class);
        List<SegmentAreaCountOut> areaCountOutList = areaAggregationResults.getMappedResults();

        // merge area and province result
        int chinaCount = 0;
        int foreignCount = 0;
        if (!CollectionUtils.isEmpty(areaCountOutList)) {
            for (SegmentAreaCountOut tempSegmentAreaCountOut : areaCountOutList) {
                String citizenship = tempSegmentAreaCountOut.getCitizenship();
                if (citizenship != null && ApiConstant.NATIONALITY_CHINA.equals(citizenship.trim())) {
                    chinaCount++;
                } else {
                    foreignCount++;
                }
            }
        }

        if (CollectionUtils.isEmpty(provinceCountOutList)) {
            provinceCountOutList = new ArrayList<>();
        }
        provinceCountOutList.add(0, new SegmentProvinceCountOut(ApiConstant.NATIONALITY_FOREIGN, foreignCount));
        provinceCountOutList.add(0, new SegmentProvinceCountOut(ApiConstant.NATIONALITY_CHINA, chinaCount));
        setBaseOut(result, provinceCountOutList);
        return result;
    }

    @Override
    public BaseOutput segmentReceiveCountList(SegmentCountFilterIn input) {
        BaseOutput result = newSuccessBaseOutput();
        Criteria midCriteria = getDataPartyCriteria(input.getSegmentHeadIds());
        if (midCriteria == null) {
            return result;
        }
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(midCriteria),
                Aggregation.group("receiveCount").count().as("populationCount"),
                Aggregation.project("populationCount", "receiveCount"));
        AggregationResults<SegmentReceiveCountOut> aggregationResults =  mongoTemplate.aggregate(
                aggregation, DataParty.class, SegmentReceiveCountOut.class);
        setBaseOut(result, aggregationResults.getMappedResults());
        return result;
    }

    private Criteria getDataPartyCriteria(List<Integer> segmentHeadIds) {
        if (CollectionUtils.isEmpty(segmentHeadIds)) {
            return null;
        }
        Query query = new Query(Criteria.where("segmentationHeadId").in(segmentHeadIds));
        List<Segment> segmentList = mongoTemplate.find(query, Segment.class);
        if (CollectionUtils.isEmpty(segmentList)) {
            return null;
        }

        Criteria midCriteria = Criteria.where("mid");
        Set<Integer> dataPartyIdSet = new HashSet<>();
        for (Segment segment : segmentList) {
            dataPartyIdSet.add(segment.getDataId());
        }
        midCriteria.in(dataPartyIdSet);
        return midCriteria;
    }

    private String getPeopleCount(int idx, List<SegmentFilterCondition> conditions){
        Query query = new Query();
        Criteria[] criterialist = new Criteria[idx+1];
        for(int i=0;i<idx+1;i++) {
        	int tagId = Integer.parseInt(conditions.get(i).getTag_id());
        	int tagGroupId = conditions.get(i).getTag_group_id();
            //根据getExclude()拼条件            
            if(conditions.get(i).getExclude().equals("1")) {
            	Criteria criteriaCond = null;
            	if(tagId == 0) {//不限
            		criteriaCond = Criteria.where("tagList.tagGroupId").ne(tagGroupId);
            	} else {
            		criteriaCond=Criteria.where("tagList.tagId").ne(tagId);
            	}
                criterialist[i]=criteriaCond;
            } else {
            	Criteria criteriaCond = null;
            	if(tagId == 0) {//不限
            		criteriaCond = Criteria.where("tagList.tagGroupId").is(tagGroupId);
            	} else {
            		criteriaCond=Criteria.where("tagList.tagId").is(tagId);
            	}
                criterialist[i]=criteriaCond;
            }
        }
        query.addCriteria(new Criteria().andOperator(criterialist));
        long tag_count=mongoTemplate.count(query, DataParty.class);
        return  Long.toString(tag_count);     
    }

    private BaseOutput newSuccessBaseOutput() {
        return new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
                                     ApiErrorCode.SUCCESS.getMsg(),
                                     ApiConstant.INT_ZERO,null);
    }

    private <O> void setBaseOut(BaseOutput out, List<O> dataList) {
        if (CollectionUtils.isEmpty(dataList)) {
            return;
        }

        out.setTotal(dataList.size());
        out.getData().addAll(dataList);
    }


}
