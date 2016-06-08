/*************************************************
 * @功能简述: AudienceListDeleteService实现类
 * @see: MktApi
 * @author: 杨玉麟
 * @version: 1.0
 * @date: 2016/6/6
*************************************************/
package cn.rongcapital.mkt.service.impl;

import heracles.data.common.annotation.ReadWrite;
import heracles.data.common.util.ReadWriteType;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.AudienceListPartyMapDao;
import cn.rongcapital.mkt.po.AudienceListPartyMap;
import cn.rongcapital.mkt.service.AudienceListDeleteService;
import cn.rongcapital.mkt.vo.BaseOutput;

@Service
public class AudienceListDeleteServiceImpl implements AudienceListDeleteService{
	@Autowired
	AudienceListPartyMapDao audienceListPartyMapDao;
	
	@Override
	@ReadWrite(type=ReadWriteType.WRITE)
	public BaseOutput audienceListDel(String userToken, Integer audienceListId, SecurityContext securityContext) {
		AudienceListPartyMap audienceListPartyMap = new AudienceListPartyMap();
		
		audienceListPartyMap.setAudienceListId(audienceListId);
		audienceListPartyMap.setStatus(ApiConstant.INT_ONE);
		audienceListPartyMap.setUpdateTime(new Date());
		
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),ApiErrorCode.SUCCESS.getMsg(),ApiConstant.INT_ZERO,null);
		int res =  audienceListPartyMapDao.updateByListId(audienceListPartyMap);
		
		if(res == ApiConstant.INT_ZERO) {
			result.setCode(ApiErrorCode.DB_ERROR.getCode());
			result.setMsg(ApiErrorCode.DB_ERROR.getMsg());
    	}
		
		Map<String,Object> map = new HashMap<String,Object>();
		result.getData().add(map);
    	return result;
	}
}
