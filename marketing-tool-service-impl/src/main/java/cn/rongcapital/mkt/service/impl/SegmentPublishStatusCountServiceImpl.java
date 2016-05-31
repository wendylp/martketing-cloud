/*************************************************
 * @功能简述: SegmentPublishStatusCountService实现类
 * @项目名称: marketing cloud
 * @see: 
 * @author: 宋世涛
 * @version: 0.0.1
 * @date: 2016/5/16
 * @复审人: 
*************************************************/

package cn.rongcapital.mkt.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.SegmentationDao;
import cn.rongcapital.mkt.po.Segmentation;
import cn.rongcapital.mkt.service.SegmentPublishStatusCountService;
import cn.rongcapital.mkt.vo.BaseOutput;
import heracles.data.common.annotation.ReadWrite;
import heracles.data.common.util.ReadWriteType;

@Service
public class SegmentPublishStatusCountServiceImpl implements SegmentPublishStatusCountService {

    @Autowired
    SegmentationDao segmentationDao;
    
    /**
	 * @功能简述: 查询不同发布状态下的segment数量
	 * @param: String method, String userToken, String ver 
	 * @return: Object
	 */
    @Override
    @ReadWrite(type=ReadWriteType.READ)
    public Object segmentPublishstatusCount(String userToken,String ver) {
    	Segmentation t = new Segmentation();
    	t.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
    	t.setPublishStatus(ApiConstant.SEGMENT_PUBLISH_STATUS_NOT_PUBLISH);
    	int countNotPublish = segmentationDao.selectListCount(t);
    	
    	t.setPublishStatus(ApiConstant.SEGMENT_PUBLISH_STATUS_PUBLISH);
    	int countPublish = segmentationDao.selectListCount(t);
    	
    	t.setPublishStatus(ApiConstant.SEGMENT_PUBLISH_STATUS_IN_CAMPAIGN);
    	int countInCampaign = segmentationDao.selectListCount(t);
    	
    	int countAll = countNotPublish + countPublish + countInCampaign;
    	
    	BaseOutput spsc = new BaseOutput();
    	Map<String, Number> dataMap = new HashMap<String, Number>();
    	dataMap.put("count", countNotPublish);
    	dataMap.put("publish_status", ApiConstant.SEGMENT_PUBLISH_STATUS_NOT_PUBLISH);
    	spsc.getData().add(dataMap);
    	dataMap = new HashMap<String, Number>();
    	dataMap.put("count", countPublish);
    	dataMap.put("publish_status", ApiConstant.SEGMENT_PUBLISH_STATUS_PUBLISH);
    	spsc.getData().add(dataMap);
    	dataMap = new HashMap<String, Number>();
    	dataMap.put("count", countInCampaign);
    	dataMap.put("publish_status", ApiConstant.SEGMENT_PUBLISH_STATUS_IN_CAMPAIGN);
    	spsc.getData().add(dataMap);
    	dataMap = new HashMap<String, Number>();
    	dataMap.put("count", countAll);
    	dataMap.put("publish_status", ApiConstant.SEGMENT_PUBLISH_STATUS_ALL);
    	spsc.getData().add(dataMap);
    	
    	spsc.setCode(ApiErrorCode.SUCCESS.getCode());
    	spsc.setMsg(ApiErrorCode.SUCCESS.getMsg());
    	spsc.setTotal(spsc.getData().size());
    	return Response.ok().entity(spsc).build();
    }
  
}
