/**
 * 
 */

package cn.rongcapital.mkt.service.impl.vo;

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
@Path("/")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
public interface SmsApi {

	/**
	 * 单条发送短信
	 * 
	 * @param sms
	 * @return
	 */
	@POST
	@Path("/single")
	SmsStatusResponse sendSms(@Valid Sms sms);

	/**
	 * 批量发送短信
	 * 
	 * @param multSms
	 * @return
	 */
	@POST
	@Path("/mult")
	SmsStatusResponse sendMultSms(@Valid List<Sms> multSms);
}
