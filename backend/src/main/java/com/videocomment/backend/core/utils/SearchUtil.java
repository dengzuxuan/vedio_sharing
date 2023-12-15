package com.videocomment.backend.core.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName SearchUtil
 * @Description TODO
 * @Author Colin
 * @Date 2023/11/5 20:10
 * @Version 1.0
 */
@Component
public class SearchUtil {
    @Data
    @AllArgsConstructor
    @Document(collection="user")
    public class User {
        @Id
        private Integer id;
        private String nickName;
    }
    @Data
    @AllArgsConstructor
    @Document(collection="video")
    public class Video {
        @Id
        private Integer id;
        private String title;
        private String description;
    }
    @Autowired
    private MongoTemplate mongoTemplate;
    public void addUserInfo(Integer userId,String nickName){
        User newUser = new User(userId,nickName);
        mongoTemplate.save(newUser,"user");
    }
    public void updateUserInfo(Integer userId,String nickName){
        Query query = new Query();
        Criteria criteria = new Criteria();
        criteria.and("id").is(userId);
        query.addCriteria(criteria);
        Update updUser = new Update().set("nickName", nickName);
        mongoTemplate.updateFirst(query,updUser,User.class);
    }
    public List<User> searchUserInfo(String searchInfo){
        Query query = new Query();
        Criteria criteria = new Criteria().orOperator(
                Criteria.where("nickName").regex(".*" + searchInfo + ".*")
        );
        //将条件添加到查询内
        query.addCriteria(criteria);// 执行查询并返回结果
        return mongoTemplate.find(query, User.class, "user");
    }
    public void delUserInfo(Integer userId){
        // 创建查询
        Query query = new Query();
        Criteria criteria = new Criteria();
        criteria.and("id").is(userId);
        query.addCriteria(criteria);
        mongoTemplate.remove(query,User.class);
    }

    public void addVideoInfo(Integer videoId,String title,String description){
        Video newVideo = new Video(videoId,title,description);
        mongoTemplate.save(newVideo,"video");
    }

//    public void updateVideoInfo(Integer userId,String title,String description){
//        Query query = new Query();
//        Criteria criteria = new Criteria();
//        criteria.and("id").is(userId);
//        query.addCriteria(criteria);
//        Update updUser = new Update().set("name", nickName);
//        mongoTemplate.updateFirst(query,updUser,"user");
//    }
    public List<Video> searchVideoInfo(String searchInfo){
        Query query = new Query();
        Criteria criteria = new Criteria().orOperator(
                Criteria.where("title").regex(".*" + searchInfo + ".*"),
                Criteria.where("description").regex(".*" + searchInfo + ".*")
        );
        //将条件添加到查询内
        query.addCriteria(criteria);// 执行查询并返回结果
        return mongoTemplate.find(query, Video.class, "video");
    }

    public void delVideoInfo(Integer videoId){
        // 创建查询
        Query query = new Query();
        Criteria criteria = new Criteria();
        criteria.and("id").is(videoId);
        query.addCriteria(criteria);
        mongoTemplate.remove(query,Video.class);
    }
}
