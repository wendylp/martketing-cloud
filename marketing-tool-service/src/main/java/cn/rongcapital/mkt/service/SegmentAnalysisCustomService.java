package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;

/**
 * 
 * @author LIJINKAI
 * 2017-1-16
 *
 */
public interface SegmentAnalysisCustomService {
	BaseOutput getSegmentAnalysisTopCustomList(Integer topType);
	
	BaseOutput getSegmentCustomAnalysis(String categoryId,Integer headId);
	
}
