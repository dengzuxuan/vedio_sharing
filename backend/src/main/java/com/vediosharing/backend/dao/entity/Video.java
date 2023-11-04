package com.vediosharing.backend.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @ClassName Video
 * @Description TODO
 * @Author Colin
 * @Date 2023/10/29 23:28
 * @Version 1.0
 */
@TableName("video")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Video {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private int userId;
    private String title;
    private String description;
    private int type;
    private String videoUrl;
    private String photoUrl;
    private int initHotPoints;
    private int totalHotPoints;
    private int weekHotPoints;
    private int viewsPoints;
    private int likePoints;
    private int collectPoints;
    private int commentPoints;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    private Date updateTime;
}
