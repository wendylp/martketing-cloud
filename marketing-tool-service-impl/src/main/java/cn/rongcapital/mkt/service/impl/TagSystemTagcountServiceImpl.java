package cn.rongcapital.mkt.service.impl;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.TagDao;
import cn.rongcapital.mkt.dao.TaskRunLogDao;
import cn.rongcapital.mkt.po.Tag;
import cn.rongcapital.mkt.po.TaskRunLog;
import cn.rongcapital.mkt.service.TagSystemTagcountService;
import cn.rongcapital.mkt.vo.BaseOutput;

@Service
public class TagSystemTagcountServiceImpl implements TagSystemTagcountService {

	@Autowired
	TagDao tagDao;

	@Autowired
	TaskRunLogDao taskRunLogDao;

	@Override
	public BaseOutput getTagcount(String method, String userToken) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Tag tag = new Tag();
		int tagCount = tagDao.selectListCount(tag);
		TaskRunLog taskRunLog = new TaskRunLog();
		taskRunLog.setOrderField("end_time");
		taskRunLog.setOrderFieldType("DESC");
		taskRunLog.setStartIndex(0);
		taskRunLog.setPageSize(1);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("count", 1);
		List<TaskRunLog> taskRunLogList = taskRunLogDao.selectList(taskRunLog);
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
				ApiErrorCode.SUCCESS.getMsg(), ApiConstant.INT_ONE, null);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tag_count", tagCount);
		if (null != taskRunLogList && taskRunLogList.size() > 0) {
			map.put("sync_time", format.format(taskRunLogList.get(0).getEndTime()));
		}
		result.getData().add(map);
		return result;
	}

}
