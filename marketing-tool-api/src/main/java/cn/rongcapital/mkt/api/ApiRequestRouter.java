package cn.rongcapital.mkt.api;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;

import org.springframework.stereotype.Component;

@Component
@Provider
public class ApiRequestRouter implements ContainerRequestFilter {
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
//		String method = requestContext;
//		System.out.println(requestContext.getHeaders().);
		
	}

}
