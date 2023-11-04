package com.vediosharing.backend.core.common.cronjob;

import com.vediosharing.backend.core.constant.RankConsts;
import com.vediosharing.backend.core.utils.RankUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.scheduling.annotation.Scheduled;
/**
 * @ClassName RedisRankListClearTask
 * @Description TODO
 * @Author Colin
 * @Date 2023/11/4 17:53
 * @Version 1.0
 */
@Component
public class RedisRankListClearTask {
    @Autowired
    RankUtil rankUtil;

    @Scheduled(cron = "0 0 0 * * ?") // 每天零点触发
    public void clearMonthlyRedisData() {
        //覆盖当天热度表为初始热度表
        Boolean delled = rankUtil.initAllRank(RankConsts.DAYLY_RANK);
        if (delled){
            System.out.println("本日热度排行榜元素已清空");
        }else{
            System.out.println("本日热度排行榜清空失败");
        }
        //总热度表落表mysql
        //本周热度表落表mysql
        rankUtil.saveRank();
    }

    @Scheduled(cron = "0 0 0 * * MON") // 每周第一天零点触发
    public void clearWeeklyRedisData() {
        //覆盖本周热度表为初始热度表
        Boolean delled = rankUtil.initAllRank(RankConsts.WEEKLY_RANK);
        if (delled){
            System.out.println("本周热度排行榜元素已清空");
        }else{
            System.out.println("本周热度排行榜清空失败");
        }
    }
}
