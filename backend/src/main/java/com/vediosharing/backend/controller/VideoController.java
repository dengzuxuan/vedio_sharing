package com.vediosharing.backend.controller;

import com.vediosharing.backend.core.constant.ApiRouterConsts;
import com.vediosharing.backend.core.constant.Result;
import com.vediosharing.backend.service.Impl.VideoServiceImpl;
import com.vediosharing.backend.service.UserVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @ClassName VideoController
 * @Description 获取上一个/下一个视频 获取模块视频
 * @Author Colin
 * @Date 2023/11/4 22:27
 * @Version 1.0
 */
@RestController
@RequestMapping(ApiRouterConsts.VIDEO_URL_PREFIX)
public class VideoController {
    @Autowired
    VideoServiceImpl videoService;

    @GetMapping("/getvideo")
    public Result getVideo(){
        return videoService.getVideo();
    }
    @GetMapping("/getprevideo")
    public Result getPreVideo(){
        return videoService.getPreVideo();
    }
    @GetMapping("/clearprevideo")
    public Result clearPreVideo(){
        return videoService.clearPreVideo();
    }
    @GetMapping("/gettypevideos")
    public Result getTypeVideo(@RequestParam Map<String,String> m1){
        int type = Integer.parseInt(m1.get("type"));
        return videoService.getTypeVideos(type);
    }
    @GetMapping("/gettypedayrank")
    public Result getTypeDayRank(@RequestParam Map<String,String> m1){
        int type = Integer.parseInt(m1.get("type"));
        return videoService.getTypeDayTop(type);
    }
    @GetMapping("/gettypeweekrank")
    public Result getTypeWeekRank(@RequestParam Map<String,String> m1){
        int type = Integer.parseInt(m1.get("type"));
        return videoService.getTypeWeekTop(type);
    }
    @GetMapping("/gettypemonthrank")
    public Result getTypeTotalRank(@RequestParam Map<String,String> m1){
        int type = Integer.parseInt(m1.get("type"));
        return videoService.getTypeMonthTop(type);
    }
}
