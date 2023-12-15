package com.videocomment.backend.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * @ClassName History
 * @Description TODO
 * @Author Colin
 * @Date 2023/11/6 12:16
 * @Version 1.0
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class History{
    Integer userId; //用户id
    Map<Integer,Integer> history; //历史记录 位置->视频id
    Integer currentIndex; //当前位置
}
