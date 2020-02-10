package top.chende.shuinikg.crawl.demo;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.support.collections.RedisCollectionFactoryBean;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author: chende
 * @date: 2019/12/3 13:41
 * @description:
 */
public class RedisDemo {
    public static void main(String[] args) {

        RedisStandaloneConfiguration standaloneConfig = new RedisStandaloneConfiguration();
        standaloneConfig.setHostName("127.0.0.1");
        standaloneConfig.setPort(6379);
        standaloneConfig.setDatabase(1);
        JedisConnectionFactory connectionFactory = new JedisConnectionFactory(standaloneConfig);


        // JedisConfig jedisPoolConfig = new JedisConfig();
//        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
//        jedisConnectionFactory.setHostName("localhost");
//        jedisConnectionFactory.setPort(6379);
       // jedisConnectionFactory.setPoolConfig(new JedisPoolConfig());
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        connectionFactory.setPoolConfig(jedisPoolConfig);
        RedisTemplate<String, String> redisTemplate = new StringRedisTemplate();

        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.afterPropertiesSet();


        redisTemplate.opsForSet().add("url","http://www.baidu.com");
        redisTemplate.opsForSet().add("url","http://www.baidu.com");
        redisTemplate.opsForSet().remove("url","http://www.baidu.com");

        System.out.println(        redisTemplate.opsForSet().isMember("url","http://www.baidu.com"));


    }
}
