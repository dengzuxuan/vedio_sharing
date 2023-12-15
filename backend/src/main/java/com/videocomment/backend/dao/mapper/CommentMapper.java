package com.videocomment.backend.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.videocomment.backend.dao.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ClassName CommentMapper
 * @Description TODO
 * @Author Colin
 * @Date 2023/11/4 2:15
 * @Version 1.0
 */
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
}
