/**
 * 
 */

package cn.rongcapital.mkt.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.hibernate.validator.constraints.NotBlank;

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
	String sendSms(@NotBlank String sms);

	@POST
	@Path("/mult")
	String sendMultSms(@NotBlank String sms);

}
