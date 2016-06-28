package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;

public interface DataDownloadMainListService {

    /**
     * mkt.data.main.list.get
     * 
     * @author nianjun
     * @功能简述 : 获取主数据列表
     * @param method
     * @param userToken
     * @param dataType
     * @param index
     * @param size
     * @param ver
     * @return
     */
    public BaseOutput getMainList(String method, String userToken, Integer dataType, Integer index,
                    Integer size, String ver);
}
