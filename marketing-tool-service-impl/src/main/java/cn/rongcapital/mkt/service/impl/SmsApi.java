/**
 * 
 */
package cn.rongcapital.mkt.service.impl;

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
	String sendSms(String data);

	@POST
	@Path("/mult_same")
	String sendMultSmsSame(String data);

	@POST
	@Path("/mult_diff")
	String sendMultSmsDiff(String data);

}
