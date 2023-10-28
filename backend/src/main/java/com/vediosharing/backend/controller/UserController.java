package com.vediosharing.backend.controller;

import com.vediosharing.backend.core.constant.ApiRouterConsts;
import com.vediosharing.backend.core.constant.Result;
import com.vediosharing.backend.dto.req.UserRegisterReqDto;
import com.vediosharing.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName UserController
 * @Description 用户模块controller层
 * @Author Colin
 * @Date 2023/10/26 11:54
 * @Version 1.0
 */
@RestController
@RequestMapping(ApiRouterConsts.USER_URL_PREFIX)
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/register")
    public Result register(@RequestBody UserRegisterReqDto dto){
        return userService.register(dto);
    }

    @PostMapping("/login")
    public Result login(@RequestBody UserRegisterReqDto dto){
        return userService.login(dto);
    }

    @GetMapping("/userinfo")
    public Result userinfo(){
        return userService.userInfo();
    }

    @PostMapping("/addfirend")
    public Result addfirend(){
        return userService.userInfo();
    }
    @PostMapping("/delfirend")
    public Result delfirend(){
        return userService.userInfo();
    }
}
