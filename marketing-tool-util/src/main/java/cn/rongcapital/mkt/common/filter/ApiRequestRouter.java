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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.List;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Provider;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.CharEncoding;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import cn.rongcapital.mkt.common.constant.ApiConstant;

@Component
@Provider
@PreMatching
public class ApiRequestRouter implements ContainerRequestFilter {

	/**
	 * @功能简述: 根据传入的method参数把请求转发到相应接口
	 * @param: ContainerRequestContext requestContext
	 * @return: Response
	 */
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		String url = requestContext.getUriInfo().getPath();
		if(StringUtils.isBlank(url) || !url.equals(ApiConstant.API_PATH)){
			return;
		}
		String method = null;
		if(HttpMethod.GET.equals(requestContext.getMethod())) { 
			List<String> pList = requestContext.getUriInfo().getQueryParameters()
								 .get(ApiConstant.API_METHOD);
			method = pList==null?null:pList.get(0);
		}
	    if(HttpMethod.POST.equals(requestContext.getMethod()) 
	                && MediaType.APPLICATION_JSON_TYPE.equals(requestContext.getMediaType())
	                && requestContext.getEntityStream() != null) {
	    	 try {
	    		 ByteArrayOutputStream out = new ByteArrayOutputStream();
	    		 IOUtils.copy(requestContext.getEntityStream(), out);
	    		 byte[] requestEntity = out.toByteArray();
	    		 String jsonStr = new String(requestEntity,CharEncoding.UTF_8);
	    		 method = (String)JSON.parseObject(jsonStr).get(ApiConstant.API_METHOD);
	    		 requestContext.setEntityStream(new ByteArrayInputStream(requestEntity));
			} catch (Exception e) {
				 method = null;
			}
		}
	    if(StringUtils.isBlank(method)){
	    	return;
	    }
	    URI newRequestURI = requestContext.getUriInfo().getBaseUriBuilder()
	    		            .path(requestContext.getUriInfo().getPath()+"/"+method).build();
		requestContext.setRequestUri(newRequestURI);
	}

}
