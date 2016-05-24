package cn.rongcapital.mkt.api;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.jboss.resteasy.api.validation.ResteasyViolationException;
import org.springframework.stereotype.Component;
import cn.rongcapital.mkt.util.ApiErrorCode;
import cn.rongcapital.mkt.vo.BaseOutput;

@Component
@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ResteasyViolationException> {
	@Override
    public Response toResponse(ResteasyViolationException cex) {
        BaseOutput error = new BaseOutput();
        error.setMsg(ApiErrorCode.PARAMETER_ERROR.getMsg());
        error.setCode(ApiErrorCode.PARAMETER_ERROR.getCode());
        return Response.ok().entity(error).build();
    }
}