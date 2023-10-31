package com.vediosharing.backend.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName User
 * @Description TODO
 * @Author Colin
 * @Date 2023/10/26 11:08
 * @Version 1.0
 */
@TableName("user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String username;
    private String nickname;

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private String password;
    private String email;
    private int sexual; //0默认 1男 2女
    private String photo; //使用七牛云cdn存储
    private transient Integer likes; //被点赞数
    private transient Integer collects; //被收藏数
    private Integer friends; //被关注数
    private Integer sendFriends; //我关注的数量
    private transient Integer videos; //我发布视频的数量
    private transient Integer views; //我发布的视频的播放量


    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private String passwordReal;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    private Date updateTime;
}
