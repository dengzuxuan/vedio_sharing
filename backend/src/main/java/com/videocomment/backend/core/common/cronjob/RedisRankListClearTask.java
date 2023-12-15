package com.videocomment.backend.core.common.cronjob;

import com.videocomment.backend.core.utils.RankUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

//    @Scheduled(cron = "0 0 0 * * ?") // 每天零点触发
//    public void clearMonthlyRedisData() {
//        //覆盖当天热度表为初始热度表
//        Boolean delled = rankUtil.initAllRank(RankConsts.DAYLY_RANK);
//        if (delled){
//            System.out.println("本日热度排行榜元素已清空");
//        }else{
//            System.out.println("本日热度排行榜清空失败");
//        }
//        //总热度表落表mysql
//        //本周 本月 热度表落表mysql
//        rankUtil.saveRank(RankConsts.WEEKLY_RANK);
//        rankUtil.saveRank(RankConsts.MONTH_RANK);
//        rankUtil.saveRank(RankConsts.TOTAL_RANK);
//    }
//
//    @Scheduled(cron = "0 0 0 * * MON") // 每周第一天零点触发
//    public void clearWeeklyRedisData() {
//        //覆盖本周热度表为初始热度表
//        Boolean delled = rankUtil.initAllRank(RankConsts.WEEKLY_RANK);
//        if (delled){
//            System.out.println("本周热度排行榜元素已清空");
//        }else{
//            System.out.println("本周热度排行榜清空失败");
//        }
//        rankUtil.saveRank(RankConsts.WEEKLY_RANK);
//    }
//    @Scheduled(cron = "0 0 0 1 * ?") // 每月第一天零点触发
//    public void clearMonthRedisData() {
//        //覆盖本月热度表为初始热度表
//        Boolean delled = rankUtil.initAllRank(RankConsts.MONTH_RANK);
//        if (delled){
//            System.out.println("本月热度排行榜元素已清空");
//        }else{
//            System.out.println("本月热度排行榜清空失败");
//        }
//        rankUtil.saveRank(RankConsts.MONTH_RANK);
//    }
}
