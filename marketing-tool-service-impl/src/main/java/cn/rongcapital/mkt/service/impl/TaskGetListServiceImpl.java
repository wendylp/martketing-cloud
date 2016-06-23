package cn.rongcapital.mkt.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import cn.rongcapital.mkt.common.enums.TaskStatusEnum;
import cn.rongcapital.mkt.dao.TaskRunLogDao;
import cn.rongcapital.mkt.po.TaskRunLog;
import cn.rongcapital.mkt.service.TaskGetListService;
import cn.rongcapital.mkt.vo.BaseOutput;

public class TaskGetListServiceImpl implements TaskGetListService {

    @Autowired
    private TaskRunLogDao taskRunLogDao;

    @Override
    public BaseOutput getTaskList() {
        List<TaskRunLog> taskRunLogs = taskRunLogDao.selectList(null);
        List<Map<String, Object>> resultList = new ArrayList<>();

        if (!CollectionUtils.isEmpty(taskRunLogs)) {
            for (TaskRunLog taskRunLog : taskRunLogs) {
                Map<String, Object> map = new HashMap<>();
                map.put("task_name", taskRunLog.getTaskName());
                map.put("createtime", taskRunLog.getCreateTime());
                map.put("task_status", TaskStatusEnum.getDescriptionByStatus(taskRunLog.getStatus()));
                resultList.add(map);
            }

        }
        return null;
    }

}
