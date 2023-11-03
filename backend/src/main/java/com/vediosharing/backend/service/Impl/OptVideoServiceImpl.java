package com.vediosharing.backend.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.vediosharing.backend.core.common.constant.result.ResultCodeEnum;
import com.vediosharing.backend.core.constant.MessageConsts;
import com.vediosharing.backend.core.constant.Result;
import com.vediosharing.backend.dao.entity.*;
import com.vediosharing.backend.dao.mapper.*;
import com.vediosharing.backend.dto.req.CommentReqDto;
import com.vediosharing.backend.dto.resp.VideoDetailRespDto;
import com.vediosharing.backend.service.Impl.utils.UserDetailsImpl;
import com.vediosharing.backend.service.OptVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName OptVideoServiceImpl
 * @Description TODO
 * @Author Colin
 * @Date 2023/10/31 14:10
 * @Version 1.0
 */
@Service
public class OptVideoServiceImpl implements OptVideoService {
    @Autowired
    LikeMapper likeMapper;
    @Autowired
    CollectMapper collectMapper;
    @Autowired
    VideoMapper videoMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    MessageServiceImpl messageService;
    @Override
    public Result addLike(int videoId) {
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User user = loginUser.getUser();
        Date now = new Date();

        Video findVideo = videoMapper.selectById(videoId);
        if(findVideo == null){
            return Result.build(null, ResultCodeEnum.VIDEO_NOT_EXIST);
        }

        QueryWrapper<Likes> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",user.getId()).eq("video_id",videoId);
        Likes findLike = likeMapper.selectOne(queryWrapper);
        if(findLike!=null){
            return Result.build(null,ResultCodeEnum.LIKE_ALREADY_EXIST);
        }

        Likes newLike = new Likes(
                null,
                user.getId(),
                videoId,
                now,
                now
        );
        likeMapper.insert(newLike);

        //在video表中新增
        findVideo.setLikePoints(findVideo.getLikePoints()+1);
        videoMapper.updateById(findVideo);
        return Result.success(null);
    }

    @Override
    public Result delLike(int videoId) {
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User user = loginUser.getUser();

        Video findVideo = videoMapper.selectById(videoId);
        if(findVideo == null){
            return Result.build(null,ResultCodeEnum.VIDEO_NOT_EXIST);
        }

        QueryWrapper<Likes> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",user.getId()).eq("video_id",videoId);
        Likes findLike = likeMapper.selectOne(queryWrapper);
        if(findLike == null){
            return Result.build(null, ResultCodeEnum.LIKE_NOT_EXIST);
        }
        likeMapper.delete(queryWrapper);


        //在video表中减去
        findVideo.setLikePoints(findVideo.getLikePoints()-1);
        videoMapper.updateById(findVideo);
        return Result.success(null);
    }

    @Override
    public Result addcollect(int videoId) {
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User user = loginUser.getUser();
        Date now = new Date();

        Video findVideo = videoMapper.selectById(videoId);
        if(findVideo == null){
            return Result.build(null, ResultCodeEnum.VIDEO_NOT_EXIST);
        }

        QueryWrapper<Collects> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",user.getId()).eq("video_id",videoId);
        Collects findCollect = collectMapper.selectOne(queryWrapper);
        if(findCollect!=null){
            return Result.build(null,ResultCodeEnum.COLLECT_ALREADY_EXIST);
        }

        Collects newCollect = new Collects(
                null,
                user.getId(),
                videoId,
                now,
                now
        );
        collectMapper.insert(newCollect);

        //在video表中新增
        findVideo.setCollectPoints(findVideo.getCollectPoints()+1);
        videoMapper.updateById(findVideo);
        return Result.success(null);
    }

    @Override
    public Result delcollect(int videoId) {
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User user = loginUser.getUser();
        Date now = new Date();

        Video findVideo = videoMapper.selectById(videoId);
        if(findVideo == null){
            return Result.build(null, ResultCodeEnum.VIDEO_NOT_EXIST);
        }

        QueryWrapper<Collects> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",user.getId()).eq("video_id",videoId);
        Collects findCollect = collectMapper.selectOne(queryWrapper);
        if(findCollect==null){
            return Result.build(null,ResultCodeEnum.COLLECT_NOT_EXIST);
        }
        collectMapper.delete(queryWrapper);

        //在video表中减去
        findVideo.setCollectPoints(findVideo.getCollectPoints()-1);
        videoMapper.updateById(findVideo);
        return Result.success(null);
    }

