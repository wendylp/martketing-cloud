/*************************************************
 * @功能简述: 根据系统最末级标签组ID查询出标签内容列表的业务类实现
 * @see MktApi：
 * @author: xuning
 * @version: 1.0
 * @date：2016-06-07
 *************************************************/
package cn.rongcapital.mkt.service.impl;

import heracles.data.common.annotation.ReadWrite;
import heracles.data.common.util.ReadWriteType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.TagDao;
import cn.rongcapital.mkt.po.Tag;
import cn.rongcapital.mkt.service.SegmentTagnameTagValueService;
import cn.rongcapital.mkt.vo.BaseOutput;

@Service
public class SegmentTagnameTagValueServiceImpl implements SegmentTagnameTagValueService{
	@Autowired
	TagDao tagDao;
	
	@Override
	@ReadWrite(type=ReadWriteType.READ)
	public BaseOutput getTagValueByGroupId(String tagGroupId) {
		List<Tag> resList = tagDao.selectListByGroupId(tagGroupId);
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
				   ApiErrorCode.SUCCESS.getMsg(),
				   ApiConstant.INT_ZERO,null);
		if(resList != null && !resList.isEmpty()){
			result.setTotal(resList.size());
			for(Tag po : resList){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("tag_id", po.getId());
				map.put("tag_name", po.getName());
				result.getData().add(map);
			}
		}
		return result;
	}
}
