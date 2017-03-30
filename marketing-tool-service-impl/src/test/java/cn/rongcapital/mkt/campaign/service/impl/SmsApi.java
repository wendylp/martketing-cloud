/**
 * 
 */
package cn.rongcapital.mkt.campaign.service.impl;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author LiuQ
 *
 */
@Path("/sms")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
public interface SmsApi {

	@POST
	@Path("/single")
	String sendSms(String receive, String msg);

	@POST
	@Path("/mult_same")
	String sendMultSms(List<String> receives, String msg);

	@POST
	@Path("/mult_diff")
	String sendMultSms(List<String> receives, List<String> msgs);

}
