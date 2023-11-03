package com.vediosharing.backend.dto.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName UserInfoReqDto
 * @Description TODO
 * @Author Colin
 * @Date 2023/10/30 17:45
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoReqDto implements Serializable {
    private String nickname;
    private String photo;
    private String email;
    private int sexual;
    private int likehidden;
    private int collecthidden;
}
