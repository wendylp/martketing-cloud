package cn.rongcapital.mkt.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.common.util.DateUtil;
import cn.rongcapital.mkt.dao.SegmentationHeadDao;
import cn.rongcapital.mkt.po.SegmentationHead;
import cn.rongcapital.mkt.service.SegmentHeaderUpdateService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.SegmentHeadUpdateIn;
import heracles.data.common.annotation.ReadWrite;
import heracles.data.common.util.ReadWriteType;

@Service
public class SegmentHeaderUpdateImpl implements SegmentHeaderUpdateService {
	
	 @Autowired
	 SegmentationHeadDao segmentationHeadDao;
	 
	 /**
     * @功能简述: 编辑segment header
     * @param: SegmentHeadIn body, SecurityContext securityContext 
     * @return: Object
     */
	 @Override
	 @ReadWrite(type=ReadWriteType.WRITE)
	 public Object segmentHeaderUpdate(SegmentHeadUpdateIn body,SecurityContext securityContext) {
		 SegmentationHead t = new SegmentationHead();  
	        t.setId(body.getSegmentId());
	    	t.setName(body.getSegmentName());
	    	t.setPublishStatus(body.getPublishStatus().byteValue());
	    	Date now = new Date();
	    	t.setCreateTime(now);
	    	BaseOutput ur = new BaseOutput(ApiConstant.INT_ZERO,ApiErrorCode.SUCCESS.getMsg(),ApiConstant.INT_ZERO,null);
	    	segmentationHeadDao.updateById(t);
	    	Map<String,Object> map = new HashMap<String,Object>();
	    	map.put("oper", "");//TO DO:获取当前用户名
	    	map.put("updatetime", DateUtil.getStringFromDate(now, ApiConstant.DATE_FORMAT_yyyy_MM_dd_HH_mm_ss));
	    	map.put("id", t.getId());
	    	ur.getData().add(map);
	    	ur.setTotal(ur.getData().size());
	    	return Response.ok().entity(ur).build();
	    
	 }
}
