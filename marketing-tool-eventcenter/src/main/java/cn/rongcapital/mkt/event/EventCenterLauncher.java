/**
 * 
 */
package cn.rongcapital.mkt.event;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author shangchunming
 *
 */
@SpringBootApplication
@EnableTransactionManagement
@ImportResource(value = { "classpath:event-center.xml" })
@PropertySource(value = { "classpath:${conf.dir}/event-center.properties" })
public class EventCenterLauncher {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(EventCenterLauncher.class, args);
	}

}
