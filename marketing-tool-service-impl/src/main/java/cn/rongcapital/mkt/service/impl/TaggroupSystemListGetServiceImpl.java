package cn.rongcapital.mkt.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.TagDao;
import cn.rongcapital.mkt.dao.TaggroupDao;
import cn.rongcapital.mkt.po.Taggroup;
import cn.rongcapital.mkt.service.TaggroupSystemListGetService;
import cn.rongcapital.mkt.vo.BaseOutput;

@Service
public class TaggroupSystemListGetServiceImpl implements
		TaggroupSystemListGetService {

	@Autowired
	TaggroupDao taggroupDao;

	@Autowired
	TagDao tagDao;

	@Override
	public BaseOutput getTagGroupByParentGroupId(String method,
			String userToken, Integer tagGroupId, Integer index, Integer size) {
		BaseOutput baseOutput = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
				ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO, null);
		Taggroup taggroup = new Taggroup();
		taggroup.setParentGroupId(Long.valueOf(tagGroupId));
		taggroup.setStartIndex(index);
		taggroup.setPageSize(size);
		List<Taggroup> groupList = taggroupDao.selectList(taggroup);
		if(null != groupList && groupList.size() > 0){
			baseOutput.setTotal(groupList.size());
			for(Taggroup group: groupList){
				int count = tagDao.selectListCountByGroupId(String.valueOf(group.getId()));
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("tag_group_id", group.getId());
				map.put("tag_group_name", group.getName());
				map.put("tag_count", count);
				baseOutput.getData().add(map);
			}
		}
		
		return baseOutput;
	}

}
