package com.vediosharing.backend.service;

import com.vediosharing.backend.core.constant.Result;
import com.vediosharing.backend.dto.req.UserRegisterReqDto;

/**
 * @ClassName UserService
 * @Description 用户模块
 * @Author Colin
 * @Date 2023/10/26 12:06
 * @Version 1.0
 */
public interface UserService {
    Result register(UserRegisterReqDto dto);

    Result login(UserRegisterReqDto dto);
    Result userInfo();
    Result addfirend(Integer userId);
    Result delfirend(Integer userId);
}
