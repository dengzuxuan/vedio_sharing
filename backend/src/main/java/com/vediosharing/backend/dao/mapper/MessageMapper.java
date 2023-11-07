package com.vediosharing.backend.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vediosharing.backend.dao.entity.Message;
import org.apache.ibatis.annotations.Mapper;

/**
 * @ClassName MessageMapper
 * @Description TODO
 * @Author Colin
 * @Date 2023/10/28 2:29
 * @Version 1.0
 */
@Mapper
public interface MessageMapper extends BaseMapper<Message> {
}
