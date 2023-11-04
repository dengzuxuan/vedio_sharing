package com.vediosharing.backend.core.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Set;

/**
 * @ClassName RedisUtil
 * @Description TODO
 * @Author Colin
 * @Date 2023/11/4 11:07
 * @Version 1.0
 */
@Configuration
public class RedisUtil {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public boolean sortSetAdd(String key,double score,String value){
        try{
            return redisTemplate.opsForZSet().add(key,value,score);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public double sortSetZincrby(String key,String value,double i){
        try {
            //返回新增元素后的分数
            return redisTemplate.opsForZSet().incrementScore(key, value, i);
        }catch(Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    public Set sortSetRange(String key, int start, int end){
        try {
            return redisTemplate.opsForZSet().reverseRange(key, start, end);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
