package com.videocomment.backend.controller;

import com.videocomment.backend.core.constant.ApiRouterConsts;
import com.videocomment.backend.core.constant.RankConsts;
import com.videocomment.backend.core.constant.Result;
import com.videocomment.backend.core.utils.redis.RankUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName ReloadRedisDataController
 * @Description TODO
 * @Author Colin
 * @Date 2023/11/7 22:02
 * @Version 1.0
 */
@RestController
@RequestMapping(ApiRouterConsts.RELOAD_URL_PREFIX)
public class ReloadRedisDataController {
    @Autowired
    RankUtil rankUtil;

    @PostMapping("/rankdata")
    Result ReloadRedisData(){
        String[] keys = {RankConsts.DAYLY_RANK,RankConsts.WEEKLY_RANK,RankConsts.TOTAL_RANK,RankConsts.MONTH_RANK};

        for(String key:keys){
            rankUtil.initAllRank(key);
        }

        return Result.success(null);
    }
}
