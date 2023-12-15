package com.videocomment.backend.dto.resp;

import com.videocomment.backend.dao.entity.User;
import com.videocomment.backend.dao.entity.Video;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @ClassName VideoDetailRespDto
 * @Description TODO
 * @Author Colin
 * @Date 2023/11/3 2:01
 * @Version 1.0
 */
@Data
@AllArgsConstructor
public class VideoDetailRespDto {
    User user;
    Video video;
}
