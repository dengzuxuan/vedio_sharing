package com.vediosharing.backend.dto.resp;

import com.vediosharing.backend.dao.entity.User;
import com.vediosharing.backend.dao.entity.Video;
import lombok.Data;

/**
 * @ClassName VideoDetailRespDto
 * @Description TODO
 * @Author Colin
 * @Date 2023/11/3 2:01
 * @Version 1.0
 */
@Data
public class VideoDetailRespDto {
    User user;
    Video video;
}
