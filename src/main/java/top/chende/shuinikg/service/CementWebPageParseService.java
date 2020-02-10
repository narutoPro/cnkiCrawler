package top.chende.shuinikg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import top.chende.shuinikg.repository.CementPageRepository;

/**
 * @author: chende
 * @date: 2019/12/4 10:26
 * @description:
 */
@Service
public class CementWebPageParseService {

    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    CementPageRepository cementPageRepository;


}
