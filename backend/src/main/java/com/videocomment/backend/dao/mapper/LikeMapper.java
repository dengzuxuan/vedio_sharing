package com.videocomment.backend.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.videocomment.backend.dao.entity.Likes;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ClassName LikeMapper
 * @Description TODO
 * @Author Colin
 * @Date 2023/10/31 12:25
 * @Version 1.0
 */
@Mapper
public interface LikeMapper extends BaseMapper<Likes> {
}
