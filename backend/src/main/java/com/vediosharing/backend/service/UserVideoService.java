package com.vediosharing.backend.service;

import com.vediosharing.backend.core.constant.Result;

/**
 * @ClassName UserVideoService
 * @Description TODO
 * @Author Colin
 * @Date 2023/10/30 2:39
 * @Version 1.0
 */
public interface UserVideoService {
    Result incrVideoLike(int videoId,int delta);
    Result decrVideoLike(int videoId,int delta);
    Result initVideoLike(int userId);
    Result getVideo();
}
