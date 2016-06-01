package cn.rongcapital.mkt.service;

import javax.ws.rs.core.SecurityContext;

import cn.rongcapital.mkt.vo.SegmentHeadUpdateIn;

public interface SegmentHeaderUpdateService {
	
	public Object segmentHeaderUpdate(SegmentHeadUpdateIn body,SecurityContext securityContext);

}
