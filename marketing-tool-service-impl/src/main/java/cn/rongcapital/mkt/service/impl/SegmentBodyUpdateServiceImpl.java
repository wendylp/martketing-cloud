package cn.rongcapital.mkt.service.impl;

import heracles.data.common.annotation.ReadWrite;
import heracles.data.common.util.ReadWriteType;

import java.util.Date;
import java.util.List;

import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.SegmentationBodyDao;
import cn.rongcapital.mkt.po.SegmentationBody;
import cn.rongcapital.mkt.service.SegmentBodyUpdateService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.SegmentBodyFilterGroupIn;
import cn.rongcapital.mkt.vo.in.SegmentBodyTagsIn;
import cn.rongcapital.mkt.vo.in.SegmentBodyUpdateIn;

@Service
public class SegmentBodyUpdateServiceImpl implements SegmentBodyUpdateService {

	@Autowired
	SegmentationBodyDao segmentationHeadDao;

	@Override
	@ReadWrite(type = ReadWriteType.WRITE)
	public Object segmentBodyUpdate(SegmentBodyUpdateIn body,
			SecurityContext securityContext) {
		BaseOutput baseOutput = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
				ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO, null);

		Integer headerId = Integer.valueOf(body.getSegmentHeadId());
		// 删除既有body数据
		segmentationHeadDao.batchDeleteUseHeaderId(headerId);

		// 插入新的Body数据
		List<SegmentBodyFilterGroupIn> filterGroups = body.getFilterGroups();
		List<SegmentBodyTagsIn> tags = null;
		if (filterGroups != null) {
			for (SegmentBodyFilterGroupIn filterGroup : filterGroups) {
				Integer groupIndex = filterGroup.getGroupIndex();
				tags = filterGroup.getTagList();
				if (tags != null) {
					for (SegmentBodyTagsIn tag : tags) {
						SegmentationBody insertBody = new SegmentationBody();
						insertBody.setHeadId(headerId);
						insertBody.setTagGroupId(tag.getTagGroupId());
						insertBody.setTagId(tag.getTagId());
						insertBody.setExclude(tag.getExclude().byteValue());
						insertBody.setCreateTime(new Date());
						insertBody.setGroupIndex(groupIndex);
						insertBody
								.setStatus(ApiConstant.TABLE_DATA_STATUS_VALID);
						segmentationHeadDao.insert(insertBody);
					}
				}
			}
		}
		return baseOutput;
	}

}
