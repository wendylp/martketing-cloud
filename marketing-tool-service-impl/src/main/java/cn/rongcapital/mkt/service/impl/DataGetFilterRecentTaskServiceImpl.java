package cn.rongcapital.mkt.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import cn.rongcapital.mkt.common.enums.TaskConditionEnum;
import cn.rongcapital.mkt.dao.TaskRunLogDao;
import cn.rongcapital.mkt.po.TaskRunLog;
import cn.rongcapital.mkt.service.DataGetFilterRecentTaskService;

public class DataGetFilterRecentTaskServiceImpl implements DataGetFilterRecentTaskService {

    @Autowired
    private TaskRunLogDao taskRunLogDao;

    @Override
    public List<TaskRunLog> getFilterRecntTask(String method, String userToken, String ver,
                    String condition) {

        if (StringUtils.isEmpty(condition)) {
            return null;
        }

        TaskRunLog paramTaskRunLog = new TaskRunLog();
        paramTaskRunLog.setEndTime(TaskConditionEnum.getEnumByAbbreviation(condition).getTime());
        List<TaskRunLog> taskRunLogList = taskRunLogDao.selectByEndtime(paramTaskRunLog);

        return taskRunLogList;
    }

}
