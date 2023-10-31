package com.vediosharing.backend.service;

import com.vediosharing.backend.core.constant.Result;
import com.vediosharing.backend.dto.req.UserInfoReqDto;
import com.vediosharing.backend.dto.req.UserRegisterReqDto;

/**
 * @ClassName UserService
 * @Description 用户相关模块
 * @Author Colin
 * @Date 2023/10/26 12:06
 * @Version 1.0
 */
public interface UserService {
    Result register(UserRegisterReqDto dto);

    Result login(UserRegisterReqDto dto);
    Result userInfo();
    Result otherUserInfo(Integer userId);
    Result updateInfo(UserInfoReqDto dto);
    //添加朋友
    Result addfriend(Integer userId);
    //删除朋友
    Result delfriend(Integer userId);
    //获取关注我的
    Result getfriend();
    //获取我关注的
    Result getSendFriend();
}
