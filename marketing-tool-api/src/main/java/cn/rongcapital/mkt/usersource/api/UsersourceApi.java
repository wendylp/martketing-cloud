/*************************************************
 * @功能简述: API用户来源
 * @项目名称: marketing cloud
 * @see:
 * @author: 单璟琦
 * @version: 0.0.1
 * @date: 2017/03/02
 * @复审人:
 *************************************************/
package cn.rongcapital.mkt.usersource.api;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.usersource.UsersourceFlieUploadGetService;
import cn.rongcapital.mkt.usersource.service.UsersourceService;
import cn.rongcapital.mkt.usersource.vo.in.UsersourceAvailableIn;
import cn.rongcapital.mkt.usersource.vo.in.UsersourceIn;
import cn.rongcapital.mkt.vo.BaseOutput;

@Component
@Path(ApiConstant.API_PATH)
@Produces({ MediaType.APPLICATION_JSON })
@ValidateRequest
@PropertySource("classpath:${conf.dir}/application-api.properties")
public class UsersourceApi {

	private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UsersourceService usersourceService;
    
    /**
     * 添加用户来源
     * 
     * 接口：mkt.source.save
     * 
     * @param UsersourceIn
     * @return BaseOutput
     * @author shanjingqi
     * @Date 2017-03-02
     */
    @POST
    @Path("/mkt.source.save")
    public BaseOutput saveUsersourceClassification(@Valid UsersourceIn in){
    	return usersourceService.saveUsersource(in);
    }
    
    @Autowired
    private UsersourceFlieUploadGetService usersourceFlieUploadGetService;
	
    /**
     * 获取文件上传路径
     * 接口：mkt.usersource.file.upload.get
     * 
     * @return BaseOutput
     * @author guozhenchao
     * @Date 2017-03-01
     */
    @GET
    @Path("/mkt.usersource.file.upload.get")
    public BaseOutput getUserSourceCount(@NotEmpty @QueryParam("user_token") String userToken,
            @NotEmpty @QueryParam("ver") String ver, @NotEmpty @QueryParam("user_id") String userId){
    	return usersourceFlieUploadGetService.getUsersourceUploadUrlGet();
    }
    
    /**
     * 文件上传
     * 接口：mkt.usersource.file.upload
     * @param file_input
     * @return BaseOutput
     * @author guozhenchao
     * @Date 2017-03-01
     */
    @POST
    @Path("/mkt.usersource.file.upload")
    @Consumes("multipart/form-data")
    public BaseOutput fileUpload(MultipartFormDataInput input){
        return usersourceFlieUploadGetService.uploadFile(input);
    }
    
    /**
     * 文件上传数据导入
     * 接口：mkt.usersource.data.import
     * @param file_id
     * @return BaseOutput
     * @author guozhenchao
     * @Date 2017-03-01
     */
    @GET
    @Path("/mkt.usersource.data.import")
    public BaseOutput importUsersourceDate(@NotEmpty @QueryParam("user_token") String userToken,
            @NotEmpty @QueryParam("ver") String ver, @NotEmpty @QueryParam("user_id") String userId, @NotEmpty @QueryParam("file_id") String fileId){
    	return usersourceFlieUploadGetService.importUsersourceDate(fileId);
    }
    
    /**
     * 用户来源分类校验
     * 接口：mkt.usersource.classification.check
     * @return BaseOutput
     * @author guozhenchao
     * @Date 2017-03-01
     */
    @GET
    @Path("/mkt.usersource.classification.check")
    public BaseOutput usersourceCheck(@NotEmpty @QueryParam("user_token") String userToken,
            @NotEmpty @QueryParam("ver") String ver, @NotEmpty @QueryParam("user_id") String userId){
    	return usersourceFlieUploadGetService.usersourceCheck();
    }
    
    /**
     * 用户来源列表
     * 接口：mkt.source.list
     * @return BaseOutput
     * @author shanjingqi
     * @Date 2017-03-03
     */
    @GET
    @Path("/mkt.source.list")
	public BaseOutput usersourceList(@NotNull @QueryParam("id") Long id, @QueryParam("index") Integer index,
			@QueryParam("size") Integer size) {
		return usersourceService.getUsersourceList(id, index, size);
	}
    
    /**
     * 禁用/启用来源
     * 接口：mkt.source.available
     * @param Long id
     * @param Long available
     * @return BaseOutput
     * @author shanjingqi
     * @Date 2017-03-07
     */
    @POST
    @Path("/mkt.source.available")
	public BaseOutput usersourceAvailable(@Valid UsersourceAvailableIn in) {
		return usersourceService.usersourceAvailable(in);
	}
}
