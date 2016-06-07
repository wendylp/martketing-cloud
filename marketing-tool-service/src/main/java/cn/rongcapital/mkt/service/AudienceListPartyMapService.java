package cn.rongcapital.mkt.service;
import javax.ws.rs.core.SecurityContext;

public interface AudienceListPartyMapService {
	public Object audienceListDel(String userToken, Integer audience_list_id,SecurityContext securityContext);
}
