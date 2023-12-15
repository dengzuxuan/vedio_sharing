package com.videocomment.backend.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.videocomment.backend.dao.entity.Video;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ClassName VideoMapper
 * @Description TODO
 * @Author Colin
 * @Date 2023/10/29 23:43
 * @Version 1.0
 */
@Mapper
public interface VideoMapper extends BaseMapper<Video> {
}
