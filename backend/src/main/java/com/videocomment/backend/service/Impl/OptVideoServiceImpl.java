package com.videocomment.backend.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.videocomment.backend.core.common.constant.result.ResultCodeEnum;
import com.videocomment.backend.core.constant.LikeConsts;
import com.videocomment.backend.core.constant.MessageConsts;
import com.videocomment.backend.core.constant.RankConsts;
import com.videocomment.backend.core.constant.Result;
import com.videocomment.backend.core.utils.OrderDateComparator;
import com.videocomment.backend.core.utils.RankUtil;
import com.videocomment.backend.dao.entity.*;
import com.videocomment.backend.dao.mapper.*;
import com.videocomment.backend.dto.req.CommentReqDto;
import com.videocomment.backend.dto.resp.CommentDetailRespDto;
import com.videocomment.backend.service.Impl.utils.UserDetailsImpl;
import com.videocomment.backend.service.OptVideoService;
import com.videocomment.backend.service.UserVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
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
    @Autowired
    CommentLikesMapper commentLikesMapper;
    @Autowired
    UserVideoService userVideoService;
    @Autowired
    RankUtil rankUtil;
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

        rankUtil.addRank(findVideo.getType(), videoId, RankConsts.LIKE_INCR);

        userVideoService.changeVideoLike(findVideo.getType(), LikeConsts.LIKE_INCR);

        String pre = findVideo.getTitle();
        if(pre.length()>10){
            pre = pre.substring(0, 10)+"...";
        }
        messageService.addMessage(MessageConsts.LIKE,"","点赞了你的视频:"+pre,findVideo.getUserId(),user.getId());
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

        userVideoService.changeVideoLike(findVideo.getType(), LikeConsts.LIKE_DESC);

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

        userVideoService.changeVideoLike(findVideo.getType(), LikeConsts.COLLECT_INCR);

        rankUtil.addRank(findVideo.getType(),videoId, RankConsts.COLLECT_INCR);

        String pre = findVideo.getTitle();
        if(pre.length()>10){
            pre = pre.substring(0, 10)+"...";
        }
        messageService.addMessage(MessageConsts.COLLECT,"","收藏了你的视频:"+pre,findVideo.getUserId(),user.getId());
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

        userVideoService.changeVideoLike(findVideo.getType(), LikeConsts.COLLECT_DESC);

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

        if(dto.getFlag()!=1 && dto.getFlag()!=2 && dto.getFlag()!=3){
            return Result.build(null,ResultCodeEnum.COMMENT_PARAMS_WRONG);
        }
        if(dto.getFlag()==1){
            String pre = video.getTitle();
            if(pre.length()>10){
                pre = pre.substring(0, 10)+"...";
            }
            messageService.addMessage(MessageConsts.COMMENT,pre,content,video.getUserId(),user.getId());
        }else{
            Comment replyComment = commentMapper.selectById(dto.getCommentid());
            if(replyComment == null){
                return Result.build(null,ResultCodeEnum.COMMENT_NOT_EXIST);
            }

            User replyUser = userMapper.selectById(replyComment.getUserId());
            if(replyUser==null){
                return Result.build(null,ResultCodeEnum.USER_NOT_EXIST);
            }
            if(dto.getFlag()==3){
                if(replyComment.getFlag() != 2){
                    return Result.build(null,ResultCodeEnum.COMMENT_PARAMS_WRONG);
                }
                String preComment = "回复 @"+replyUser.getNickname() +" :";
                content = preComment+content;
            }
            String pre = replyComment.getContent();
            if(pre.length()>10){
                pre = pre.substring(0, 10)+"...";
            }
            messageService.addMessage(MessageConsts.COMMENT,pre,content,replyUser.getId(),user.getId());
        }


        Date now = new Date();
        Comment newComment = new Comment(
                null,
                content,
                user.getId(),
                dto.getVideoid(),
                dto.getCommentid(),
                0,
                dto.getFlag(),
                now,
                now
        );
        commentMapper.insert(newComment);

        video.setCommentPoints(video.getCommentPoints()+1);
        videoMapper.updateById(video);

        rankUtil.addRank(video.getType(), dto.getVideoid(), RankConsts.COMMENT_INCR);
        return Result.success(null);
    }

    @Override
    public Result delcomment(int commentId) {
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User user = loginUser.getUser();

        Comment comment = commentMapper.selectById(commentId);
        if(comment == null){
            return Result.build(null,ResultCodeEnum.COMMENT_NOT_EXIST);
        }

        if(comment.getUserId() != user.getId()){
            return Result.build(null,ResultCodeEnum.COMMENT_CANT_DELTE);
        }

        Video video = videoMapper.selectById(comment.getVideoId());
        if(video == null){
            return Result.build(null,ResultCodeEnum.VIDEO_NOT_EXIST);
        }
        //一级评论删除的话，下面所有评论都要删除
        if(comment.getFlag() == 1){
            int delComment = 0;
            //二级
            QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("comment_id",commentId);
            List<Comment> commentSeconds = commentMapper.selectList(queryWrapper);
            commentMapper.delete(queryWrapper);
            delComment+=commentSeconds.size();
            //三级
            for (Comment comment2:commentSeconds){
                QueryWrapper<Comment> queryWrapper1 = new QueryWrapper<>();
                queryWrapper1.eq("comment_id",comment2.getId());
                List<Comment> commentThird = commentMapper.selectList(queryWrapper1);
                commentMapper.delete(queryWrapper1);
                delComment+=commentThird.size();
            }
            video.setCommentPoints(video.getCommentPoints()-delComment);
        }
        commentMapper.deleteById(commentId);
        video.setCommentPoints(video.getCommentPoints()-1);
        videoMapper.updateById(video);
        return Result.success(null);
    }

    @Override
    public Result addLikeComment(int commentId) {
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User user = loginUser.getUser();

        Comment comment = commentMapper.selectById(commentId);
        if(comment==null){
            return Result.build(null,ResultCodeEnum.COMMENT_NOT_EXIST);
        }

        Video video = videoMapper.selectById(comment.getVideoId());
        if(video == null){
            return Result.build(null,ResultCodeEnum.VIDEO_NOT_EXIST);
        }

        QueryWrapper<CommentLikes> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("comment_id",commentId).eq("send_userid",user.getId());
        CommentLikes commentLikes1 = commentLikesMapper.selectOne(queryWrapper);
        if(commentLikes1!=null){
            return Result.build(null,ResultCodeEnum.LIKE_ALREADY_EXIST);
        }

        Date now = new Date();
        CommentLikes commentLikes = new CommentLikes(
                null,
                video.getId(),
                commentId,
                user.getId(),
                now,
                now
        );
        commentLikesMapper.insert(commentLikes);

        comment.setLikes(comment.getLikes()+1);
        commentMapper.updateById(comment);

        String pre = comment.getContent();
        if(pre.length()>10){
            pre = pre.substring(0, 10)+"...";
        }
        messageService.addMessage(MessageConsts.LIKE,"","点赞了你的评论:"+pre,comment.getUserId(),user.getId());


        return Result.success(null);
    }

    @Override
    public Result delLikeComment(int commentId) {
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User user = loginUser.getUser();

        Comment comment = commentMapper.selectById(commentId);
        if(comment==null){
            return Result.build(null,ResultCodeEnum.COMMENT_NOT_EXIST);
        }

        Video video = videoMapper.selectById(comment.getVideoId());
        if(video == null){
            return Result.build(null,ResultCodeEnum.VIDEO_NOT_EXIST);
        }

        QueryWrapper<CommentLikes> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("comment_id",commentId).eq("send_userid",user.getId());
        CommentLikes commentLikes1 = commentLikesMapper.selectOne(queryWrapper);
        if(commentLikes1==null){
            return Result.build(null,ResultCodeEnum.LIKE_NOT_EXIST);
        }

        commentLikesMapper.delete(queryWrapper);

        comment.setLikes(comment.getLikes()-1);
        commentMapper.updateById(comment);
        return Result.success(null);
    }

    @Override
    public Result getFirstComments(int videoId) {
        Video video = videoMapper.selectById(videoId);
        if(video==null){
            return Result.build(null, ResultCodeEnum.VIDEO_NOT_EXIST);
        }
        return Result.success(getComments(videoId,0));
    }

    @Override
    public Result getSecondComments(int commentId) {
        Comment comment = commentMapper.selectById(commentId);
        if(comment==null){
            return Result.build(null, ResultCodeEnum.COMMENT_NOT_EXIST);
        }
        return Result.success(getComments(0,commentId));
    }

    private List<CommentDetailRespDto> getComments(int videoId,int commentId) {
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User user = loginUser.getUser();

        List<Comment> comments = new ArrayList<>();
        if(videoId!=0){
            QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("video_id",videoId).eq("flag",1);
            comments = commentMapper.selectList(queryWrapper);
        }else if(commentId!=0){
            QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("comment_id",commentId) ;
            List<Comment> commentLevel2 = commentMapper.selectList(queryWrapper);
            comments.addAll(commentLevel2);
            for(Comment comment:commentLevel2){
                QueryWrapper<Comment> queryWrapper2 = new QueryWrapper<>();
                queryWrapper2.eq("comment_id",comment.getId()) ;
                comments.addAll(commentMapper.selectList(queryWrapper2));
            }
            Collections.sort(comments, new OrderDateComparator());
        }

        List<CommentDetailRespDto> detailRespDtos = new ArrayList<>();
        for(Comment comment:comments){
            User commentUser = userMapper.selectById(comment.getUserId());
            commentUser.setPassword(null);
            commentUser.setPasswordReal(null);

            QueryWrapper<CommentLikes> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("comment_id",comment.getId()).eq("send_userid",user.getId());
            CommentLikes findCommentLikes = commentLikesMapper.selectOne(queryWrapper1);
            boolean like = findCommentLikes != null;

            CommentDetailRespDto commentDetailRespDto = new CommentDetailRespDto(
                    comment,
                    commentUser,
                    like
            );
            detailRespDtos.add(commentDetailRespDto);
        }
        return detailRespDtos;
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
