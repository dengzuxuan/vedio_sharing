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
 * @ClassName CommentLikes
 * @Description TODO
 * @Author Colin
 * @Date 2023/11/4 14:55
 * @Version 1.0
 */
@TableName("comment_likes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentLikes {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private int videoId;
    private int commentId;
    private int sendUserid;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    private Date updateTime;
}
