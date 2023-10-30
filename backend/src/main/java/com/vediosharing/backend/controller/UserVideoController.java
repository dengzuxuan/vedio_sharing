package com.vediosharing.backend.controller;

import com.vediosharing.backend.core.constant.ApiRouterConsts;
import com.vediosharing.backend.core.constant.Result;
import com.vediosharing.backend.service.UserVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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
    @GetMapping("/addvideo")
    public Result addVideo(@RequestParam Map<String,String> m1){

        return userVideoService.addVideo();
    }
    @GetMapping("/getvideo")
    public Result getVideo(){
        return userVideoService.getVideo();
    }
}
