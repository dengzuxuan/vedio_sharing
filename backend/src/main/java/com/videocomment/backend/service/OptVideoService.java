package com.videocomment.backend.service;

import com.videocomment.backend.core.constant.Result;
import com.videocomment.backend.dto.req.CommentReqDto;

/**
 * @ClassName OptVideoService
 * @Description TODO
 * @Author Colin
 * @Date 2023/10/31 14:10
 * @Version 1.0
 */
public interface OptVideoService {
    Result addLike(int videoId);
    Result delLike(int videoId);
    Result addcollect(int videoId);
    Result delcollect(int videoId);

    Result getcollectVideo();
    Result getlikeVideo();
    Result getOthercollectVideo(int userId);
    Result getOtherlikeVideo(int userId);
    Result addcomment(CommentReqDto dto);
    Result delcomment(int commentId);
    Result addLikeComment(int commentId);
    Result delLikeComment(int commentId);
    Result getFirstComments(int videoId);
    Result getSecondComments(int commentId);

}
