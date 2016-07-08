package cn.rongcapital.mkt.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.dao.TaskRunLogDao;
import cn.rongcapital.mkt.po.TaskRunLog;
import cn.rongcapital.mkt.service.TaskGetListService;
import cn.rongcapital.mkt.vo.BaseOutput;

@Service
public class TaskGetListServiceImpl implements TaskGetListService {

    @Autowired
    private TaskRunLogDao taskRunLogDao;

    @Override
    public BaseOutput getTaskList() {
        TaskRunLog paramTaskRunLog = new TaskRunLog();
        paramTaskRunLog.setOrderField("start_time");
        paramTaskRunLog.setOrderFieldType("DESC");
        List<TaskRunLog> taskRunLogs = taskRunLogDao.selectList(paramTaskRunLog);
        List<Map<String, Object>> resultList = new ArrayList<>();
        int totalCount = 0;

        if (!CollectionUtils.isEmpty(taskRunLogs)) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (TaskRunLog taskRunLog : taskRunLogs) {
                Map<String, Object> map = new HashMap<>();
                map.put("task_name", taskRunLog.getTaskName());
                map.put("createtime", simpleDateFormat.format(taskRunLog.getCreateTime()));
                map.put("task_status", taskRunLog.getEndTime() == null ? "已完成" : "进行中");
                resultList.add(map);
            }

        }

        totalCount = taskRunLogDao.selectListCount(null);
        BaseOutput result = new BaseOutput(ApiErrorCode.SUCCESS.getCode(), ApiErrorCode.SUCCESS.getMsg(),
                        ApiConstant.INT_ZERO, null);
        result.getData().addAll(resultList);
        result.setTotalCount(totalCount);
        result.setTotal(resultList.size());

        return result;
    }

}
