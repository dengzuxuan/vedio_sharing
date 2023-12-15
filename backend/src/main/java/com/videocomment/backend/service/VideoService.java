package com.videocomment.backend.service;

import com.videocomment.backend.core.constant.Result;

/**
 * @ClassName VideoService
 * @Description TODO
 * @Author Colin
 * @Date 2023/11/4 22:28
 * @Version 1.0
 */
public interface VideoService {
    Result getVideo();
    Result getPreVideo();
    Result clearPreVideo();
    Result getTypeVideos(int type);
    Result getTypeDayTop(int type);
    Result getTypeWeekTop(int type);
    Result getTypeMonthTop(int type);
}
