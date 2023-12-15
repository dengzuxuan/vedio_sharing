package com.videocomment.backend.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @ClassName Comment
 * @Description 评论模块
 * @Author Colin
 * @Date 2023/11/3 18:08
 * @Version 1.0
 */
@TableName("comment")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String content;
    private int userId;
    private int videoId;
    private int commentId;
    private int likes;
    private int flag;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    private Date updateTime;
}
