package com.vediosharing.backend;

import com.vediosharing.backend.core.utils.CaffeineUtil;
import com.vediosharing.backend.dao.entity.History;
import com.vediosharing.backend.dao.entity.Video;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName JedisTest
 * @Description TODO
 * @Author Colin
 * @Date 2023/11/4 11:32
 * @Version 1.0
 */
@SpringBootTest
@EnableCaching
public class JedisTest {
    @Autowired
    private CaffeineUtil caffeineUtil;
    @Test
    void testString(){
//        History history = caffeineUtil.getHistory(1);
//        History history1 = caffeineUtil.addHistory(history);
//        History history7 = caffeineUtil.getHistory(1);
//        CaffeineUtil.History history6 = caffeineUtil.addHistory(history7);
//
//        CaffeineUtil.History history0 = caffeineUtil.getHistory(1);
//        CaffeineUtil.History history2 = caffeineUtil.getHistory(2);
//        CaffeineUtil.History history3 = caffeineUtil.addHistory(history2);
//        CaffeineUtil.History history4 = caffeineUtil.getHistory(1);
//        CaffeineUtil.History history5 = caffeineUtil.getHistory(1);
//        CaffeineUtil.History history10 = caffeineUtil.getHistory(2);
//
//        caffeineUtil.removeHistory(2);
//        CaffeineUtil.History history11 = caffeineUtil.getHistory(1);
//        CaffeineUtil.History history12 = caffeineUtil.getHistory(2);
    }
}
