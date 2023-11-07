package com.vediosharing.backend.service.Impl;

import com.vediosharing.backend.core.constant.Result;
import com.vediosharing.backend.core.utils.SearchUtil;
import com.vediosharing.backend.dao.entity.User;
import com.vediosharing.backend.dao.entity.Video;
import com.vediosharing.backend.dao.mapper.UserMapper;
import com.vediosharing.backend.dao.mapper.VideoMapper;
import com.vediosharing.backend.dto.resp.SearchDeatilRespDto;
import com.vediosharing.backend.dto.resp.VideoDetailRespDto;
import com.vediosharing.backend.service.SearchService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName SearchServiceImpl
 * @Description TODO
 * @Author Colin
 * @Date 2023/11/5 21:57
 * @Version 1.0
 */
@Service
public class SearchServiceImpl implements SearchService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    VideoMapper videoMapper;
    @Autowired
    SearchUtil searchUtil;

    @Override
    public Result search(String content) {

        List<SearchUtil.User> searchUserInfo = searchUtil.searchUserInfo(content);
        List<User> userList = new ArrayList<>();

        for (SearchUtil.User searchuser:searchUserInfo){
            User user = userMapper.selectById(searchuser.getId());
            user.setPassword(null);
            user.setPasswordReal(null);
            userList.add(user);
        }

        List<Video> videoList = videoMapper.selectList(null);
        List<VideoDetailRespDto> videoDetailRespDtos = new ArrayList<>();

        List<SearchUtil.Video> searchVideoInfo = searchUtil.searchVideoInfo(content);
        for(SearchUtil.Video searchvideo:searchVideoInfo){
            Video video = videoMapper.selectById(searchvideo.getId());
            User user = userMapper.selectById(video.getUserId());
            user.setPassword(null);
            user.setPasswordReal(null);
            videoDetailRespDtos.add(new VideoDetailRespDto(user,video));
        }

        SearchDeatilRespDto searchDeatilRespDto = new SearchDeatilRespDto(videoDetailRespDtos,userList);
        return Result.success(searchDeatilRespDto);
    }
}
