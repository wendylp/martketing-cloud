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
import org.springframework.boot.autoconfigure.mongo.MongoDataAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(exclude={MongoDataAutoConfiguration.class})
@EnableTransactionManagement
@PropertySource(value = { "classpath:${conf.dir}/application-api.properties",
                          "classpath:${conf.dir}/application-dao.properties", "classpath:${conf.dir}/datatag-agent.properties",
                          "classpath:${conf.dir}/caas-settings.properties"})
@ImportResource({"classpath:spring/spring-config-mybatis-heracles.xml","classpath:spring/spring-config-schedule.xml","classpath:caas-mc.xml"})
//@ImportResource({"classpath:spring/spring-config-mybatis-heracles.xml","classpath:spring/spring-config-job.xml"})

@ComponentScan(basePackages = { "cn.rongcapital.mkt.*", "cn.rongcapital.mc.datatag.*" })
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
