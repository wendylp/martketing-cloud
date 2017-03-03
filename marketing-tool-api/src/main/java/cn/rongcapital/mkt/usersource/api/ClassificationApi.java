/*************************************************
 * @功能简述: API用户来源分类
 * @项目名称: marketing cloud
 * @see:
 * @author: 单璟琦
 * @version: 0.0.1
 * @date: 2017/03/01
 * @复审人:
 *************************************************/
package cn.rongcapital.mkt.usersource.api;

import javax.validation.Valid;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.plugins.validation.hibernate.ValidateRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import cn.rongcapital.mkt.common.constant.ApiConstant;
import cn.rongcapital.mkt.usersource.service.UsersourceClassificationService;
import cn.rongcapital.mkt.usersource.vo.in.UsersourceClassificationIn;
import cn.rongcapital.mkt.vo.BaseOutput;

@Component
@Path(ApiConstant.API_PATH)
@Produces({ MediaType.APPLICATION_JSON })
@ValidateRequest
@PropertySource("classpath:${conf.dir}/application-api.properties")
public class ClassificationApi {
	 
	private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UsersourceClassificationService usersourceClassificationService;
    
    /**
     * 添加用户来源的分类
     * 
     * 接口：mkt.classification.save
     * 
     * @param name
     * @param id
     * @return BaseOutput
     * @author shanjingqi
     * @Date 2017-03-01
     */
    @POST
    @Path("/mkt.classification.save")
    public BaseOutput saveUsersourceClassification(@Valid UsersourceClassificationIn in){
    	return usersourceClassificationService.saveUsersourceClassification(in);
    }
    
    /**
     * 用户分类列表
     * 
     * 接口：mkt.classification.list 
     * 
     * @return BaseOutput
     * @author shanjingqi
     * @Date 2017-03-01
     */
    @POST
    @Path("/mkt.classification.list")
    public BaseOutput classificationList(){
    	return usersourceClassificationService.classificationList();
    }
}