/*************************************************
 * @功能简述: 查询系统推荐标签列表的业务类实现
 * @see MktApi：
 * @author: xuning
 * @version: 1.0
 * @date：2016-06-06
 *************************************************/
package cn.rongcapital.mkt.service.impl;

import heracles.data.common.annotation.ReadWrite;
import heracles.data.common.util.ReadWriteType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.TagRecommendDao;
import cn.rongcapital.mkt.po.TagRecommend;
import cn.rongcapital.mkt.service.SegmentTagnameTagListService;
import cn.rongcapital.mkt.vo.BaseOutput;

@Service
public class SegmentTagnameTagListServiceImpl implements SegmentTagnameTagListService {
	@Autowired
	TagRecommendDao tagRecommendDao;

	@Override
	@ReadWrite(type = ReadWriteType.READ)
	public BaseOutput getSysRecommendedTagList() {
		TagRecommend tr = new TagRecommend();
		tr.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		int count = tagRecommendDao.selectListCount(tr);
		tr.setPageSize(count);
		List<TagRecommend> resList = tagRecommendDao.selectList(tr);
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);
		if (CollectionUtils.isNotEmpty(resList)) {
			result.setTotal(resList.size());
			for (TagRecommend po : resList) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("tag_group_id", po.getTagGroupId());
				map.put("tag_group_name", getTagRecommendLastName(po.getTagGroupName()));
				result.getData().add(map);
			}
		}
		return result;
	}

	/**
	 * 获取系统推荐标签的最后一个关键词
	 * 
	 * @param tagRecommend
	 * @return
	 */
	private String getTagRecommendLastName(String tagRecommend) {
		if (tagRecommend == null || tagRecommend.length() == 0) {
			return "";
		} else {
			return tagRecommend.substring(tagRecommend.lastIndexOf('-') + 1);
		}
	}

}
