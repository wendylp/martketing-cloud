/*************************************************
 * @功能简述: SegmentHeaderCreateService实现类
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

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.SegmentationDao;
import cn.rongcapital.mkt.po.Segmentation;
import cn.rongcapital.mkt.service.SegmentHeaderCreateService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.SegmentHeadIn;
import heracles.data.common.annotation.ReadWrite;
import heracles.data.common.util.ReadWriteType;

@Service
public class SegmentHeaderCreateServiceImpl implements SegmentHeaderCreateService {

    @Autowired
    SegmentationDao segmentationDao;
    
    /**
     * @功能简述: 创建segment header
     * @param: SegmentHeadIn body, SecurityContext securityContext 
     * @return: Object
     */
    @Override
    @ReadWrite(type=ReadWriteType.WRITE)
    public Object segmentHeaderCreate(SegmentHeadIn body,SecurityContext securityContext) {
        Segmentation t = new Segmentation();
    	t.setName(body.getSegmentName());
    	t.setPublishStatus(body.getPublishStatus().byteValue());
    	BaseOutput ur = new BaseOutput(ApiErrorCode.DB_ERROR.getCode(),ApiErrorCode.DB_ERROR.getMsg(),ApiConstant.INT_ZERO,null);
    	int res = segmentationDao.insert(t);
    	if(res > ApiConstant.INT_ZERO) {
    		ur = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),ApiErrorCode.SUCCESS.getMsg(),ApiConstant.INT_ZERO,null);
    	}
    	return Response.ok().entity(ur).build();
    }

}
