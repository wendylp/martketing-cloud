package cn.rongcapital.mkt.dao.base;

import java.util.List;
import java.util.Map;

public interface BaseDataFilterDao<T> {

    /**
     * @功能简述: 查询根据作业任务id过滤后的数据
     * @param: Map paramMap
     * @return: List<T>
     */
    List<T> selectByBatchId(Map<String, Object> paramMap);
    
    /**
     * @功能简述: 查询根据作业任务id过滤后的数据数量
     * @param: Map paramMap
     * @return: List<T>
     */
    
    Integer selectCountByBatchId(Map<String, Object> paramMap);
    
    /**
     * @功能简述: 查询一个表里的所有字段
     * @return: List<String>
     */
    List<String> selectColumns();
    
    /**
     * @功能简述: 查询一个表里的mobile字段
     * @return: List<String>
     */
    String selectMobileById(Integer id);
}
