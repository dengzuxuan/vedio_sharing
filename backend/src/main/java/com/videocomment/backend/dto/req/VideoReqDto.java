package com.videocomment.backend.dto.req;

import lombok.Data;

/**
 * @ClassName VideoReqDto
 * @Description TODO
 * @Author Colin
 * @Date 2023/10/30 13:55
 * @Version 1.0
 */
@Data
public class VideoReqDto {
    private String title;
    private String description;
    private int type;
    private String videourl;
    private String photourl;
}
