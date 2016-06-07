/*************************************************
 * @功能简述: AudienceListService实现类
 * @项目名称: marketing cloud
 * @see: 
 * @author: 杨玉麟
 * @version: 0.0.1
 * @date: 2016/6/6
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
import cn.rongcapital.mkt.dao.AudienceListDao;
import cn.rongcapital.mkt.po.AudienceList;
import cn.rongcapital.mkt.service.AudienceListService;
import cn.rongcapital.mkt.vo.BaseOutput;
import heracles.data.common.annotation.ReadWrite;
import heracles.data.common.util.ReadWriteType;

@Service
public class AudienceListServiceImpl implements AudienceListService {
	@Autowired
	AudienceListDao audienceListDao;

	 /**
     * @功能简述: mkt.audience.list.get
     * @param: String method, String userToken, 
	 *		   Integer index,
	 *		   Integer size
     * @return: Object 
     * http://localhost/api?method=mkt.segment.header.get&ver=1.0&user_token=abc&segment_head_id=1
     */
	@Override
	@ReadWrite(type=ReadWriteType.READ)
	public Object audienceList(String userToken,Integer size,Integer index) {
		// TODO Auto-generated method stub
		
		AudienceList t_com = new AudienceList();
		t_com.setPageSize(size);
		t_com.setStartIndex((index-1)*size);
		
		List<AudienceList> reList = audienceListDao.selectList(t_com);
		
		
		BaseOutput rseult = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
				   ApiErrorCode.SUCCESS.getMsg(),
				   ApiConstant.INT_ZERO,null);
		if(null !=reList && reList.size()>0){
		for(AudienceList s:reList){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", s.getId());
		map.put("audienceName", s.getAudienceName());
		map.put("audienceRows", s.getAudienceRows());
		map.put("source", s.getSource());
		map.put("createTime", s.getCreateTime());
		rseult.getData().add(map);
		}
		}
		rseult.setTotal(rseult.getData().size());
		return Response.ok().entity(rseult).build();
		
	
	}

}
