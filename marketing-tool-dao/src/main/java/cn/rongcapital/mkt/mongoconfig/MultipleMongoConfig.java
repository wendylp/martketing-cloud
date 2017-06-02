package cn.rongcapital.mkt.mongoconfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;

@Configuration
@PropertySources({@PropertySource(value = "classpath:${conf.dir}/application-dao.properties")})
public class MultipleMongoConfig {

    @Value("${spring.data.mongodb.host}")
    private String host;
    @Value("${spring.data.mongodb.port}")
    private int port;
    @Value("${spring.data.mongodb.database}")
    private String dbname;
    @Value("${spring.data.mongodb.connectionsPerHost}")
    private int connectionsPerHost;
    @Value("${spring.data.mongodb.threadsAllowedToBlockForConnectionMultiplier}")
    private int threadsLimit;

    @Value("${spring.data.mongodb.tag.host}")
    private String tagHost;
    @Value("${spring.data.mongodb.tag.port}")
    private int tagPort;
    @Value("${spring.data.mongodb.tag.database}")
    private String tagDbname;
    @Value("${spring.data.mongodb.tag.connectionsPerHost}")
    private int tagConnectionsPerHost;
    @Value("${spring.data.mongodb.tag.threadsAllowedToBlockForConnectionMultiplier}")
    private int tagThreadsLimit;


    @Primary
    @Bean(name = PrimaryMongoConfig.MONGO_TEMPLATE)
    public MongoTemplate primaryMongoTemplate() throws Exception {
        return new MongoTemplate(primaryFactory());
    }

    @Bean(name = SecondaryMongoConfig.MONGO_TEMPLATE)
    public MongoTemplate secondaryMongoTemplate() throws Exception {
        return new MongoTemplate(secondaryFactory());
    }

    @Bean
    @Primary
    public MongoDbFactory primaryFactory() throws Exception {
        return new SimpleMongoDbFactory(new MongoClient(new ServerAddress(host, port), MongoClientOptions.builder()
                .connectionsPerHost(connectionsPerHost).threadsAllowedToBlockForConnectionMultiplier(threadsLimit)
                .build()), dbname);
    }

    @Bean
    public MongoDbFactory secondaryFactory() throws Exception {
        return new SimpleMongoDbFactory(new MongoClient(new ServerAddress(tagHost, tagPort), MongoClientOptions
                .builder().connectionsPerHost(tagConnectionsPerHost)
                .threadsAllowedToBlockForConnectionMultiplier(tagThreadsLimit).build()), tagDbname);
    }
}
