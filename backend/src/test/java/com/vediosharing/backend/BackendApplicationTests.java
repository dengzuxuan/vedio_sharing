package com.vediosharing.backend;

import com.videocomment.backend.core.constant.RankConsts;
import com.videocomment.backend.core.utils.RankUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BackendApplicationTests {
    @Autowired
    RankUtil rankUtil;

    @Test
    void contextLoads() {
        String[] keys = {RankConsts.DAYLY_RANK,RankConsts.WEEKLY_RANK,RankConsts.TOTAL_RANK,RankConsts.MONTH_RANK};

        for(String key:keys){
            rankUtil.initAllRank(key);
        }
    }

}
