package com.vediosharing.backend.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.vediosharing.backend.core.constant.RankConsts;
import com.vediosharing.backend.core.constant.Result;
import com.vediosharing.backend.core.constant.VideoTypeConsts;
import com.vediosharing.backend.core.utils.RankUtil;
import com.vediosharing.backend.dao.entity.*;
import com.vediosharing.backend.dao.mapper.*;
import com.vediosharing.backend.dto.resp.VideoDetailRespDto;
import com.vediosharing.backend.service.Impl.utils.UserDetailsImpl;
import com.vediosharing.backend.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @ClassName VideoServiceImpl
 * @Description TODO
 * @Author Colin
 * @Date 2023/11/4 22:29
 * @Version 1.0
 */
@Service
public class VideoServiceImpl implements VideoService {
    @Autowired
    UserLikelyMapper userLikelyMapper;
    @Autowired
    VideoMapper videoMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    CollectMapper collectMapper;
    @Autowired
    LikeMapper likeMapper;
    @Autowired
    FriendMapper friendMapper;
    @Autowired
    RankUtil rankUtil;

    @Override
    public Result getVideo() {
        Map<String,Object> res = new HashMap<>();

        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User loginuser = loginUser.getUser();
        QueryWrapper<UsertLikely> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",loginuser.getId());
        UsertLikely usertLikely = userLikelyMapper.selectOne(queryWrapper);

        //根据用户喜好权重随机获取视频
        List<WeightedItem<Integer>> weightedItems = new ArrayList<>();
        weightedItems.add(new WeightedItem<>(VideoTypeConsts.SPORT,  usertLikely.getSport()));
        weightedItems.add(new WeightedItem<>(VideoTypeConsts.GAME,  usertLikely.getGame()));
        weightedItems.add(new WeightedItem<>(VideoTypeConsts.FOOD,  usertLikely.getFood()));
        weightedItems.add(new WeightedItem<>(VideoTypeConsts.MUSIC,  usertLikely.getMusic()));
        weightedItems.add(new WeightedItem<>(VideoTypeConsts.FUN,  usertLikely.getFun()));
        weightedItems.add(new WeightedItem<>(VideoTypeConsts.KNOWLEDGE,  usertLikely.getKnowledge()));
        weightedItems.add(new WeightedItem<>(VideoTypeConsts.ANIMAL,  usertLikely.getAnimal()));


        Integer selectedOption = selectRandomWeightedOption(weightedItems);

        QueryWrapper<Video> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("type",selectedOption);
        List<Video> videos = videoMapper.selectList(queryWrapper1);

        int videoIndex = (int) (Math.random()* videos.size());
        Video video = videos.get(videoIndex);
        video.setViewsPoints(video.getViewsPoints()+1);
        videoMapper.updateById(video);

        QueryWrapper<Collects> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("user_id",loginuser.getId()).eq("video_id",video.getId());
        Collects findCollect= collectMapper.selectOne(queryWrapper2);
        if(findCollect!=null){
            res.put("is_collect",true);
        }else{
            res.put("is_collect",false);
        }

        QueryWrapper<Likes> queryWrapper3 = new QueryWrapper<>();
        queryWrapper3.eq("user_id",loginuser.getId()).eq("video_id",video.getId());
        Likes findLike= likeMapper.selectOne(queryWrapper3);
        if(findLike!=null){
            res.put("is_like",true);
        }else{
            res.put("is_like",false);
        }

        QueryWrapper<Friend> queryWrapper4 = new QueryWrapper<>();
        queryWrapper4.eq("recv_userid",video.getUserId()).eq("send_userid",loginuser.getId());
        Friend findFriend= friendMapper.selectOne(queryWrapper4);
        if(findFriend!=null){
            res.put("is_friend",true);
        }else{
            res.put("is_friend",false);
        }

        User author = userMapper.selectById(video.getUserId());
        author.setPassword(null);
        author.setPasswordReal(null);

        res.put("video",video);
        res.put("user",author);

        return Result.success(res);
    }

    @Override
    public Result getTypeVideo(int Type) {
        Map<String,Object> res = new HashMap<>();

        QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type",Type);
        List<Video> videoAllList = videoMapper.selectList(queryWrapper);
        Collections.shuffle(videoAllList);

        List<VideoDetailRespDto> videoWeekList = new ArrayList<>();
        Set weekRankVideoIds = rankUtil.getRank(RankConsts.WEEKLY_RANK,Type ,0, 9);
        for (Object videoId : weekRankVideoIds) {
            Video video = videoMapper.selectById((int)videoId);
            if(video == null){
                continue;
            }
            User user = userMapper.selectById(video.getUserId());
            videoWeekList.add(new VideoDetailRespDto(user,video));
        }

        List<VideoDetailRespDto> videoTotalList = new ArrayList<>();
        Set totalRankVideoIds = rankUtil.getRank(RankConsts.TOTAL_RANK, Type ,0, 9);
        for (Object videoId : totalRankVideoIds) {
            Video video = videoMapper.selectById((int)videoId);
            if(video == null){
                continue;
            }
            User user = userMapper.selectById(video.getUserId());
            videoTotalList.add(new VideoDetailRespDto(user,video));
        }

        List<VideoDetailRespDto> videoDayList = new ArrayList<>();
        Set dayRankVideoIds = rankUtil.getRank(RankConsts.DAYLY_RANK,Type , 0, 4);
        for (Object videoId : dayRankVideoIds) {
            Video video = videoMapper.selectById((int)videoId);
            if(video == null){
                continue;
            }
            User user = userMapper.selectById(video.getUserId());
            videoDayList.add(new VideoDetailRespDto(user,video));
        }

        res.put("all",videoAllList);
        res.put("total",videoTotalList);
        res.put("week",videoWeekList);
        res.put("day",videoDayList);
        return Result.success(res);
    }

    public static <T> T selectRandomWeightedOption(List<WeightedItem<T>> items) {
        int totalWeight = items.stream().mapToInt(WeightedItem::getWeight).sum();
        int randomNumber = new Random().nextInt(totalWeight);

        int cumulativeWeight = 0;
        for (WeightedItem<T> item : items) {
            cumulativeWeight += item.getWeight();
            if (randomNumber < cumulativeWeight) {
                return item.getItem();
            }
        }

        // Fallback option in case of unexpected situations
        return items.get(0).getItem();
    }
    class WeightedItem<T> {
        private T item;
        private int weight;

        public WeightedItem(T item, int weight) {
            this.item = item;
            this.weight = weight;
        }

        public T getItem() {
            return item;
        }

        public int getWeight() {
            return weight;
        }
    }
}
