package com.videocomment.backend.dto.req;

import lombok.Data;

/**
 * @ClassName UserRegisterReqDto
 * @Description TODO
 * @Author Colin
 * @Date 2023/10/26 12:02
 * @Version 1.0
 */
@Data
public class UserRegisterReqDto {
    private String username;
    private String password;
}
