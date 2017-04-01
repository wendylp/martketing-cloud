package cn.rongcapital.mkt.job.test;

import java.io.IOException;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import cn.rongcapital.mkt.job.service.DataPartySyncService;
import cn.rongcapital.mkt.job.service.SystemTagSynchService;
import cn.rongcapital.mkt.job.service.base.TaskService;
import cn.rongcapital.mkt.job.service.impl.DataPartySyncMongoTaskServiceImpl;
import cn.rongcapital.mkt.job.service.impl.DataPopulationToDataPartyImpl;
import cn.rongcapital.mkt.job.service.impl.DataShoppingToDataPartyImpl;
import cn.rongcapital.mkt.job.service.impl.OriginalDataArchPointScheduleServiceImpl;
import cn.rongcapital.mkt.job.service.impl.OriginalDataPopulationServiceImpl;
import cn.rongcapital.mkt.job.service.impl.OriginalDataShoppingScheduleServiceImpl;
import cn.rongcapital.mkt.job.tag.service.impl.InitTagValueCountServiceImpl;
import cn.rongcapital.mkt.job.tag.service.impl.SystemTagSynMongodbServiceImpl;
import cn.rongcapital.mkt.service.OriginalDataArchPointScheduleService;
import cn.rongcapital.mkt.service.OriginalDataPopulationService;
import cn.rongcapital.mkt.service.OriginalDataShoppingScheduleService;

//@Configuration
//@EnableAutoConfiguration
@ImportResource({ "classpath:spring/spring-config-mybatis-heracles.xml" })
@PropertySource(value = { "classpath:conf_mockup/application-api.properties",
        "classpath:conf_mockup/application-dao.properties" })
@ComponentScan(basePackages = { "cn.rongcapital.mkt.dao" })
public class GlobalConfiguration {

    @Bean
    public PropertyPlaceholderConfigurer ppc() throws IOException {
        PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
        ppc.setLocations(new ClassPathResource("conf_mockup/application-api.properties"),
                new ClassPathResource("conf_mockup/application-dao.properties"));
        ppc.setIgnoreUnresolvablePlaceholders(true);
        return ppc;
    }

    @Bean
    public OriginalDataArchPointScheduleService getOriginalDataArchPointScheduleService() {
        return new OriginalDataArchPointScheduleServiceImpl();
    }

    @Bean
    public OriginalDataPopulationService getOriginalDataPopulationService() {
        return new OriginalDataPopulationServiceImpl();
    }

    @Bean
    public OriginalDataShoppingScheduleService getOriginalDataShoppingScheduleService() {
        return new OriginalDataShoppingScheduleServiceImpl();
    }

    @Bean(name = "dataPopulationToDataParty")
    public DataPartySyncService<Integer> getDataPopulationToDataParty() {
        return new DataPopulationToDataPartyImpl();
    }

    @Bean(name = "dataShoppingToDataParty")
    public DataPartySyncService<Integer> getDataShoppingToDataParty() {
        return new DataShoppingToDataPartyImpl();
    }

    @Bean
    public ThreadPoolTaskExecutor getThreadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(10);
        executor.initialize();
        return executor;
    }

    @Bean
    public SystemTagSynchService getSystemTagSynchService() {
        return new InitTagValueCountServiceImpl();
    }

    @Bean(name = "dataPartySyncMongoTaskService")
    public TaskService getDataPartySyncMongoTaskService() {
        return new DataPartySyncMongoTaskServiceImpl();
    }

    @Bean(name = "systemTagSynMongodbService")
    public TaskService getSystemTagSynMongodbService() {
        return new SystemTagSynMongodbServiceImpl();
    }
}