package com.vediosharing.backend.core.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

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
    private class User {
        @Id
        private Integer id;
        private String nickName;
    }
    @Data
    @AllArgsConstructor
    @Document(collection="video")
    private class Video {
        @Id
        private Integer id;
        private String title;
        private String description;
    }
    @Autowired
    private MongoTemplate mongoTemplate;
    public void addUserInfo(Integer userId,String nickName){

    }
}
