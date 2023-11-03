package com.vediosharing.backend.dto.req;

import lombok.Data;

/**
 * @ClassName CommentReqDto
 * @Description TODO
 * @Author Colin
 * @Date 2023/11/4 2:39
 * @Version 1.0
 */
@Data
public class CommentReqDto {
    private int videoid;
    private int commentid;
    private String content;
}
