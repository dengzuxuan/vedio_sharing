package com.videocomment.backend.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.videocomment.backend.core.common.constant.result.ResultCodeEnum;
import com.videocomment.backend.core.constant.LikeConsts;
import com.videocomment.backend.core.constant.RankConsts;
import com.videocomment.backend.core.constant.Result;
import com.videocomment.backend.core.constant.VideoTypeConsts;
import com.videocomment.backend.core.utils.RankUtil;
import com.videocomment.backend.core.utils.SearchUtil;
import com.videocomment.backend.dao.entity.*;
import com.videocomment.backend.dao.mapper.*;
import com.videocomment.backend.dto.req.VideoJudgeReqDto;
import com.videocomment.backend.dto.req.VideoReqDto;
import com.videocomment.backend.service.Impl.utils.UserDetailsImpl;
import com.videocomment.backend.service.UserVideoService;
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
    CommentMapper commentMapper;
    @Autowired
    CommentLikesMapper commentLikesMapper;
    @Autowired
    LikeMapper likeMapper;
    @Autowired
    FriendMapper friendMapper;
    @Autowired
    RankUtil rankUtil;
    @Autowired
    SearchUtil searchUtil;
    @Override
    public Result judge(VideoJudgeReqDto dto) {
        Video video = videoMapper.selectById(dto.getVideoId());
        if(video == null){
            return Result.build(null, ResultCodeEnum.VIDEO_NOT_EXIST);
        }
        if(dto.is1()){
            changeVideoLike(video.getType(), LikeConsts.VIEW_INCR_1);
        }
        if(dto.is2()){
            changeVideoLike(video.getType(), LikeConsts.VIEW_INCR_2);
        }
        if(dto.is4()){
            changeVideoLike(video.getType(), LikeConsts.VIEW_INCR_4);
        }
        return Result.success(null);
    }

    @Override
    public void changeVideoLike(int type,int delta) {
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User user = loginUser.getUser();
        QueryWrapper<UsertLikely> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",user.getId());
        UsertLikely usertLikely = userLikelyMapper.selectOne(queryWrapper);
        UpdateWrapper<UsertLikely> updateWrapper = new UpdateWrapper<>();
        switch (type){
            case VideoTypeConsts.SPORT:
                updateWrapper.eq("user_id",user.getId()).set("sport",usertLikely.getSport()+delta);
                break;
            case VideoTypeConsts.GAME:
                updateWrapper.eq("user_id",user.getId()).set("game",usertLikely.getGame()+delta);
                break;
            case VideoTypeConsts.FOOD:
                updateWrapper.eq("user_id",user.getId()).set("food",usertLikely.getFood()+delta);
                break;
            case VideoTypeConsts.MUSIC:
                updateWrapper.eq("user_id",user.getId()).set("music",usertLikely.getMusic()+delta);
                break;
            case VideoTypeConsts.FUN:
                updateWrapper.eq("user_id",user.getId()).set("fun",usertLikely.getFun()+delta);
                break;
            case VideoTypeConsts.KNOWLEDGE:
                updateWrapper.eq("user_id",user.getId()).set("knowledge",usertLikely.getKnowledge()+delta);
                break;
            case VideoTypeConsts.ANIMAL:
                updateWrapper.eq("user_id",user.getId()).set("animal",usertLikely.getAnimal()+delta);
                break;
        }
        userLikelyMapper.update(null,updateWrapper);
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
        //初始化mongodb
        searchUtil.addVideoInfo(vedio.getId(),vedio.getTitle(),vedio.getDescription());

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

    @Override
    public Result delUserVideo(int videoId) {
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User loginuser = loginUser.getUser();

        Video video = videoMapper.selectById(videoId);
        if(video == null){
            return Result.build(null,ResultCodeEnum.VIDEO_NOT_EXIST);
        }

        if(video.getUserId()!=loginuser.getId()){
            return Result.build(null,ResultCodeEnum.VIDEO_CANT_DELTE);
        }

        //删除视频mysql
        videoMapper.deleteById(videoId);
        //删除被收藏
        QueryWrapper<Collects> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("video_id",videoId);
        collectMapper.delete(queryWrapper);
        //删除被点赞
        QueryWrapper<Likes> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("video_id",videoId);
        likeMapper.delete(queryWrapper1);
        //删除被评论
        QueryWrapper<Comment> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("video_id",videoId);
        commentMapper.delete(queryWrapper2);
        //删除评论区点赞
        QueryWrapper<CommentLikes> queryWrapper3 = new QueryWrapper<>();
        queryWrapper3.eq("video_id",videoId);
        commentLikesMapper.delete(queryWrapper3);
        //删除redis
        rankUtil.delVideoId(video.getType(),videoId);
        //删除mongodb
        searchUtil.delVideoInfo(videoId);

        return Result.success(null);
    }

    private List<Video> getSingleUserVideo(int userId){
        QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        return videoMapper.selectList(queryWrapper);
    }

}
