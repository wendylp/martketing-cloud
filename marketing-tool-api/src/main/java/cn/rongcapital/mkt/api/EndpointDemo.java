/*
 * Copyright 2012-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.rongcapital.mkt.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import cn.rongcapital.mkt.service.DemoService;

@Component
@Path("/hello")
public class EndpointDemo {
	@Autowired
	DemoService demoService;
	@Autowired
	heracles.data.datasource.ReadWriteDataSource metads;
	@Value("${message:World}")
	private String msg;

	
	@GET
	@Path("/message")
	public String message() {
		int res = demoService.getAll();
		return "Hello " + msg+","+res;
	}
	
//	@GET
//	@Path("/message2")
//	@Produces("application/json")
//	public String message2() {
//		String res = demoService.getAll2();
//		return "Hello " + msg+","+res;
//	}
//	@GET
//	@Path("/message3")
//	@Produces("application/json")
//	public String message3() {
//		String res = demoService.getAll3();
//		return "Hello " + msg+","+res;
//	}
//	@GET
//	@Path("/message4")
//	@Produces("application/json")
//	public String message4() {
//		System.out.println(metads);
//		String res = demoService.getAll4();
//		return "Hello " + msg+","+res;
//	}
//	@GET
//	@Path("/message5")
//	@Produces("application/json")
//	public String message5() {
//		String res = demoService.getAll5()+"";
//		return "Hello " + msg+","+res;
//	}
	@GET
	@Path("/message6")
	@Produces("application/json")
	public String insert() {
		String res = demoService.insert()+"";
		return "Hello " + msg+","+res;
	}
	
	@GET
	@Path("/message7")
	@Produces("application/json")
	public String getAll7() {
		demoService.getAll7("12v");
		return "Hello " + msg;
	}
}