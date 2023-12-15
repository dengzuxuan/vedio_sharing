package com.videocomment.backend.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.videocomment.backend.dao.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ClassName UserMapper
 * @Description TODO
 * @Author Colin
 * @Date 2023/10/26 11:09
 * @Version 1.0
 */

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
