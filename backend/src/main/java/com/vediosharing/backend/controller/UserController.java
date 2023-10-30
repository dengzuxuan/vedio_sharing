package com.vediosharing.backend.controller;

import com.vediosharing.backend.core.constant.ApiRouterConsts;
import com.vediosharing.backend.core.constant.Result;
import com.vediosharing.backend.dto.req.UserInfoReqDto;
import com.vediosharing.backend.dto.req.UserRegisterReqDto;
import com.vediosharing.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

    @PostMapping(value = "/register",consumes="application/json")
    public Result register(@RequestBody UserRegisterReqDto dto){
        return userService.register(dto);
    }
    @PostMapping(value = "/login",consumes="application/json")
    public Result login(@RequestBody UserRegisterReqDto dto){
        return userService.login(dto);
    }


    @GetMapping("/userinfo")
    public Result userinfo(){
        return userService.userInfo();
    }
    @GetMapping("/otheruserinfo")
    public Result userinfo(@RequestParam Map<String,String>m1){
        int userId = Integer.parseInt(m1.get("userid"));
        return userService.otherUserInfo(userId);
    }
    @PostMapping(value = "/updateinfo",consumes="application/json")
    public Result updateinfo(@RequestBody UserInfoReqDto dto){
        return userService.updateInfo(dto);
    }


    @PostMapping("/addfriend")
    public Result addfirend(@RequestParam Map<String,String> m1){
        int userId = Integer.parseInt(m1.get("userid"));
        return userService.addfriend(userId);
    }
    @PostMapping("/delfriend")
    public Result delfirend(@RequestParam Map<String,String> m1){
        int userId = Integer.parseInt(m1.get("userid"));
        return userService.delfriend(userId);
    }
    @GetMapping("/getfriend")
    public Result getfriend(){
        return userService.getfriend();
    }

}
