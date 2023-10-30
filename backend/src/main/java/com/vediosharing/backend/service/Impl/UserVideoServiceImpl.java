package com.vediosharing.backend.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.vediosharing.backend.core.constant.Result;
import com.vediosharing.backend.core.constant.VideoTypeConsts;
import com.vediosharing.backend.dao.entity.User;
import com.vediosharing.backend.dao.entity.UsertLikely;
import com.vediosharing.backend.dao.entity.Video;
import com.vediosharing.backend.dao.mapper.UserLikelyMapper;
import com.vediosharing.backend.dao.mapper.UserMapper;
import com.vediosharing.backend.dao.mapper.VideoMapper;
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
    @Override
    public Result incrVideoLike(int videoId, int delta) {
        return null;
    }

    @Override
    public Result decrVideoLike(int videoId, int delta) {
        return null;
    }

    @Override
    public Result addVideo(String videoUrl, String picUrl) {
        return null;
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
        UsernamePasswordAuthenticationToken authentication =
            (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User loginuser = loginUser.getUser();
        QueryWrapper<UsertLikely> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",loginuser.getId());
        UsertLikely usertLikely = userLikelyMapper.selectOne(queryWrapper);

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

        User user = userMapper.selectById(video.getUserId());
        User userShow = new User();
        userShow.setId(user.getId());
        userShow.setPhoto(user.getPhoto());
        userShow.setNickname(user.getNickname());
        Map<String,Object> res = new HashMap<>();
        res.put("user",userShow);
        res.put("video",video);
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
