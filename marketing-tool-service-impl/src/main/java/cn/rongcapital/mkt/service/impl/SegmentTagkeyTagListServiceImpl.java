/*************************************************
 * @功能简述: 根据关键字查询系统推荐标签下拉列表的业务类实现
 * @see MktApi：
 * @author: xuning
 * @version: 1.0
 * @date：2016-06-06
 *************************************************/
package cn.rongcapital.mkt.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.TaggroupDao;
import cn.rongcapital.mkt.po.Taggroup;
import cn.rongcapital.mkt.service.SegmentTagkeyTagListService;
import cn.rongcapital.mkt.vo.BaseOutput;

@Service
public class SegmentTagkeyTagListServiceImpl implements SegmentTagkeyTagListService{
	
	@Autowired
	TaggroupDao taggroupDao;
	
	@Override
	public BaseOutput getLastTagByKey(String tagGroupName) {
		//根据标签组名模糊查询标签组
		Taggroup param = new Taggroup();
		param.setName(tagGroupName);
		param.setLevel(ApiConstant.INT_ZERO);
		List<Taggroup> resList = taggroupDao.selectByNameFuzzy(param);

		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
				   ApiErrorCode.SUCCESS.getMsg(),
				   ApiConstant.INT_ZERO,null);
		if(null != resList && resList.size() > 0){
			result.setTotal(resList.size());
			for(Taggroup po : resList){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("tag_group_id", po.getId());
				map.put("tag_group_name", po.getName());
				result.getData().add(map);
			}
		}
		return result;
	}
}
