/*************************************************
 * @功能简述:
 * @see: MkyApi
 * @author: chengjincheng
 * @version: 1.0
 * @date: 2016/9/13
 *************************************************/

package cn.rongcapital.mkt.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.DataPopulationDao;
import cn.rongcapital.mkt.po.DataPopulation;
import cn.rongcapital.mkt.po.mongodb.Segment;
import cn.rongcapital.mkt.service.SegmentSearchGetService;
import cn.rongcapital.mkt.vo.BaseOutput;
import cn.rongcapital.mkt.vo.in.SegmentSearchIn;
import heracles.data.common.annotation.ReadWrite;
import heracles.data.common.util.ReadWriteType;

@Service
public class SegmentSearchGetServiceImpl implements SegmentSearchGetService {

	@Autowired
	DataPopulationDao dataPopulationDao;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	@ReadWrite(type = ReadWriteType.READ)
	public BaseOutput SegmentSearch(Integer head_id, String query_name) {

		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
				ApiConstant.INT_ZERO, null);

		Query query = new Query(Criteria.where("segmentation_head_id").is(head_id));
		List<Segment> segmentList = mongoTemplate.find(query, Segment.class);
		List<Integer> head_ids = new ArrayList<>();
		if (CollectionUtils.isNotEmpty(segmentList)) {
			for (Segment segment : segmentList) {
				head_ids.add(segment.getDataId());
			}
		}

		if (CollectionUtils.isNotEmpty(head_ids)) {
			SegmentSearchIn searchIn = new SegmentSearchIn();
			searchIn.setHeadidList(head_ids);
			searchIn.setQueryName(query_name);

			List<DataPopulation> dataList = dataPopulationDao.segmentSearch(searchIn);
			if (CollectionUtils.isNotEmpty(dataList)) {
				result.setTotal(dataList.size());
				for (DataPopulation data : dataList) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("name", data.getName());
					result.getData().add(map);
				}
			}
		}
		

		return result;
	}
}