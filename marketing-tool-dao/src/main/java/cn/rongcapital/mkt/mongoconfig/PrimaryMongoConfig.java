package cn.rongcapital.mkt.mongoconfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = {"cn.rongcapital.mkt.mongodb", "cn.rongcapital.mkt.mongodaoimpl"}, mongoTemplateRef = PrimaryMongoConfig.MONGO_TEMPLATE)
public class PrimaryMongoConfig {
    public static final String MONGO_TEMPLATE = "primaryMongoTemplate";
}
