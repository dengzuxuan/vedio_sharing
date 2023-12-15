package com.videocomment.backend.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.videocomment.backend.dao.entity.Friend;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ClassName FriendMapper
 * @Description TODO
 * @Author Colin
 * @Date 2023/10/29 0:05
 * @Version 1.0
 */
@Mapper
public interface FriendMapper extends BaseMapper<Friend> {
}
