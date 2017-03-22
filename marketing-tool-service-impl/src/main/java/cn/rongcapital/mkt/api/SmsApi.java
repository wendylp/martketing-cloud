/**
 * 
 */

package cn.rongcapital.mkt.api;

import java.util.List;

import javax.validation.Valid;
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
	SmsStatusResponse sendSms(@Valid Sms sms);

	@POST
	@Path("/mult")
	SmsStatusResponse sendMultSms(@Valid List<Sms> multSms);
}
