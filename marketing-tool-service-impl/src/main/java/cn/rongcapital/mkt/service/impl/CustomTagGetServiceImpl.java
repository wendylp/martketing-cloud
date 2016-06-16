package cn.rongcapital.mkt.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.CustomTagDao;
import cn.rongcapital.mkt.po.CustomTag;
import cn.rongcapital.mkt.service.CustomTagGetService;
import cn.rongcapital.mkt.vo.BaseOutput;

@Service
public class CustomTagGetServiceImpl implements CustomTagGetService {

    @Autowired
    private CustomTagDao customTagDao;

    @Override
    public BaseOutput getCustomTagList(String method, String userToken, Integer index, Integer size) {
    	
    	CustomTag customTag = new CustomTag(index,size);
		List<CustomTag> customTagList = customTagDao.selectList(customTag);
		int totalCount = customTagDao.selectListCount(null);
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
                ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO, null);
		
		if (customTagList != null && !customTagList.isEmpty()) {
			for (CustomTag tag : customTagList) {
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("tag_id", tag.getId());
				map.put("tag_name", tag.getName());
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				map.put("create_time", format.format(tag.getCreateTime()));
				map.put("cover_audience_count", tag.getCoverAudienceCount());
				result.getData().add(map);
			}
		}
		result.setTotal(result.getData().size());
		result.setTotalCount(totalCount);
		return result;
    }
}
