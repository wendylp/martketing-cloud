package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;

public interface AudienceSearchService {

	public BaseOutput audienceByName(String userToken,String audience_type,int audience_id,String audience_name,Integer size,Integer index);

}
