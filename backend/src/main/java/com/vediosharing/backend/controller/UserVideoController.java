package com.vediosharing.backend.controller;

import com.vediosharing.backend.core.constant.ApiRouterConsts;
import com.vediosharing.backend.core.constant.Result;
import com.vediosharing.backend.service.UserVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName UserVideoController
 * @Description TODO
 * @Author Colin
 * @Date 2023/10/30 2:42
 * @Version 1.0
 */
@RestController
@RequestMapping(ApiRouterConsts.VIDEO_URL_PREFIX)
public class UserVideoController {
    @Autowired
    UserVideoService userVideoService;
    @GetMapping("/getvideo")
    public Result getVideo(){
        return userVideoService.getVideo();
    }
}
