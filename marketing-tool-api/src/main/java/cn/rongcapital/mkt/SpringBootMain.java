/*************************************************
 * @功能简述: spring boot启动主类
 * @项目名称: marketing cloud
 * @see: 
 * @author: 宋世涛
 * @version: 0.0.1
 * @date: 2016/5/16
 * @复审人: 
*************************************************/

package cn.rongcapital.mkt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@ImportResource({"classpath:spring/spring-config-mybatis-heracles.xml"})
//@ImportResource({"classpath:spring/spring-config-mybatis-heracles.xml","classpath:spring/spring-config-job.xml"})
@PropertySource(value = {"${conf.dir}/application-api.properties","${conf.dir}/application-dao.properties"})
public class SpringBootMain {
	
	/**
	 * @功能简述: 启动spring boot工程
	 * @param: args 
	 * @return: void
	 */
    public static void main(String[] args) {
	    SpringApplication.run(SpringBootMain.class, args);
    }
}

