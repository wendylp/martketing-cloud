/*************************************************
 * @功能简述: spring boot dao test启动主类
 * @项目名称: marketing cloud
 * @see: 
 * @author: 
 * @version: 
 * @date: 
 * @复审人: 
*************************************************/

package cn.rongcapital.mkt.mongodao.testbase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
//@ImportResource({"classpath:spring/spring-config-mybatis-heracles.xml"})
//@ImportResource({"classpath:spring/spring-config-mybatis-heracles.xml","classpath:spring/spring-config-schedule.xml"})
//@ImportResource({"classpath:spring/spring-config-mybatis-heracles.xml","classpath:spring/spring-config-job.xml"})
@PropertySource(value = {"classpath:conf_junit/application-api.properties","classpath:conf_junit/application-dao.properties"})
//@EnableMongoRepositories(basePackages ="cn.rongcapital.mkt")
@ComponentScan(basePackages ="cn.rongcapital.mkt")

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

