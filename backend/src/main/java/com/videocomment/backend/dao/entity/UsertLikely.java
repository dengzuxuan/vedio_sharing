package com.videocomment.backend.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @ClassName UsertLikely
 * @Description TODO
 * @Author Colin
 * @Date 2023/10/30 11:34
 * @Version 1.0
 */
@TableName("user_likely")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsertLikely {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer userId;
    private Integer sport;
    private Integer game;
    private Integer food;
    private Integer music;
    private Integer fun;
    private Integer knowledge;
    private Integer animal;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Asia/Shanghai")
    private Date updateTime;
}
