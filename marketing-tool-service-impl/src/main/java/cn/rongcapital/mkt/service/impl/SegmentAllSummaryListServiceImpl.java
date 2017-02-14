/*************************************************
 * @功能简述: SegmentSummaryListService实现类
 * @项目名称: marketing cloud
 * @see: 
 * @复审人: 
*************************************************/

package cn.rongcapital.mkt.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.SegmentationHeadDao;
import cn.rongcapital.mkt.po.SegmentationHead;
import cn.rongcapital.mkt.service.SegmentManageCalService;
import cn.rongcapital.mkt.service.SegmentAllSummaryListService;
import cn.rongcapital.mkt.vo.out.SegmentSummaryListDataOut;
import cn.rongcapital.mkt.vo.out.SegmentSummaryListOut;
import heracles.data.common.annotation.ReadWrite;
import heracles.data.common.util.ReadWriteType;

@Service
public class SegmentAllSummaryListServiceImpl implements SegmentAllSummaryListService {

	public static final  Integer POOL_INDEX = 2;
	
	public static final String SEGMENT_COVER_ID_STR="segmentcoverid:";
	
    @Autowired
    SegmentationHeadDao segmentationHeadDao;
    
	@Autowired
	private SegmentManageCalService segmentManageCalService;
	
    /**
     * @功能简述: mkt.segment.summary.list.get
     * @param: String userToken, Integer publishStatus, String ver 
     * @return: Object
     */
	@Override
	@ReadWrite(type=ReadWriteType.READ)
	public SegmentSummaryListOut segmentAllSummaryList(String userToken, 
										   Integer publishStatus,String ver) {
		SegmentationHead head = constructSegmentationHead(publishStatus);
		int totalCount = segmentationHeadDao.selectListCount(head);
		adjustmentSegmentationHead(head, totalCount);		
		SegmentSummaryListOut segSummaryList = new SegmentSummaryListOut(
										   ApiErrorCode.SUCCESS.getCode(),
										   ApiErrorCode.SUCCESS.getMsg(),
										   ApiConstant.INT_ZERO);
		List<SegmentSummaryListDataOut> summaryList = fillSummaryList(head);
		segSummaryList.setDataCustom(summaryList);
		segSummaryList.setTotal(summaryList.size());
		segSummaryList.setTotalCount(totalCount);
		return segSummaryList;
	}

	private void adjustmentSegmentationHead(SegmentationHead head, int totalCount) {
			head.setPageSize(totalCount);
			head.setStartIndex(0);
	}

	private List<SegmentSummaryListDataOut> fillSummaryList(SegmentationHead head) {
		List<SegmentSummaryListDataOut> summaryList = new ArrayList<SegmentSummaryListDataOut>();
		List<SegmentationHead> segHeadList = segmentationHeadDao.selectListByKeyword(head);
		
		if (CollectionUtils.isNotEmpty(segHeadList)) {
			for (SegmentationHead s : segHeadList) {
				SegmentSummaryListDataOut data = fillSegSummary(s);
				summaryList.add(data);
			}
		}
		
		return summaryList;
	}

	private SegmentSummaryListDataOut fillSegSummary(SegmentationHead s) {
		SegmentSummaryListDataOut data = new SegmentSummaryListDataOut();
		data.setSegmentName(s.getName());
		data.setSegmentHeadId(s.getId());
		data.setPublishStatus(s.getPublishStatus());
		// redis 中获取覆盖人数
		Long coverCount = segmentManageCalService.scard(POOL_INDEX, SEGMENT_COVER_ID_STR + s.getId());
		data.setCoverCount(coverCount.intValue());
		return data;
	}

	private SegmentationHead constructSegmentationHead(Integer publishStatus) {
		SegmentationHead head = new SegmentationHead();
		head.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
		if(ApiConstant.SEGMENT_PUBLISH_STATUS_ALL == publishStatus.byteValue()){
			//t.setPublishStatus(publishStatus.byteValue());
			head.setOrderFieldType("desc");
			head.setOrderField("publish_status");
		}else if(ApiConstant.SEGMENT_PUBLISH_STATUS_PUBLISH == publishStatus.byteValue()){
			
			head.setPublishStatus(publishStatus.byteValue());
			head.setOrderFieldType("desc");
			head.setOrderField("update_time");
		}else if(ApiConstant.SEGMENT_PUBLISH_STATUS_NOT_PUBLISH == publishStatus.byteValue()){
			head.setPublishStatus(publishStatus.byteValue());
			head.setOrderFieldType("desc");
			head.setOrderField("create_time");
		}

		return head;
	}

}
