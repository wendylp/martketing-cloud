package cn.rongcapital.mkt.usersource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.hibernate.validator.constraints.NotEmpty;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.jboss.resteasy.plugins.validation.hibernate.ValidateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.vo.BaseOutput;

@Component
@Path(ApiConstant.API_PATH)
@Produces({ MediaType.APPLICATION_JSON })
@ValidateRequest
public class UsersourceApi {
	
    @Autowired
    private UsersourceFlieUploadGetService usersourceFlieUploadGetService;
	
    @GET
    @Path("/mkt.usersource.file.upload.get")
    public BaseOutput getMaterialCouponCount(@NotEmpty @QueryParam("user_token") String userToken,
            @NotEmpty @QueryParam("ver") String ver, @NotEmpty @QueryParam("user_id") String userId){
    	return usersourceFlieUploadGetService.getUsersourceUploadUrlGet();
    }
    
    @POST
    @Path("/mkt.usersource.file.upload")
    @Consumes("multipart/form-data")
    public BaseOutput fileUpload(MultipartFormDataInput input){
        return usersourceFlieUploadGetService.uploadFile(input);
    }
}
