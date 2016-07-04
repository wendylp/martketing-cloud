package cn.rongcapital.mkt.job.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.dao.SegmentationBodyDao;
import cn.rongcapital.mkt.dao.SegmentationHeadDao;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.po.SegmentationBody;
import cn.rongcapital.mkt.po.SegmentationHead;
import cn.rongcapital.mkt.po.mongodb.DataParty;
import cn.rongcapital.mkt.po.mongodb.Segment;

@Service
public class DataSegmentSyncTaskServiceImpl implements TaskService {

//	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private MongoTemplate mongoTemplate; 
	@Autowired
	private SegmentationBodyDao segmentationBodyDao;
	@Autowired
	private SegmentationHeadDao segmentationHeadDao;
	
	@Override
	public void task(Integer taskId) {
		SegmentationHead segmentationHeadT = new SegmentationHead();
		segmentationHeadT.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		segmentationHeadT.setPageSize(Integer.MAX_VALUE);
		List<SegmentationHead> segmentationHeadList = segmentationHeadDao.selectList(segmentationHeadT);
		if(CollectionUtils.isNotEmpty(segmentationHeadList)) {
			for(SegmentationHead segmentationHead:segmentationHeadList) {
				if(segmentationHead.getPublishStatus() == ApiConstant.SEGMENT_PUBLISH_STATUS_NOT_PUBLISH) {
					continue;//未发布的细分不进行同步
				}
				//删除旧segment数据
				mongoTemplate.findAllAndRemove(new Query(Criteria.where("segmentationHeadId").is(segmentationHead.getId())),Segment.class);
				//SEGMENTATION_GROUP_MEMBER_MOST_COUNT:每个组最多的标签数
				for(int groupIndex=0;groupIndex<ApiConstant.SEGMENTATION_GROUP_MEMBER_MOST_COUNT;groupIndex++) {
					SegmentationBody segmentationBodyT = new SegmentationBody(); 
					segmentationBodyT.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
					segmentationBodyT.setHeadId(segmentationHead.getId());
					segmentationBodyT.setGroupIndex(groupIndex);
					List<SegmentationBody> segmentationBodyList = segmentationBodyDao.selectList(segmentationBodyT);
					if(CollectionUtils.isEmpty(segmentationBodyList)) {
						continue;
					}
					List<Criteria> criteriasList = new ArrayList<Criteria>();
					for(SegmentationBody segmentationBody:segmentationBodyList) {
						Integer tagId = segmentationBody.getTagId();
						Integer tagGroupId = segmentationBody.getTagGroupId();
						Byte exclude = segmentationBody.getExclude();
						if(exclude == 0) {
							if(tagId == 0) {//不限
								Criteria criteria = Criteria.where("tagList.tagGroupId").is(tagGroupId);
								criteriasList.add(criteria);
							} else {
								Criteria criteria = Criteria.where("tagList.tagId").is(tagId);
								criteriasList.add(criteria);
							}
						}else{
							if(tagId == 0) {//不限
								Criteria criteria = Criteria.where("tagList.tagGroupId").ne(tagGroupId);
								criteriasList.add(criteria);
							} else {
								Criteria criteria  = Criteria.where("tagList.tagId").ne(tagId);
								criteriasList.add(criteria);
							}
						}
						Criteria criteriaAll = new Criteria().andOperator(criteriasList.toArray(new Criteria[criteriasList.size()]));
						List<DataParty> dataPartyList = mongoTemplate.find(new Query().addCriteria(criteriaAll), DataParty.class);
						if(CollectionUtils.isNotEmpty(dataPartyList)) {
							for(DataParty dataParty:dataPartyList) {
								List<Segment> sListT = mongoTemplate.find(new Query(Criteria.where("segmentationHeadId")
												       .is(segmentationBody.getHeadId())
												       .and("dataId").is(dataParty.getMid())
													   .and("mappingKeyid").is(dataParty.getMappingKeyid())),
												       Segment.class);
								if(CollectionUtils.isEmpty(sListT)) {//不存在，则插入
									Segment segment = new Segment();
									segment.setDataId(dataParty.getMid());
									segment.setSegmentationHeadId(segmentationBody.getHeadId());
									segment.setName(dataParty.getName());
									segment.setMdType(dataParty.getMdType());
									segment.setMappingKeyid(dataParty.getMappingKeyid());
									mongoTemplate.insert(segment);
								}
							}
						}
					}
				}
			}
		}
	}
	

}
