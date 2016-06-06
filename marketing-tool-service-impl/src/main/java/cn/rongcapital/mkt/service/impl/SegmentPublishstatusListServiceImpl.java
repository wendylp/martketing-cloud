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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.SegmentationHeadDao;
import cn.rongcapital.mkt.po.SegmentationHead;
import cn.rongcapital.mkt.service.SegmentPublishstatusListService;
import cn.rongcapital.mkt.vo.BaseOutput;
import heracles.data.common.annotation.ReadWrite;
import heracles.data.common.util.ReadWriteType;

@Service
public class SegmentPublishstatusListServiceImpl implements SegmentPublishstatusListService {

    @Autowired
    SegmentationHeadDao segmentationHeadDao;

    /**
     * @功能简述: mkt.segment.publishstatus.list.get
     * @param: String method, String userToken, 
	 *		   Integer publishStatus, Integer index,
	 *		   Integer size, String ver
     * @return: Object
     */
	@Override
	@ReadWrite(type=ReadWriteType.READ)
	public Object segmentPublishstatusList(String userToken, 
										   Integer publishStatus, Integer index,
										   Integer size, String ver,String keyword) {
		SegmentationHead t = new SegmentationHead();
		t.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		if(ApiConstant.SEGMENT_PUBLISH_STATUS_ALL != publishStatus.byteValue()){
			t.setPublishStatus(publishStatus.byteValue());
		}
		t.setPageSize(size);
		t.setStartIndex((index-1)*size);
		t.getCustomMap().put("keyword", keyword);
		List<SegmentationHead> reList = segmentationHeadDao.selectListByKeyword(t);
		BaseOutput rseult = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
										   ApiErrorCode.SUCCESS.getMsg(),
										   ApiConstant.INT_ZERO,null);
		if(null !=reList && reList.size()>0){
			for(SegmentationHead s:reList){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("segment_name", s.getName());
				map.put("segment_head_id", s.getId());
				rseult.getData().add(map);
			}
		}
		rseult.setTotal(rseult.getData().size());
		return Response.ok().entity(rseult).build();
	}

}
