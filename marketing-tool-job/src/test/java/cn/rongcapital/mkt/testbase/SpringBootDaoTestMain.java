/*************************************************
 * @功能简述: spring boot dao test启动主类
 * @项目名称: marketing cloud
 * @see: 
 * @author: 
 * @version: 
 * @date: 
 * @复审人: 
*************************************************/

package cn.rongcapital.mkt.testbase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;



@SpringBootApplication
@EnableTransactionManagement
@ImportResource({"classpath:spring/spring-config-mybatis-heracles.xml"})
@PropertySource(value = {"classpath:conf_junit/application-api.properties","classpath:conf_junit/application-dao.properties"})
@EnableMongoRepositories(basePackages ="cn.rongcapital.mkt.mongodb.event")
@ComponentScan(basePackages = { "cn.rongcapital.mkt.job.service.impl.event" })
public class SpringBootDaoTestMain {
	
	/**
	 * @功能简述: 启动spring boot工程
	 * @param: args 
	 * @return: void
	 */
    public static void main(String[] args) {
	    SpringApplication.run(SpringBootDaoTestMain.class, args);
    }
}

