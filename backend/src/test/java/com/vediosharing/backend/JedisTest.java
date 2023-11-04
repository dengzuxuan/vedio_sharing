package com.vediosharing.backend;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
    private RedisTemplate<String,Object> redisTemplate;
    @Data
    @AllArgsConstructor
    private class User{
        int age;
    }
    @Test
    void testString(){
        List<User> users = new ArrayList<>();
        User user = new User(1);
        users.add(user);

        for (User user1:users){
            user1.setAge(2);
        }

        System.out.println(users);
    }
}
