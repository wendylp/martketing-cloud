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
import cn.rongcapital.mkt.dao.SegmentationDao;
import cn.rongcapital.mkt.po.Segmentation;
import cn.rongcapital.mkt.service.SegmentHeaderUpdateService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.SegmentHeadUpdateIn;
import heracles.data.common.annotation.ReadWrite;
import heracles.data.common.util.ReadWriteType;

@Service
public class SegmentHeaderUpdateImpl implements SegmentHeaderUpdateService {
	
	 @Autowired
	 SegmentationDao segmentationDao;
	 
	 /**
     * @功能简述: 编辑segment header
     * @param: SegmentHeadIn body, SecurityContext securityContext 
     * @return: Object
     */
	 @Override
	 @ReadWrite(type=ReadWriteType.WRITE)
	 public Object segmentHeaderUpdate(SegmentHeadUpdateIn body,SecurityContext securityContext) {
	        Segmentation t = new Segmentation();
	        t.setId(body.getSegmentId());
	    	t.setName(body.getSegmentName());
	    	t.setPublishStatus(body.getPublishStatus().byteValue());
	    	t.setCreateTime(new Date());
	    	BaseOutput ur = new BaseOutput(ApiConstant.INT_ZERO,ApiErrorCode.SUCCESS.getMsg(),ApiConstant.INT_ZERO,null);
	    	Map<String,Object> map = new HashMap<String,Object>();
	    	map.put("oper", "奥巴马");//TO DO:MOCK
	    	map.put("updatetime", "2016-06-01 14:26:01");
	    	ur.getData().add(map);
	    	ur.setTotal(ur.getData().size());
	    	segmentationDao.updateById(t);
	    	return Response.ok().entity(ur).build();
	    
	 }
}
