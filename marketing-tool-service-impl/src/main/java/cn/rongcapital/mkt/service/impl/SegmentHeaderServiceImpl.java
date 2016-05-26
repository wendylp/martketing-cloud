/*************************************************
 * @功能简述: segement header的service实现类
 * @项目名称: marketing cloud
 * @see: 
 * @author: 宋世涛
 * @version: 0.0.1
 * @date: 2016/5/16
 * @复审人: 
*************************************************/

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
import heracles.data.common.annotation.ReadWrite;
import heracles.data.common.util.ReadWriteType;

@Service
public class SegmentHeaderServiceImpl implements SegmentHeaderService {

	/**
	 * @功能简述: 创建segment header
	 * @param: SegmentHeadIn body, SecurityContext securityContext 
	 * @return: Object
	 */
    @Autowired
    SegmentationDao segmentationDao;
    @Override
    @ReadWrite(type=ReadWriteType.WRITE)
    public Object segmentHeaderCreate(SegmentHeadIn body,SecurityContext securityContext) {
        Segmentation t = new Segmentation();
    	t.setName(body.getSegmentName());
    	t.setPublishStatus(body.getPublishStatus().byteValue());
    	UpdateResult ur = new UpdateResult(1,"error");
    	int res = segmentationDao.insert(t);
    	if(res>0) {
    	    ur = new UpdateResult(0,"success");
    	}
    	return Response.ok().entity(ur).build();
    }
  
}
