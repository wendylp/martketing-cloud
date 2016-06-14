/*************************************************
 * @功能简述: 获取系统标签组列表 
 * @see MktApi
 * @author: zhangwei
 * @version: 1.0
 * @date：2016-06-08
 *************************************************/
package cn.rongcapital.mkt.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.TaggroupDao;
import cn.rongcapital.mkt.po.Taggroup;
import cn.rongcapital.mkt.service.TaggroupSystemMenulistGetService;
import cn.rongcapital.mkt.vo.BaseOutput;

@Service
public class TaggroupSystemMenulistGetServiceImpl implements
		TaggroupSystemMenulistGetService {

	@Autowired
	TaggroupDao TaggroupDao;
	
	@Override
	public BaseOutput getTaggroupSystemMenulist(String method, String userToken,
			Integer index, Integer size) {
		BaseOutput baseOutput = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
				ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO, null);
		Taggroup param = new Taggroup();
		param.setStartIndex(index);
		param.setPageSize(size);
		List<Map<String, Object>> groupList = TaggroupDao.selectTaggroupSystemMenulist(param);
		if (CollectionUtils.isNotEmpty(groupList)) {
			baseOutput.setTotal(groupList.size());
			for (Map<String, Object> groupMap : groupList) {
				if(!groupMap.containsKey("tag_group_id")){
					groupMap.put("tag_group_id", "");
					groupMap.put("tag_group_name", "");
				}
				if(!groupMap.containsKey("tag_group_pid")){
					groupMap.put("tag_group_pid", "");
					groupMap.put("tag_group_pname", "");
				}
				baseOutput.getData().add(groupMap);
			}
		}

		return baseOutput;
	}

}
