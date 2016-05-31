package cn.rongcapital.mkt.common.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.springframework.stereotype.Component;

import cn.rongcapital.mkt.common.constant.ApiErrorCode;
import cn.rongcapital.mkt.vo.BaseOutput;

@Component
@Provider
public class GeneralExceptionMapper implements ExceptionMapper<Exception> {
	
	/**
	 * @功能简述: 系统异常后返回错误码
	 * @param: Exception cex
	 * @return: Response
	 */
	@Override
    public Response toResponse(Exception cex) {
        BaseOutput error = new BaseOutput(ApiErrorCode.SYSTEM_ERROR.getCode(),
        		                          ApiErrorCode.SYSTEM_ERROR.getMsg(),0,null);
        return Response.ok().entity(error).build();
    }

}
