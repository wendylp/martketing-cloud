/*************************************************
 * @功能简述: AudienceListService实现类
 * @see: MkyApi
 * @author: 杨玉麟
 * @version: 1.0
 * @date: 2016/6/6 
*************************************************/


package cn.rongcapital.mkt.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.AudienceListDao;
import cn.rongcapital.mkt.po.AudienceList;
import cn.rongcapital.mkt.service.AudienceAllListService;
import cn.rongcapital.mkt.vo.BaseOutput;
import heracles.data.common.annotation.ReadWrite;
import heracles.data.common.util.ReadWriteType;

@Service
public class AudienceAllListServiceImpl implements AudienceAllListService {
    private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	AudienceListDao audienceListDao;
	
	@Override
	@ReadWrite(type=ReadWriteType.READ)
	public BaseOutput audienceAllList(String userToken) {		
		AudienceList param = new AudienceList();
		param.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		int totalCount = audienceListDao.selectListCount(param);
		param.setPageSize(totalCount);
		param.setStartIndex(0);
		param.setOrderField("create_time");
		param.setOrderFieldType("desc");
		
		List<AudienceList> audienceList = audienceListDao.selectList(param);
		
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
				   ApiErrorCode.SUCCESS.getMsg(),
				   ApiConstant.INT_ZERO,null);
		if (CollectionUtils.isNotEmpty(audienceList)) {
			for (AudienceList e : audienceList) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("audience_list_id", e.getId());
				map.put("audience_list_name", e.getAudienceName());
				map.put("audience_count", e.getAudienceRows());
				result.getData().add(map);
			}
		}
		
		result.setTotal(result.getData().size());
		result.setTotalCount(totalCount);
		return result;
	}
}
