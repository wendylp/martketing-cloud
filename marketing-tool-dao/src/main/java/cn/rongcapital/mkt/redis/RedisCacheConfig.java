/*************************************************
 * @功能及特点的描述简述: redis缓存配置类
 * 该类被编译测试过
 * @see （与该类关联的类）：
 * @对应项目名称：营销云系统
 * @author: 谢小良
 * @version: 版本v1.6
 * @date(创建、开发日期)：2017-01-08 
 * @date(最后修改日期)：2017-01-08 
 * @复审人：
 *************************************************/
package cn.rongcapital.mkt.redis;
import java.lang.reflect.Method;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;  
  
@Configuration  
@EnableCaching  
@PropertySource("classpath:${conf.dir}/jedisConfig.properties")
public class RedisCacheConfig extends CachingConfigurerSupport {  
    private @Value("${redis.cache.host}") String redisHost;
    private @Value("${redis.cache.host.port}") int redisPort;
    private @Value("${redis.cache.host.pass}")String password;
    private @Value("${redis.cache.host.database}")int database;
    
    /**
     * redis 连接池
     */
    @Bean  
    public JedisConnectionFactory redisConnectionFactory() {  
        JedisConnectionFactory redisConnectionFactory = new JedisConnectionFactory();  
  
        // Defaults  
        redisConnectionFactory.setHostName(this.redisHost);  
        redisConnectionFactory.setPort(this.redisPort);  
        redisConnectionFactory.setPassword(this.password);
        redisConnectionFactory.setDatabase(database);
        return redisConnectionFactory;  
    }  
    
    /**
     * 自定义键
     */
    @Bean
    public KeyGenerator wiselyKeyGenerator(){  
        return new KeyGenerator() {  
            @Override  
            public Object generate(Object target, Method method, Object... params) {  
                StringBuilder sb = new StringBuilder();  
                sb.append(target.getClass().getName());  
                sb.append(method.getName());  
                for (Object obj : params) {  
                    sb.append(obj.toString());  
                }  
                return sb.toString();  
            }  
        };  
  
    }  
  
    @Bean  
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory cf) {  
        StringRedisTemplate template = new StringRedisTemplate(cf);  
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);  
        ObjectMapper om = new ObjectMapper();  
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);  
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);  
        jackson2JsonRedisSerializer.setObjectMapper(om);  
        template.setValueSerializer(jackson2JsonRedisSerializer);  
        template.afterPropertiesSet();  
        return template;  
    }  
  
    @Bean  
    public CacheManager cacheManager(RedisTemplate redisTemplate) {  
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);  
  
        // Number of seconds before expiration. Defaults to unlimited (0)  
        cacheManager.setDefaultExpiration(3000); // Sets the default expire time (in seconds)  
        return cacheManager;  
    }  
}  