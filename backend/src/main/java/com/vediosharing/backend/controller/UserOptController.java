package com.vediosharing.backend.controller;

import com.vediosharing.backend.core.constant.ApiRouterConsts;
import com.vediosharing.backend.core.constant.Result;
import com.vediosharing.backend.service.OptVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @ClassName UserOptController
 * @Description 用户对视频的操作模块，包括点赞 收藏 评论
 * @Author Colin
 * @Date 2023/10/31 14:01
 * @Version 1.0
 */
@RestController
@RequestMapping(ApiRouterConsts.OPTVIDEO_URL_PREFIX)
public class UserOptController {
    @Autowired
    OptVideoService optVideoService;

    @PostMapping("/addcollect")
    public Result addCollect(@RequestParam Map<String,String> m1){
        int videoId = Integer.parseInt(m1.get("video_id"));
        return optVideoService.addcollect(videoId);
    }
    @PostMapping("/delcollect")
    public Result delCollect(@RequestParam Map<String,String> m1){
        int videoId = Integer.parseInt(m1.get("video_id"));
        return optVideoService.delcollect(videoId);
    }

    @PostMapping("/addlike")
    public Result addLike(@RequestParam Map<String,String> m1){
        int videoId = Integer.parseInt(m1.get("video_id"));
        return optVideoService.addLike(videoId);
    }
    @PostMapping("/dellike")
    public Result delLike(@RequestParam Map<String,String> m1){
        int videoId = Integer.parseInt(m1.get("video_id"));
        return optVideoService.delLike(videoId);
    }
}
