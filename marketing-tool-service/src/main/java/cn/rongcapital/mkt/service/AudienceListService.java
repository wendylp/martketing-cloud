package cn.rongcapital.mkt.service;

import java.util.List;

import cn.rongcapital.mkt.vo.BaseOutput;

public interface AudienceListService {

    public BaseOutput audienceList(String userToken, Integer size, Integer index, Integer orgId, Boolean firsthand);

    /**
     * mkt.audience.listbyid.get
     * 
     * 获取人群list列表
     * 
     * @author nianjun
     * @param userToken
     * @param audienceId
     * @param size
     * @param index
     * @return
     */
    public BaseOutput getAudienceByListId(String userToken, Integer audienceId, Integer size, Integer index);

    BaseOutput audienceCount(String userToken, Integer orgId, Boolean firsthand);
    
    boolean saveAudienceByMobile(Long taskHeadId, List<String> mobileList, String audienceName );

}
