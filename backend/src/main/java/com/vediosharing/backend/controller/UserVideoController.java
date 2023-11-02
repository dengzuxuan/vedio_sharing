package com.vediosharing.backend.controller;

import com.vediosharing.backend.core.constant.ApiRouterConsts;
import com.vediosharing.backend.core.constant.Result;
import com.vediosharing.backend.dto.req.VideoReqDto;
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
    @PostMapping(value = "/addvideo",consumes="application/json")
    public Result addVideo(@RequestBody VideoReqDto dto){
        return userVideoService.addVideo(dto);
    }
    @GetMapping("/getvideo")
    public Result getVideo(){
        return userVideoService.getVideo();
    }
    @GetMapping("/getsinglevideo")
    public Result getSingleVideo(@RequestParam Map<String,String>m1){
        int videoId = Integer.parseInt(m1.get("video_id"));
        return userVideoService.getSingleVideo(videoId);
    }
    @GetMapping("/uservideo")
    public Result getUserVideo(){
        return userVideoService.getUserVideos();
    }
    @GetMapping("/userlike")
    public Result getMyLike(){
        return userVideoService.getUserLikes();
    }
    @GetMapping("/usercollect")
    public Result getUserCollect(){
        return userVideoService.getUserCollects();
    }
}
