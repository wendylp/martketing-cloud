package cn.rongcapital.mkt.dao.base;

import java.util.List;
import java.util.Map;

public interface BaseDataFilterDao<T> {

    /**
     * @功能简述: 查询根据作业任务id过滤后的数据
     * @param: Map paramMap
     * @return: List<T>
     */
    List<T> selectByTaskId(Map<String, Object> paramMap);
}
