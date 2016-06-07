package cn.rongcapital.mkt.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.TagDao;
import cn.rongcapital.mkt.dao.TaskRunLogDao;
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
		int tagCount = tagDao.selectAllListCount();
		List<TaskRunLog> taskRunLogList = taskRunLogDao.selectLastOne();
		BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(),
				   ApiErrorCode.SUCCESS.getMsg(),
				   ApiConstant.INT_ONE,null);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("tag_count", tagCount);
		if(null != taskRunLogList && taskRunLogList.size() > 0){
			map.put("sync_time", taskRunLogList.get(0).getEndTime());
		}
		result.getData().add(map);
		return result;
	}

}
