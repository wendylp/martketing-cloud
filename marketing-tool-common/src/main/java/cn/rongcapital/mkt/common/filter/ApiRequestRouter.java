/*************************************************
 * @功能简述: API接口根据传入的method参数把请求转发到相应接口
 * @项目名称: marketing cloud
 * @see: 
 * @author: 宋世涛
 * @version: 0.0.1
 * @date: 2016/5/16
 * @复审人: 
*************************************************/

package cn.rongcapital.mkt.common.filter;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.List;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;



import cn.rongcapital.mkt.common.constant.ApiConstant;

@Component
@Provider
@PreMatching
public class ApiRequestRouter implements ContainerRequestFilter {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * @功能简述: 根据传入的method参数把请求转发到相应接口
	 * @param: ContainerRequestContext requestContext
	 * @return: Response
	 */
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {	
		String url = requestContext.getUriInfo().getPath();
		String appId = "";
		if(StringUtils.isNotEmpty(url)&&url.contains(ApiConstant.API_PATH)){
			if(url.length()>=5){
				appId = url.substring(5);
			}							
		}else{
			requestContext.abortWith(Response.status(404).entity("Api not found").build());
		}

		if(HttpMethod.GET.equals(requestContext.getMethod()) ||(HttpMethod.POST.equals(requestContext.getMethod()))) { 
			List<String> pList = requestContext.getUriInfo().getQueryParameters()
								 .get(ApiConstant.API_METHOD);
			String method = pList==null?null:pList.get(0);
			if(StringUtils.isBlank(method)){
				requestContext.abortWith(Response.status(404).entity("Api method not found").build());
			}
			if(StringUtils.isNotEmpty(appId)){
				URI newRequestURI = requestContext.getUriInfo().getBaseUriBuilder()
						.path(ApiConstant.API_PATH+"/"+method+"/"+appId).build();
				requestContext.setRequestUri(newRequestURI);				
			}else{
				URI newRequestURI = requestContext.getUriInfo().getBaseUriBuilder()
						.path(ApiConstant.API_PATH+"/"+method).build();
				requestContext.setRequestUri(newRequestURI);
			}
		}
	}

}