    @Override
    public Result getcollectVideo() {
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User user = loginUser.getUser();

        return Result.success(getSingleUserCollect(user.getId()));
    }

    @Override
    public Result getlikeVideo() {
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User user = loginUser.getUser();

        return Result.success(getSingleUserLike(user.getId()));
    }

    @Override
    public Result getOthercollectVideo(int userId) {
        User user = userMapper.selectById(userId);
        if(user==null){
            return Result.build(null,ResultCodeEnum.USER_NAME_NOT_EXIST);
        }
        if (user.getCollectHidden() == 1){
            return Result.success(null);
        }
        return Result.success(getSingleUserCollect(userId));
    }

    @Override
    public Result getOtherlikeVideo(int userId) {
        User user = userMapper.selectById(userId);
        if(user==null){
            return Result.build(null,ResultCodeEnum.USER_NAME_NOT_EXIST);
        }
        if (user.getLikeHidden() == 1){
            return Result.success(null);
        }
        return Result.success(getSingleUserLike(userId));
    }

    @Override
    public Result addcomment(CommentReqDto dto) {
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User user = loginUser.getUser();
        String content = dto.getContent();
        Video video = videoMapper.selectById(dto.getVideoid());
        if(video==null){
            return Result.build(null,ResultCodeEnum.VIDEO_NOT_EXIST);
        }

        int flag = 0;
        if(dto.getCommentid() != 0){
            Comment replyComment = commentMapper.selectById(dto.getCommentid());
            User replyUser = userMapper.selectById(replyComment.getUserId());
            String preComment = "回复 "+replyUser.getNickname() +":";
            content = preComment+content;
            flag=1;
            messageService.addMessage(MessageConsts.REPLYCOMMENT,content,replyUser.getId(),user.getId());
        }else{
            messageService.addMessage(MessageConsts.COMMENTVIDEO,content,video.getUserId(),user.getId());
        }
        Date now = new Date();
        Comment newComment = new Comment(
                null,
                content,
                user.getId(),
                dto.getVideoid(),
                dto.getCommentid(),
                flag,
                0,
                now,
                now
        );
        commentMapper.insert(newComment);

        video.setCommentPoints(video.getCommentPoints()+1);
        videoMapper.updateById(video);
        return Result.success(null);
    }

    @Override
    public Result delcomment(int commentId) {
        Comment comment = commentMapper.selectById(commentId);
        if(comment == null){
            return Result.build(null,ResultCodeEnum.COMMENT_NOT_EXIST);
        }

        Video video = videoMapper.selectById(comment.getVideoId());
        video.setCommentPoints(video.getCommentPoints()-1);

        commentMapper.deleteById(commentId);
        videoMapper.updateById(video);
        return Result.success(null);
    }

    @Override
    public Result addLikeComment(int commentId) {
        return null;
    }

    @Override
    public Result delLikeComment(int commentId) {
        return null;
    }

    @Override
    public Result getComments(int videoId) {
        Video video = videoMapper.selectById(videoId);
        if(video==null){
            return Result.build(null, ResultCodeEnum.VIDEO_NOT_EXIST);
        }

        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("video_id",videoId);
        List<Comment> comments = commentMapper.selectList(queryWrapper);
        return Result.success(comments);
    }

    private List<Video> getSingleUserCollect(int userId){
        QueryWrapper<Collects> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        List<Collects> collectsList= collectMapper.selectList(queryWrapper);

        List<Video> collectVideoList = new ArrayList<>();

        for(Collects collects:collectsList){
            Video video = videoMapper.selectById(collects.getVideoId());
            collectVideoList.add(video);
        }
        return collectVideoList;
    }

    private List<Video> getSingleUserLike(int userId){
        QueryWrapper<Likes> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        List<Likes> likesList= likeMapper.selectList(queryWrapper);

        List<Video> likeVideoList = new ArrayList<>();
        for(Likes like:likesList){
            Video video = videoMapper.selectById(like.getVideoId());
            likeVideoList.add(video);
        }
        return likeVideoList;
    }
}
