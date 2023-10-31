package com.vediosharing.backend.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.vediosharing.backend.core.constant.Result;
import com.vediosharing.backend.core.constant.VideoTypeConsts;
import com.vediosharing.backend.dao.entity.*;
import com.vediosharing.backend.dao.mapper.*;
import com.vediosharing.backend.dto.req.VideoReqDto;
import com.vediosharing.backend.service.Impl.utils.UserDetailsImpl;
import com.vediosharing.backend.service.UserVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @ClassName UserVideoServiceImpl
 * @Description TODO
 * @Author Colin
 * @Date 2023/10/30 2:37
 * @Version 1.0
 */
@Service
public class UserVideoServiceImpl implements UserVideoService {
    @Autowired
    VideoMapper videoMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    UserLikelyMapper userLikelyMapper;
    @Autowired
    CollectMapper collectMapper;
    @Autowired
    LikeMapper likeMapper;
    List<Video> videoList = new ArrayList<>();
    int currentList = 0;
    @Override
    public Result incrVideoLike(int videoId, int delta) {
        return null;
    }

    @Override
    public Result decrVideoLike(int videoId, int delta) {
        return null;
    }

    @Override
    public Result addVideo(VideoReqDto reqDto) {

            UsernamePasswordAuthenticationToken authentication =
                    (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
            UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
            User user = loginUser.getUser();

            Date now = new Date();

            Video vedio = new Video(
                    null,
                    user.getId(),
                    reqDto.getTitle(),
                    reqDto.getDescription(),
                    reqDto.getType(),
                    reqDto.getVideourl(),
                    reqDto.getPhotourl(),
                    0,
                    0,
                    0,
                    0,
                    0,
                    now,
                    now
            );

            videoMapper.insert(vedio);
        return Result.success(null);
    }

    @Override
    public Result initVideoLike(int userId) {
        Date now = new Date();
        UsertLikely newUserLikely= new UsertLikely(
                null,
                userId,
                10,10,10,10,10,10,10,
                now,now

        );
        return Result.success(null);
    }

    @Override
    public Result getVideo() {
        Map<String,Object> res = new HashMap<>();

        if(currentList!=videoList.size()){
            currentList++;
            res.put("video",videoList.get(currentList));
            res.put("user",userMapper.selectById(videoList.get(currentList).getUserId()));
            return Result.success(res);
        }

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

        //Video video1 = videoMapper.selectById(13);


        Integer selectedOption = selectRandomWeightedOption(weightedItems);

        QueryWrapper<Video> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("type",selectedOption);
        List<Video> videos = videoMapper.selectList(queryWrapper1);

        int videoIndex = (int) (Math.random()* videos.size());
        Video video = videos.get(videoIndex);

        User user = userMapper.selectById(video.getUserId());
        User userShow = new User();
        userShow.setId(user.getId());
        userShow.setPhoto(user.getPhoto());
        userShow.setNickname(user.getNickname());

        QueryWrapper<Collects> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("user_id",video.getUserId()).eq("video_id",video.getId());
        Collects findCollect= collectMapper.selectOne(queryWrapper2);
        if(findCollect!=null){
            res.put("is_collect",true);
        }else{
            res.put("is_collect",false);
        }

        QueryWrapper<Likes> queryWrapper3 = new QueryWrapper<>();
        queryWrapper3.eq("user_id",video.getUserId()).eq("video_id",video.getId());
        Likes findLike= likeMapper.selectOne(queryWrapper3);
        if(findLike!=null){
            res.put("is_like",true);
        }else{
            res.put("is_like",false);
        }


        res.put("video",video);
        res.put("user",userMapper.selectById(userShow));

        return Result.success(res);

    }

    @Override
    public Result getUserVideos() {
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User loginuser = loginUser.getUser();

        QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",loginuser.getId());
        List<Video> videos = videoMapper.selectList(queryWrapper);
        return Result.success(videos);
    }

    @Override
    public Result getUserCollects() {
        return null;
    }

    @Override
    public Result getUserLikes() {
        return null;
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
