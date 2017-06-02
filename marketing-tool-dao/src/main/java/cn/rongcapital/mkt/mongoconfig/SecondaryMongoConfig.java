package cn.rongcapital.mkt.mongoconfig;

import org.springframework.context.annotation.Configuration;

@Configuration
//@EnableMongoRepositories(basePackages = "", mongoTemplateRef = SecondaryMongoConfig.MONGO_TEMPLATE)
public class SecondaryMongoConfig {
    public static final String MONGO_TEMPLATE = "secondaryMongoTemplate";
}
