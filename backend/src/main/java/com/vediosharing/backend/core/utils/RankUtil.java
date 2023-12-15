package com.vediosharing.backend.core.utils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.vediosharing.backend.core.constant.RankConsts;
import com.vediosharing.backend.dao.entity.Video;
import com.vediosharing.backend.dao.mapper.VideoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName RankUtil
 * @Description
 * 视频热度分为两部分 一是总热度，这部分不会清空，
 * 二是本月热度和本周热度，会定时清空，清空后数据从mysql中初始热度获取
 * 同时，热度也会作为视频推荐模块的部分权重
 * 热度 = 3*观看 + 8*点赞 + 10*评论 + 20*收藏
 * @Author Colin
 * @Date 2023/11/4 11:07
 * @Version 1.0
 */
@Component
public class RankUtil {
    @Autowired
    VideoMapper videoMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    public boolean initRank(String key,int type, double score, Integer value){
        //总榜热度
//        String keytotal=key+":"+0;
//        Boolean add = redisTemplate.opsForZSet().add(keytotal, value, score);
//        redisTemplate.expire(keytotal,  10 * 365, TimeUnit.DAYS );

        //子榜热度
        String keyType=key+":"+type;
        try{
            Boolean add = redisTemplate.opsForZSet().add(keyType, value, score);
            redisTemplate.expire(keyType, Duration.ofDays(100));
            return add;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public void addRank(int type,Integer value,double incr){
        String[] keys = {RankConsts.DAYLY_RANK,RankConsts.WEEKLY_RANK,RankConsts.TOTAL_RANK,RankConsts.MONTH_RANK};
        for (String key : keys) {
            //总榜热度
            String keytotal=key+":"+0;
            redisTemplate.opsForZSet().incrementScore(keytotal, value, incr);
            //细分子榜热度
            String keyType=key+":"+type;
            redisTemplate.opsForZSet().incrementScore(keyType, value, incr);
        }
    }

    public Set getRank(String key,int type, int start, int end){
        key=key+":"+type;
        try {
            return redisTemplate.opsForZSet().reverseRange(key, start, end);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public Double getSingleScore(String key, int type,int value){
        key=key+":"+type;
        return redisTemplate.opsForZSet().score(key, value);
    }

    public Boolean delVideoId(int type,int videoId){
        String[] keys = {RankConsts.DAYLY_RANK,RankConsts.WEEKLY_RANK,RankConsts.TOTAL_RANK,RankConsts.MONTH_RANK};
        for (String key : keys) {
            key=key+":"+type;
            redisTemplate.opsForZSet().remove(key, videoId);
        }
        return true;
    }

    public Boolean initAllRank(String key){
        boolean add = false;
        List<Video> videoListAll = videoMapper.selectList(null);
        for (Video video:videoListAll){
            add = initRank(key, 0,video.getInitHotPoints(), video.getId());
        }
        for (int i = 1; i <= 7; i++) {
            QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("type",i);
            List<Video> videoList = videoMapper.selectList(queryWrapper);
            for (Video video:videoList){
                add = initRank(key, i,video.getInitHotPoints(), video.getId());
            }
        }

        return add;
    }

    public void saveRank(String rankType){
        Cursor<ZSetOperations.TypedTuple<Object>> scan = redisTemplate.opsForZSet().scan(rankType, ScanOptions.NONE);
        switch (rankType){
            case RankConsts.WEEKLY_RANK:
                while (scan.hasNext()){
                    ZSetOperations.TypedTuple item = scan.next();
                    int videoId = (int) item.getValue();
                    int hots = (int) Math.round(item.getScore());
                    Video video = videoMapper.selectById(videoId);
                    video.setWeekHotPoints(hots);
                    videoMapper.updateById(video);
                }
                break;
            case RankConsts.MONTH_RANK:
                while (scan.hasNext()){
                    ZSetOperations.TypedTuple item = scan.next();
                    int videoId = (int) item.getValue();
                    int hots = (int) Math.round(item.getScore());
                    Video video = videoMapper.selectById(videoId);
                    video.setMonthHotPoints(hots);
                    videoMapper.updateById(video);
                }
                break;
            case RankConsts.TOTAL_RANK:
                while (scan.hasNext()){
                    ZSetOperations.TypedTuple item = scan.next();
                    int videoId = (int) item.getValue();
                    int hots = (int) Math.round(item.getScore());
                    Video video = videoMapper.selectById(videoId);
                    video.setTotalHotPoints(hots);
                    videoMapper.updateById(video);
                }
                break;

        }
    }
}
