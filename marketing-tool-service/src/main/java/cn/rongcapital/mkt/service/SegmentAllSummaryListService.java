package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.out.SegmentSummaryListOut;

public interface SegmentAllSummaryListService {

	public SegmentSummaryListOut segmentAllSummaryList(String userToken,
			    						   Integer publishStatus, String ver);
	
}
