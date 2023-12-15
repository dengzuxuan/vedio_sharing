package com.vediosharing.backend.controller;

import com.vediosharing.backend.core.constant.ApiRouterConsts;
import com.vediosharing.backend.core.constant.RankConsts;
import com.vediosharing.backend.core.constant.Result;
import com.vediosharing.backend.core.utils.RankUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
