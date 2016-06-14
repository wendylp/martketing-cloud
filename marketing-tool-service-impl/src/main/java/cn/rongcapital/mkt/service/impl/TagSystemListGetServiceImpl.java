/*************************************************
 * @功能简述: 获取系统标签内容列表 
 * @see MktApi
 * @author: zhangwei
 * @version: 1.0
 * @date：2016-06-07
 *************************************************/
package cn.rongcapital.mkt.service.impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.TagDao;
import cn.rongcapital.mkt.po.Tag;
import cn.rongcapital.mkt.service.TagSystemListGetService;
import cn.rongcapital.mkt.vo.BaseOutput;

@Service
public class TagSystemListGetServiceImpl implements TagSystemListGetService {

	@Autowired
	TagDao tagDao;

	@Override
	public BaseOutput getTagcount(String method, String userToken,
			Integer tagGroupId, Integer index, Integer size) {
		BaseOutput baseOutput = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
				ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO, null);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("tagGroupId", tagGroupId);
		paramMap.put("startIndex", index);
		paramMap.put("pageSize", size);
		List<Tag> tagList = tagDao.selectListByParentGroupId(paramMap);
		if (CollectionUtils.isNotEmpty(tagList)) {
			baseOutput.setTotal(tagList.size());
			for (Tag t : tagList) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("tag_id", t.getId());
				map.put("tag_name", t.getName());
				baseOutput.getData().add(map);
			}
		}

		return baseOutput;
	}

}
