/*************************************************
 * @功能简述: 获取细分关联的标签
 * @see MktApi：
 * @author: 朱学龙
 * @version: 1.0
 * @date：2016-06-07
 *************************************************/
package cn.rongcapital.mkt.service.impl;

import heracles.data.common.annotation.ReadWrite;
import heracles.data.common.util.ReadWriteType;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.CustomTagMapDao;
import cn.rongcapital.mkt.po.CustomTagWithName;
import cn.rongcapital.mkt.service.SegmentTagGetService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.out.SegmentTagGetOut;

@Service
public class SegmentTagGetServiceImpl implements SegmentTagGetService {

	@Autowired
	CustomTagMapDao customTagMapDao;

	@ReadWrite(type = ReadWriteType.READ)
	@Override
	public BaseOutput getSegmentTag(String userToken, String segmentHeadId) {
		BaseOutput baseOutput = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
				ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ZERO, null);
		List<CustomTagWithName> data = customTagMapDao.getTagUseHeadId(Long
				.valueOf(segmentHeadId));
		if (data != null) {
			List<Object> result = new ArrayList<Object>();
			SegmentTagGetOut out = null;
			for (CustomTagWithName tags : data) {
				out = new SegmentTagGetOut();
				out.setTagId(tags.getId());
				out.setTagName(tags.getName());
				result.add(out);
			}
			baseOutput.setData(result);
			baseOutput.setTotal(result.size());
		}
		return baseOutput;
	}

}
