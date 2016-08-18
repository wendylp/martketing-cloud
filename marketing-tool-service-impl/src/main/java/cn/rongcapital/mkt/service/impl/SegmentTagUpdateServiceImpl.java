/*************************************************
 * @功能简述: 增加或修改受众细分关联的标签
 * @see MktApi：
 * @author: 朱学龙
 * @version: 1.0
 * @date：2016-06-07
 *************************************************/
package cn.rongcapital.mkt.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.SecurityContext;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.CustomTagDao;
import cn.rongcapital.mkt.dao.CustomTagMapDao;
import cn.rongcapital.mkt.dao.SegmentationHeadDao;
import cn.rongcapital.mkt.po.CustomTag;
import cn.rongcapital.mkt.po.CustomTagMap;
import cn.rongcapital.mkt.po.SegmentationHead;
import cn.rongcapital.mkt.service.SegmentTagUpdateService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.SegmentTagUpdateIn;
import heracles.data.common.annotation.ReadWrite;
import heracles.data.common.util.ReadWriteType;

@Service
@Transactional
public class SegmentTagUpdateServiceImpl implements SegmentTagUpdateService {

	@Autowired
	SegmentationHeadDao segmentationHeadDao;

	@Autowired
	CustomTagMapDao customTagMapDao;

	@Autowired
	CustomTagDao customTagDao;

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@ReadWrite(type = ReadWriteType.WRITE)
	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public BaseOutput updateSegmentTag(SegmentTagUpdateIn body,
			SecurityContext securityContext) {
		BaseOutput baseOutput = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
				ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO, null);
		Integer headerId = Integer.valueOf(body.getSegmentHeadId());
		List<String> tagNames = body.getTagNames();
//		Date now = new Date();
		List<Integer> tagIdList = new ArrayList<Integer>();
		CustomTag tagExample = null;
		List<CustomTag> tag = null;
        Query query = Query.query(Criteria.where("segmentation_head_id").is(headerId));
		
		long count = mongoTemplate.count(query, "segment");
		// 标签名保存至自定义标签表
		if (tagNames != null) {
			for (String tagName : tagNames) {
				tagExample = new CustomTag();
				tagExample.setName(tagName);
				tagExample.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
				tag = customTagDao.selectList(tagExample);
				if (CollectionUtils.isEmpty(tag)) {
					CustomTag insertTag = new CustomTag();
					insertTag.setName(tagName);
					insertTag.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
					insertTag.setCoverAudienceCount((int)count);
//					insertTag.setCreateTime(now);
//					insertTag.setUpdateTime(now);
					customTagDao.insert(insertTag);
					tagIdList.add(insertTag.getId());
				} else {
					CustomTag insertTag = new CustomTag();
					insertTag.setName(tagName);
					insertTag.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
					insertTag.setCoverAudienceCount((int)count);
					insertTag.setId(tag.get(0).getId());
//					insertTag.setCreateTime(now);
//					insertTag.setUpdateTime(now);
					customTagDao.updateById(insertTag);
					tagIdList.add(tag.get(0).getId());
				}
			}
		}
		// 删除标签与细分对应关系
		customTagMapDao.batchDeleteUseHeadId(headerId);

		// 建立标签与细分对应关系
		for (Integer customTag : tagIdList) {
			CustomTagMap tagMap = new CustomTagMap();
			tagMap.setTagId(customTag.intValue());
			tagMap.setType(ApiConstant.TAG_TYPE_SEGMENT);
			tagMap.setMapId(headerId);
			tagMap.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
//			tagMap.setCreateTime(now);
//			tagMap.setUpdateTime(now);
			customTagMapDao.insert(tagMap);
		}

		// 标签ID保存至segmentation_head
		SegmentationHead headUpdate = new SegmentationHead();
		headUpdate.setId(headerId);
		headUpdate.setTagIds(StringUtils.join(tagIdList, ","));
//		headUpdate.setUpdateTime(now);
		segmentationHeadDao.updateById(headUpdate);
		
		return baseOutput;
	}

}
