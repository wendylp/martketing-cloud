/*************************************************
 * @功能简述: 接口mkt.segment.header.get的service实现类
 * @项目名称: marketing cloud
 * @see: 
 * @author: 宋世涛
 * @version: 0.0.1
 * @date: 2016/5/16
 * @复审人: 
*************************************************/

package cn.rongcapital.mkt.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.SegmentationDao;
import cn.rongcapital.mkt.po.Segmentation;
import cn.rongcapital.mkt.service.SegmentHeaderGetService;
import cn.rongcapital.mkt.vo.BaseOutput;

@Service
public class SegmentHeaderGetServiceImpl implements SegmentHeaderGetService {

	@Autowired
    SegmentationDao segmentationDao;
	
	@Override
	public Object segmentHeaderGet(String userToken, String ver, String segmentId) {
		Segmentation t = new Segmentation();
		t.setId(Integer.parseInt(segmentId));
		t.setStatus((byte)ApiConstant.INT_ZERO);
		List<Segmentation> list = segmentationDao.selectList(t);
		BaseOutput out = new BaseOutput(ApiConstant.INT_ZERO,ApiErrorCode.SUCCESS.getMsg(),ApiConstant.INT_ZERO,null);
		Map<String,Object> map = new HashMap<String,Object>();
		if(null != list && list.size() > 0){
			Segmentation s = list.get(0);
			map.put("segment_name", s.getName());
			map.put("publish_status", s.getPublishStatus());
			map.put("oper", "奥巴马");//TO DO:MOCK
			map.put("id", t.getId());
			map.put("updatetime", "2016-06-01 14:26:01");
			out.getData().add(map);
		}
		out.setTotal(out.getData().size());
		return Response.ok().entity(out).build();
	}

}
