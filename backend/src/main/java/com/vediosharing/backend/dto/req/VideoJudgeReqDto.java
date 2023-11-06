package com.vediosharing.backend.dto.req;

import lombok.Data;

/**
 * @ClassName VideoJudgeReqDto
 * @Description TODO
 * @Author Colin
 * @Date 2023/11/6 22:08
 * @Version 1.0
 */
@Data
public class VideoJudgeReqDto {
    private int videoId;
    private boolean doneOne;
    private boolean doneTwo;
    private boolean doneAll;
}
