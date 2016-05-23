package cn.rongcapital.mkt.service.impl;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.dao.SegmentationDao;
import cn.rongcapital.mkt.po.Segmentation;
import cn.rongcapital.mkt.service.SegmentHeaderService;
import cn.rongcapital.mkt.vo.SegmentHeadIn;
import cn.rongcapital.mkt.vo.UpdateResult;

@Service
public class SegmentHeaderServiceImpl implements SegmentHeaderService {
     @Autowired
     SegmentationDao segmentationDao;
      @Override
      public Object segmentHeaderPost(SegmentHeadIn body,SecurityContext securityContext){
    	  Segmentation t = new Segmentation();
    	  t.setName(body.getSegmentName());
    	  t.setPublishStatus(body.getPublishStatus().byteValue());
    	  int res = segmentationDao.insert(t);
    	  if(res>0){
    		  UpdateResult ur = new UpdateResult(0,"success");
    		  return Response.ok().entity(ur).build();
    	  }else{
    		  UpdateResult ur = new UpdateResult(1,"error");
    		  return Response.ok().entity("error").build();
    	  }
      }
  
}
