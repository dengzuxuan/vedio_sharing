package com.vediosharing.backend.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.vediosharing.backend.core.common.constant.result.ResultCodeEnum;
import com.vediosharing.backend.core.constant.RankConsts;
import com.vediosharing.backend.core.constant.Result;
import com.vediosharing.backend.core.utils.RankUtil;
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
    @Autowired
    FriendMapper friendMapper;
    @Autowired
    RankUtil rankUtil;
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

        //基本基础热度 1~10
        //描述的多 +20
        //标题多 +10
        Random r = new Random();
        int baseHot = r.nextInt(9)+1;
        if(reqDto.getTitle().length()>5){
            baseHot+=10;
        }

        if(reqDto.getDescription().length()>15){
            baseHot+=20;
        }

        Video vedio = new Video(
                null,
                user.getId(),
                reqDto.getTitle(),
                reqDto.getDescription(),
                reqDto.getType(),
                reqDto.getVideourl(),
                reqDto.getPhotourl(),
                baseHot,
                baseHot,
                baseHot,
                baseHot,
                0,
                0,
                0,
                0,
                now,
                now
        );

        videoMapper.insert(vedio);

        //初始redis中的热度
        rankUtil.initRank(RankConsts.TOTAL_RANK,vedio.getType(),baseHot,vedio.getId());
        rankUtil.initRank(RankConsts.MONTH_RANK,vedio.getType(),baseHot,vedio.getId());
        rankUtil.initRank(RankConsts.WEEKLY_RANK,vedio.getType(),baseHot,vedio.getId());
        rankUtil.initRank(RankConsts.DAYLY_RANK,vedio.getType(),baseHot,vedio.getId());

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
        userLikelyMapper.insert(newUserLikely);
        return Result.success(null);
    }

    @Override
    public Result getSingleVideo(int videoId) {
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User loginuser = loginUser.getUser();

        Map<String,Object> res = new HashMap<>();
        Video video = videoMapper.selectById(videoId);

        if(video == null){
            return Result.build(null, ResultCodeEnum.VIDEO_NOT_EXIST);
        }

        video.setViewsPoints(video.getViewsPoints()+1);
        videoMapper.updateById(video);

        rankUtil.addRank(video.getType(),videoId,RankConsts.POINT_INCR);

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
    public Result getUserVideos() {
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User loginuser = loginUser.getUser();

        return Result.success(getSingleUserVideo(loginuser.getId()));
    }

    @Override
    public Result getSingleUserVideos(int userId) {
        User user = userMapper.selectById(userId);
        if(user == null){
            return Result.build(null,ResultCodeEnum.USER_NAME_NOT_EXIST);
        }
        return Result.success(getSingleUserVideo(userId));
    }

    private List<Video> getSingleUserVideo(int userId){
        QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        return videoMapper.selectList(queryWrapper);
    }

}
