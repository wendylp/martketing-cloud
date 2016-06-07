/*************************************************
 * @功能简述: AudienceListPartyMapService实现类
 * @项目名称: marketing cloud
 * @see: 
 * @author: 杨玉麟
 * @version: 0.0.1
 * @date: 2016/6/6
 * @复审人: 
*************************************************/
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
import cn.rongcapital.mkt.dao.AudienceListPartyMapDao;
import cn.rongcapital.mkt.po.AudienceListPartyMap;
import cn.rongcapital.mkt.service.AudienceListPartyMapService;
import cn.rongcapital.mkt.vo.BaseOutput;
import heracles.data.common.annotation.ReadWrite;
import heracles.data.common.util.ReadWriteType;

@Service
public class AudienceListPartyMapServiceImpl implements AudienceListPartyMapService{
	@Autowired
	AudienceListPartyMapDao audienceListPartyMapDao;
	
	@Override
	@ReadWrite(type=ReadWriteType.WRITE)
	public Object audienceListDel(String userToken, Integer audience_list_id, SecurityContext securityContext) {
		// TODO Auto-generated method stub
		AudienceListPartyMap audienceListPartyMap = new AudienceListPartyMap();
		
		audienceListPartyMap.setAudienceListId(audience_list_id);
		audienceListPartyMap.setStatus(new Integer(1).byteValue());
		audienceListPartyMap.setUpdateTime(new Date());
		
		BaseOutput ur = new BaseOutput(ApiErrorCode.DB_ERROR.getCode(),ApiErrorCode.DB_ERROR.getMsg(),ApiConstant.INT_ZERO,null);
		int res =  audienceListPartyMapDao.updateByListId(audienceListPartyMap);
		
		if(res > ApiConstant.INT_ZERO) {
    		ur = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),ApiErrorCode.SUCCESS.getMsg(),ApiConstant.INT_ZERO,null);
    	}
		
		Map<String,Object> map = new HashMap<String,Object>();
    	ur.getData().add(map);
    	ur.setTotal(ur.getData().size());
    	return Response.ok().entity(ur).build();

	}

}
