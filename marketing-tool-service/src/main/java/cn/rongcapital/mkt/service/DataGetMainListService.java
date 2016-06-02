package cn.rongcapital.mkt.service;

public interface DataGetMainListService {

    /**
     * 获取主数据列表
     * 
     * @author nianjun
     * @功能简述: mkt.segment.publishstatus.list.get
     * @param: String method, String userToken, Integer index, Integer size, String ver
     * @return Object
     */
    public Object getMainList(String method, String userToken, Integer dataType, Integer index,
                    Integer size, String ver);
}
