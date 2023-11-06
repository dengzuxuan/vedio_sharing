package com.vediosharing.backend;
import com.mysql.cj.xdevapi.UpdateResult;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.vediosharing.backend.core.utils.CaffeineUtil;
import com.vediosharing.backend.dao.entity.History;
import com.vediosharing.backend.dao.entity.User;
import com.vediosharing.backend.dao.entity.Video;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Indexed;

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
    @Data
    @AllArgsConstructor
    @Document(collection="user")
    public class User {
        @Id
        private Integer id;
        private String name;
    }
    @Autowired
    private CaffeineUtil caffeineUtil;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Test
    void testString(){
        // 创建查询
  //      Query query = new Query();
//        Criteria criteria = new Criteria();
//        // 设置条件：
//        criteria.and("id").is(2);
        //将条件添加到查询内
        //query.addCriteria(criteria);

        String searchString = "你好"; // 要查找的字符串

        // 创建一个查询条件，使用 $or 运算符来匹配任何字段包含特定字符串的文档
        Criteria criteria = new Criteria().orOperator(
                Criteria.where("name").regex(".*" + searchString + ".*")
        );

        // 创建一个查询对象
        Query query = new Query(criteria);

        // 执行查询并返回结果
        List<User> users = mongoTemplate.find(query, User.class, "user");
        System.out.println(users);
        //return users;
//        Update update = new Update().set("name", "还好还好你好");
//        mongoTemplate.updateFirst(query, update, User.class);
        // 调用remove()，进行删除，UserCollection.class是操作类
        //mongoTemplate.remove(query,User.class);
        //  根据集合名称保存对象到mongodb
      //  mongoTemplate.save(user,"user");
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
