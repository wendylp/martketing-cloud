package cn.rongcapital.mkt.service;

public interface DataGetQualityListService {

    /**
     * 数据质量报告
     * 
     * @author nianjun
     * @功能简述 : mkt.data.quality.list.get
     * @param method
     * @param userToken
     * @param index
     * @param size
     * @param ver
     * @return
     */
    public Object getQualityList(String method, String userToken, Integer index, Integer size,
                    String ver);
}
