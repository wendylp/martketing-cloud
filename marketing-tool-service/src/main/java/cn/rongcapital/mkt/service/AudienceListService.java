package cn.rongcapital.mkt.service;

import cn.rongcapital.mkt.vo.BaseOutput;

public interface AudienceListService {

	public BaseOutput audienceList(String userToken,Integer size,Integer index);

}
