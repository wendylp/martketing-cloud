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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import cn.rongcapital.mkt.po.mongodb.Segment;
import cn.rongcapital.mkt.service.SegmentTagUpdateService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.SegmentTagUpdateIn;
import heracles.data.common.annotation.ReadWrite;
import heracles.data.common.util.ReadWriteType;

@Service
@Transactional
public class SegmentTagUpdateServiceImpl implements SegmentTagUpdateService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
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
	public BaseOutput updateSegmentTag(SegmentTagUpdateIn body, SecurityContext securityContext) {
		BaseOutput baseOutput = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);
		
		
		Integer headerId;
		try {
			headerId = Integer.valueOf(body.getSegmentHeadId());
		} catch (Exception e) {
			logger.error(e.getMessage());
			baseOutput.setCode(9001);
			baseOutput.setMsg("细分人群编号不能为空");
			return baseOutput;
		}
		
		List<String> tagNames = body.getTagNames();
		// Date now = new Date();

		CustomTag tagExample = null;
		List<CustomTag> tag = null;

		List<Segment> segmentList = mongoTemplate.find(new Query(new Criteria("segmentation_head_id").is(headerId)),
				Segment.class);

		List<Integer> personIdList = new ArrayList<Integer>();

		if (CollectionUtils.isEmpty(segmentList)) {
			segmentList = new ArrayList<Segment>();
		} else {
			for (Segment segment : segmentList) {
				personIdList.add(segment.getDataId());
			}

		}
		List<Integer> tagIdList = new ArrayList<Integer>();
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
					insertTag.setCoverAudienceCount(segmentList.size());
					// insertTag.setCreateTime(now);
					// insertTag.setUpdateTime(now);
					customTagDao.insert(insertTag);
					tagIdList.add(insertTag.getId());
				} else {
					CustomTag insertTag = new CustomTag();
					insertTag.setName(tagName);
					insertTag.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
					insertTag.setCoverAudienceCount(segmentList.size());
					insertTag.setId(tag.get(0).getId());
					// insertTag.setCreateTime(now);
					// insertTag.setUpdateTime(now);
					customTagDao.updateById(insertTag);
					tagIdList.add(tag.get(0).getId());
				}
			}
		}
		// 删除标签与细分对应关系
		customTagMapDao.batchDeleteUseHeadId(headerId);
		
		// 建立标签与细分对应关系
		for (Integer customTag : tagIdList) {
			
			Integer tagId = customTag.intValue();
			CustomTagMap tagMap = new CustomTagMap();
			tagMap.setTagId(tagId);
			tagMap.setType(ApiConstant.TAG_TYPE_SEGMENT);
			tagMap.setMapId(headerId);
//			tagMap.setCreateTime(now);
//			tagMap.setUpdateTime(now);
			customTagMapDao.insert(tagMap);
			
			// 删除标签与人群对应关系
			customTagMapDao.batchDeleteUseTagId(tagId);
			
			// 建立细分与人群对应关系
			for (Integer personId : personIdList) {
				CustomTagMap pensonTagMap = new CustomTagMap();
				pensonTagMap.setTagId(tagId);
				pensonTagMap.setType(ApiConstant.TAG_TYPE_CONTACT);
				pensonTagMap.setMapId(personId);
				// tagMap.setCreateTime(now);
				// tagMap.setUpdateTime(now);
				customTagMapDao.insert(pensonTagMap);
			}
			
		}
	
		// 标签ID保存至segmentation_head
		SegmentationHead headUpdate = new SegmentationHead();
		headUpdate.setId(headerId);
		headUpdate.setTagIds(StringUtils.join(tagIdList, ","));
		// headUpdate.setUpdateTime(now);
		segmentationHeadDao.updateById(headUpdate);

		return baseOutput;
	}

}