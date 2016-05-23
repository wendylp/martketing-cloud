package cn.rongcapital.mkt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SpringBootApplication
@ImportResource({"classpath:spring/spring-config-mybatis-heracles.xml"})
//@ImportResource({"classpath:spring/spring-config-mybatis-heracles.xml","classpath:spring/spring-config-job.xml"})
@PropertySource(value = {"${conf.dir}/application-api.properties","${conf.dir}/application-dao.properties"})
public class SampleResteasyApplication {
	public static void main(String[] args) {
//		System.setProperty(
//		          "spring.datasource.initialize", "false");
		
		SpringApplication.run(SampleResteasyApplication.class, args);
		
//		String[] configs = new String[]{"classpath:spring/spring-config-job.xml"};
//		ApplicationContext ctx = new ClassPathXmlApplicationContext(configs);
//		Object o = ctx.getBean("myElasticJob");
//		System.out.println(o);
		
	}
}

