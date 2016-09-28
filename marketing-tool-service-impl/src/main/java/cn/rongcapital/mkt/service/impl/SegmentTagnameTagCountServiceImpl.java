/*************************************************
 * @功能简述: 根据系统最末级标签组ID查询出标签内容列表的业务类实现
 * @see MktApi：
 * @author: xuning
 * @version: 1.0
 * @date：2016-06-07
 *************************************************/
package cn.rongcapital.mkt.service.impl;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.TagDao;
import cn.rongcapital.mkt.po.mongodb.DataParty;
import cn.rongcapital.mkt.service.SegmentTagnameTagCountService;
import cn.rongcapital.mkt.vo.BaseOutput;
import heracles.data.common.annotation.ReadWrite;
import heracles.data.common.util.ReadWriteType;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SegmentTagnameTagCountServiceImpl implements SegmentTagnameTagCountService {

	// private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	TagDao tagDao;

	@Override
	@ReadWrite(type = ReadWriteType.READ)
	public BaseOutput getTagCountById(String tagIds) {
		String ids[] = tagIds.split(",");
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);

		List<Integer> idList = new ArrayList<>(ids.length);
		for (String tagidStr : ids) {
			idList.add(Integer.valueOf(tagidStr));
		}
		List<cn.rongcapital.mkt.po.Tag> dbTags = tagDao.selectListByIdList(idList);
		Map<String, cn.rongcapital.mkt.po.Tag> tagMap = new HashMap<>();
		for (cn.rongcapital.mkt.po.Tag tag : dbTags) {
			tagMap.put(tag.getId().toString(), tag);
		}

		for (String tagidStr : ids) {
			int tagId = Integer.parseInt(tagidStr);
			cn.rongcapital.mkt.po.Tag tag = tagMap.get(tagidStr);
			if (tag == null) {
				continue;
			}
			List<DataParty> restList = mongoTemplate.find(new Query(Criteria.where("tagList.tagId").is(tagId)),
					DataParty.class);
			int tagCount = 0;
			if (!CollectionUtils.isEmpty(restList)) {
				tagCount = restList.size();
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("tag_id", tagId);
			map.put("tag_name", tag.getName());
			map.put("tag_count", tagCount);
			result.getData().add(map);
		}
		result.setTotal(result.getData().size());
		return result;
	}

	/**
	 * 获取标签的柱状图数据
	 * 
	 * @author congshulin
	 * @功能简述 : 获取标签的柱状图数据
	 * @param tagIds
	 *            tag集合
	 * 
	 * @return BaseOutput
	 */
	@Override
	@ReadWrite(type = ReadWriteType.READ)
	public BaseOutput getMongoTagCountByTagIdList(String tagIds) {
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);

		String tagIdList[] = tagIds.split(",");

		for (String tagidStr : tagIdList) {

			String str = tagidStr.substring(0, tagidStr.indexOf("_"));

		}

		return result;
	}
}
