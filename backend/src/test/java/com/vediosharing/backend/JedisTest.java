package com.vediosharing.backend;

import com.vediosharing.backend.dao.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.Jedis;

/**
 * @ClassName JedisTest
 * @Description TODO
 * @Author Colin
 * @Date 2023/11/4 11:32
 * @Version 1.0
 */
@SpringBootTest
public class JedisTest {
    @Autowired
    private RedisTemplate redisTemplate;
    @Test
    void testString(){
        redisTemplate.opsForValue().set("name","deng");
        Object name = redisTemplate.opsForValue().get("name");
        System.out.println("name = "+name);
    }
}
