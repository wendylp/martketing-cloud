package cn.rongcapital.mkt.job.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.enums.DataTypeEnum;
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
	private static final int pageSize = 100;
	
	@Override
	public void task(Integer taskId) {
		SegmentationHead segmentationHeadT = new SegmentationHead();
		segmentationHeadT.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		int totalRecord = segmentationHeadDao.selectListCount(segmentationHeadT);
		int totalPage = (totalRecord + pageSize -1) / pageSize;
		for(int index = 1; index <= totalPage; index++) {
			segmentationHeadT = new SegmentationHead(index,pageSize);
			segmentationHeadT.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
			List<SegmentationHead> segmentationHeadList = segmentationHeadDao.selectList(segmentationHeadT);
			if(CollectionUtils.isEmpty(segmentationHeadList)) {
				break;
			}
			doTask(segmentationHeadList);
		}
	}
	
	private void doTask(List<SegmentationHead> segmentationHeadList) {
		if(CollectionUtils.isEmpty(segmentationHeadList)) {
			return;
		}
		for(SegmentationHead segmentationHead:segmentationHeadList) {
			if(segmentationHead.getPublishStatus() == ApiConstant.SEGMENT_PUBLISH_STATUS_NOT_PUBLISH) {
				continue;//未发布的细分不进行同步
			}
			List<Segment> lastSegmentList = mongoTemplate.find(new Query(Criteria.where("segmentationHeadId").is(segmentationHead.getId())),Segment.class);
			Set<Integer> lastSegmentDataIdSet = new HashSet<Integer>();
			if(CollectionUtils.isNotEmpty(lastSegmentList)) {
				for(Segment s:lastSegmentList) {
					lastSegmentDataIdSet.add(s.getDataId());
				}
			}
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
//							List<Segment> sListT = mongoTemplate.find(new Query(Criteria.where("segmentationHeadId")
//											       .is(segmentationBody.getHeadId())
//											       .and("dataId").is(dataParty.getMid())),
//											       Segment.class);
//							if(CollectionUtils.isEmpty(sListT)) {//不存在，则插入
							if(!lastSegmentDataIdSet.contains(dataParty.getMid())) {//不存在，则插入
								Segment segment = new Segment();
								segment.setDataId(dataParty.getMid());
								segment.setSegmentationHeadId(segmentationBody.getHeadId());
								if(dataParty.getMdType()==DataTypeEnum.WECHAT.getCode()) {//如果是微信数据
									segment.setName(dataParty.getWxName());
								} else {
									segment.setName(dataParty.getName());
								}
								segment.setMdType(dataParty.getMdType());
								segment.setMappingKeyid(dataParty.getMappingKeyid());
								mongoTemplate.insert(segment);
							} else {//存在,则删除之前list里的id
								lastSegmentDataIdSet.remove(dataParty.getMid());
							}
						}
					}
				}
			}
			if(CollectionUtils.isNotEmpty(lastSegmentDataIdSet)) {
				for(int dataId:lastSegmentDataIdSet) {
					mongoTemplate.remove(new Query(Criteria.where("segmentationHeadId")
						       .is(segmentationHead.getId())
						       .and("dataId").is(dataId)),
						       Segment.class);
				}
			}
		}
	}
}
