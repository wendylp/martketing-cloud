package cn.rongcapital.mkt.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.util.GenerateUUid;
import cn.rongcapital.mkt.dao.CustomTagValueCountDao;
import cn.rongcapital.mkt.po.CustomTagValueCount;
import cn.rongcapital.mkt.po.mongodb.CustomTag;
import cn.rongcapital.mkt.service.CustomTagActionService;
import cn.rongcapital.mkt.service.SegmentAnalysisCustomService;
import cn.rongcapital.mkt.service.SegmentManageCalService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.out.SegmentAnalysisCustomVO;
import cn.rongcapital.mkt.vo.out.SegmentCustomAnalysisCountData;
import cn.rongcapital.mkt.vo.out.SegmentCustomAnalysisDataOut;
/**
 * Created by ljk on 1/16/17.
 */
@Service
public class SegmentAnalysisCustomServiceImpl implements SegmentAnalysisCustomService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private SegmentManageCalService segmentManageCalService;

	@Autowired
	private CustomTagActionService customTagActionService;
	
	@Autowired
	private CustomTagValueCountDao customTagValueCountDao;
	
	public static final  Integer POOL_INDEX = 2;
	
	public static final String TAG_COVER_ID_STR="tagcoverid:";
	
	public static final String SEGMENT_COVER_ID_STR="segmentcoverid:";
	
    private ThreadLocal<String> uuid=new ThreadLocal<String>();    
	
	/**
	 * 显示top标签列表人次(自定义标签)
	 */
	@Override
	public BaseOutput getSegmentAnalysisTopCustomList(Integer topType) {
		
		BaseOutput baseOutput = new BaseOutput(ApiErrorCode.DB_ERROR.getCode(),ApiErrorCode.DB_ERROR.getMsg(), ApiConstant.INT_ZERO,null);

		CustomTagValueCount customTagValueCount = new CustomTagValueCount();
		if(topType == 0){
			customTagValueCount.setPageSize(null);
			customTagValueCount.setStartIndex(null);
		}else if(topType == 1){
			customTagValueCount.setPageSize(25);
			customTagValueCount.setStartIndex(0);
		}else if(topType == 2){
			customTagValueCount.setPageSize(50);
			customTagValueCount.setStartIndex(0);
		}else if(topType == 3){
			customTagValueCount.setPageSize(100);
			customTagValueCount.setStartIndex(0);
		}
		//排序
		customTagValueCount.setOrderField("cover_frequency");
		customTagValueCount.setOrderFieldType(ApiConstant.SORT_DESC);
		//查询出自定义标签
		List<CustomTagValueCount> customTagValueCountList = customTagValueCountDao.selectList(customTagValueCount);
		
		List<SegmentAnalysisCustomVO> customList = new ArrayList<SegmentAnalysisCustomVO>();
		SegmentAnalysisCustomVO segmentAnalysisCustomVO;
		
		for(CustomTagValueCount customTagValueCountTemp : customTagValueCountList){
			
			segmentAnalysisCustomVO = new SegmentAnalysisCustomVO();
			segmentAnalysisCustomVO.setTagId(customTagValueCountTemp.getCustomTagId());
			segmentAnalysisCustomVO.setTagName(customTagValueCountTemp.getCustomTagName());
			segmentAnalysisCustomVO.setCoverCount(customTagValueCountTemp.getCoverFrequency().intValue());
			customList.add(segmentAnalysisCustomVO);
			
		}
		
		baseOutput.setTotal(customList.size());
		baseOutput.getData().addAll(customList);
		
        baseOutput.setCode(ApiErrorCode.SUCCESS.getCode());
        baseOutput.setMsg(ApiErrorCode.SUCCESS.getMsg());
		
		return baseOutput;
	}

	/**
	 * 细分分析(自定义标签)
	 */
	@Override
	public BaseOutput getSegmentCustomAnalysis(String categoryId, Integer headId) {
		BaseOutput baseOutput = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO,null);
		
		logger.info("========================segment audienct analysis start ,categoryId:{},segmentHeadId:{}",categoryId,headId);
		
		SegmentCustomAnalysisDataOut segmentCustomAnalysisDataOut = new SegmentCustomAnalysisDataOut();
		
		//1.设置redis 临时key
	    uuid.set(GenerateUUid.generateShortUuid());
		
	    //2.为了保持和系统标签返回格式一致,设置默认显示饼图
		logger.info("===================================segment custom analysis ,show type :{}",ApiConstant.SEGMENT_SHOW_MAP);
		segmentCustomAnalysisDataOut.setShowType(ApiConstant.SEGMENT_SHOW_MAP);
	    
		//3.根据分类ID查询所属标签ID
		List<CustomTag> CustomTagList = customTagActionService.findCustomTagsByCategoryId(categoryId);
		
		//4.计算redis中tagcoverid 与 segmentcoverId 交集获取标签人数
		List<SegmentCustomAnalysisCountData> segmentCustomAnalysisCountDataList = new ArrayList<SegmentCustomAnalysisCountData>();
		
		SegmentCustomAnalysisCountData segmentCustomAnalysisCountData;
		
		if(CustomTagList != null && CustomTagList.size() > 0){
			
			for(CustomTag customTag : CustomTagList){
				
				segmentManageCalService.sinterstore(POOL_INDEX, uuid.get() ,TAG_COVER_ID_STR+customTag.getCustomTagId(), SEGMENT_COVER_ID_STR+headId);
				Long count = segmentManageCalService.scard(POOL_INDEX, uuid.get());
				
				segmentCustomAnalysisCountData = new SegmentCustomAnalysisCountData();
				segmentCustomAnalysisCountData.setTagId(customTag.getCustomTagId());
				segmentCustomAnalysisCountData.setTagName(customTag.getCustomTagName());
				
				//标签值覆盖人数
				segmentCustomAnalysisCountData.setTagCount(count.intValue());
				segmentCustomAnalysisCountDataList.add(segmentCustomAnalysisCountData);
				
			}
		}
		
		//5.按照人数降序排序
        Collections.sort(segmentCustomAnalysisCountDataList, new Comparator<SegmentCustomAnalysisCountData>() {
            @Override
            public int compare(SegmentCustomAnalysisCountData m1, SegmentCustomAnalysisCountData m2) {
            	
                int m1Count = m1.getTagCount();
                int m2Count = m2.getTagCount();
                
                if(m1Count > m2Count){
                    return -1;
                }else if(m1Count < m2Count){
                    return 1;
                }else{
                    return 0;
                }
            }
        });
		
        //6.保存覆盖人数数据
		segmentCustomAnalysisDataOut.setPopulationCount(segmentCustomAnalysisCountDataList);
		baseOutput.getData().add(segmentCustomAnalysisDataOut);
		
		//7.清楚redis 临时key
		segmentManageCalService.deleteTempKey(POOL_INDEX, uuid.get());
		logger.info("========================segment audienct analysis end ,categoryId:{},segmentHeadId:{}",categoryId,headId);
		
		return baseOutput;
	}

}
