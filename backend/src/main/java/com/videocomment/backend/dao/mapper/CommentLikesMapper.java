package com.videocomment.backend.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.videocomment.backend.dao.entity.CommentLikes;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ClassName CommentLikesMapper
 * @Description TODO
 * @Author Colin
 * @Date 2023/11/4 14:57
 * @Version 1.0
 */
@Mapper
public interface CommentLikesMapper extends BaseMapper<CommentLikes> {
}
