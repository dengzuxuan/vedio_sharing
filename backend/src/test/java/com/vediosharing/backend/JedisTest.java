package com.vediosharing.backend;
import com.videocomment.backend.core.utils.SearchUtil;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.videocomment.backend.core.utils.CaffeineUtil;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.mongodb.core.MongoTemplate;

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
    @Autowired
    private SearchUtil searchUtil;
    @Test
    void testString(){
        //searchUtil.delVideoInfo(2);
//        searchUtil.addVideoInfo(2,"33","2222你好");
//        searchUtil.addVideoInfo(3,"你好","0000");
//
//        List<SearchUtil.Video> users = searchUtil.searchVideoInfo("你好");
//        System.out.println(users);
//
//        searchUtil.delUserInfo(2);
//        List<SearchUtil.Video> users2= searchUtil.searchVideoInfo("22");
//        System.out.println(users2);
//        System.out.println(users);
        //searchUtil.updateUserInfo(1,"更新11");
        // 创建查询
  //      Query query = new Query();
//        Criteria criteria = new Criteria();
//        // 设置条件：
//        criteria.and("id").is(2);
        //将条件添加到查询内
        //query.addCriteria(criteria);

//        String searchString = "你好"; // 要查找的字符串
//
//        // 创建一个查询条件，使用 $or 运算符来匹配任何字段包含特定字符串的文档
//        Criteria criteria = new Criteria().orOperator(
//                Criteria.where("name").regex(".*" + searchString + ".*")
//        );
//
//        // 创建一个查询对象
//        Query query = new Query(criteria);
//
//        // 执行查询并返回结果
//        List<User> users = mongoTemplate.find(query, User.class, "user");
//        System.out.println(users);
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
