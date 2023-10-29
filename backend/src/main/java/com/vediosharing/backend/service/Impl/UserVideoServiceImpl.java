package com.vediosharing.backend.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.vediosharing.backend.core.constant.Result;
import com.vediosharing.backend.dao.entity.User;
import com.vediosharing.backend.dao.entity.Video;
import com.vediosharing.backend.dao.mapper.UserMapper;
import com.vediosharing.backend.dao.mapper.VideoMapper;
import com.vediosharing.backend.service.UserVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

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
    @Override
    public Result incrVideoLike(int videoId, int delta) {
        return null;
    }

    @Override
    public Result decrVideoLike(int videoId, int delta) {
        return null;
    }

    @Override
    public Result initVideoLike(int userId) {
        return null;
    }

    @Override
    public Result getVideo() {
        Video video = videoMapper.selectById(13);

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
}
