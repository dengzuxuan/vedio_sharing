package com.vediosharing.backend.service.Impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.vediosharing.backend.core.constant.MessageConsts;
import com.vediosharing.backend.core.constant.Result;
import com.vediosharing.backend.core.common.constant.result.ResultCodeEnum;
import com.vediosharing.backend.core.utils.JwtUtil;
import com.vediosharing.backend.core.utils.RegexUtil;
import com.vediosharing.backend.dao.entity.Collects;
import com.vediosharing.backend.dao.entity.Friend;
import com.vediosharing.backend.dao.entity.User;
import com.vediosharing.backend.dao.entity.Video;
import com.vediosharing.backend.dao.mapper.CollectMapper;
import com.vediosharing.backend.dao.mapper.FriendMapper;
import com.vediosharing.backend.dao.mapper.UserMapper;
import com.vediosharing.backend.dao.mapper.VideoMapper;
import com.vediosharing.backend.dto.req.UserInfoReqDto;
import com.vediosharing.backend.dto.req.UserRegisterReqDto;
import com.vediosharing.backend.service.Impl.utils.UserDetailsImpl;
import com.vediosharing.backend.service.MessageService;
import com.vediosharing.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @ClassName UserServiceImpl
 * @Description 用户模块
 * @Author Colin
 * @Date 2023/10/26 12:11
 * @Version 1.0
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    FriendMapper friendMapper;
    @Autowired
    VideoMapper videoMapper;
    @Autowired
    CollectMapper collectMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private MessageService messageService;
    @Autowired
    private UserVideoServiceImpl userVideoServicel;

    @Override
    public Result register(UserRegisterReqDto dto) {
        if(!RegexUtil.isValidUsername(dto.getUsername())){
            return Result.build(null,ResultCodeEnum.USER_NAME_PARAM_WRONG);
        }

        if(!RegexUtil.isValidPassword(dto.getPassword())){
            return Result.build(null,ResultCodeEnum.PASSWORD_PARAM_WRONG);
        }

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",dto.getUsername());
        List<User> users = userMapper.selectList(queryWrapper);

        if(!users.isEmpty()){
            return Result.build(null,ResultCodeEnum.USER_NAME_ALREADY_EXIST);
        }

        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        String defaultPhotoUrl = "http://s34n6l898.hn-bkt.clouddn.com/photo/photo_default.png";
        Date now = new Date();

        String defaultNickName = "niuniu"+ IdUtil.getSnowflakeNextIdStr() ;

        User newUser = new  User(
                null,
                dto.getUsername(),
                defaultNickName,
                encodedPassword,
                "",
                0,
                defaultPhotoUrl,
                0,
                0,
                0,
                0,
                0,
                false,
                0,
                0,
                0,
                0,
                dto.getPassword(),
                now,
                now
        );
        userMapper.insert(newUser);
        userVideoServicel.initVideoLike(newUser.getId());
        return Result.success(null);
    }

    @Override
    public Result login(UserRegisterReqDto dto) {
        Map<String,Object> map = new HashMap<>();

        if(dto.getUsername().length()<5 || dto.getUsername().length()>15){
            return Result.build(null,ResultCodeEnum.USER_NAME_PARAM_WRONG);
        }

        if(dto.getPassword().length()<5){
            return Result.build(null,ResultCodeEnum.PASSWORD_PARAM_WRONG);
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",dto.getUsername());
        User user = userMapper.selectOne(queryWrapper);

        if(user == null){
            return Result.build(null,ResultCodeEnum.USER_NAME_NOT_EXIST);
        }

        if(!Objects.equals(user.getPasswordReal(), dto.getPassword())){
            return Result.build(null,ResultCodeEnum.USER_PASSWORD_WRONG);
        }

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(dto.getUsername(),dto.getPassword());
        Authentication authenicate = authenticationManager.authenticate(authenticationToken);//登录失败会自动处理
        UserDetailsImpl loginUser = (UserDetailsImpl) authenicate.getPrincipal();

        String jwt = JwtUtil.createJWT(loginUser.getUser().getId().toString());

        map.put("token",jwt);
        map.put("userid", loginUser.getUser().getId());
        return Result.success(map);
    }

    @Override
    public Result userInfo() {
        User user = null;
        //获取上下文
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        user = loginUser.getUser();

        user.setPasswordReal(null);
        user.setPassword(null);

        QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",user.getId());

        int views = 0,collects = 0,likes = 0;
        List<Video> videoList = videoMapper.selectList(queryWrapper);
        for (Video video:videoList){
            views+=video.getViewsPoints();
            collects+=video.getCollectPoints();
            likes+=video.getLikePoints();
        }
        user.setVideos(videoList.size());
        user.setLikes(likes);
        user.setCollects(collects);
        user.setViews(views);

        QueryWrapper<Collects> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("user_id",user.getId());
        List<Collects> collectsList = collectMapper.selectList(queryWrapper1);

        user.setSendCollects(collectsList.size());

        return Result.success(user);
    }

    @Override
    public Result otherUserInfo(Integer userId) {
        User user = userMapper.selectById(userId);

        if(user == null){
            return Result.build(null,ResultCodeEnum.USER_NAME_NOT_EXIST);
        }

        user.setPasswordReal(null);
        user.setPassword(null);

        QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",user.getId());

        int views = 0,collects = 0,likes = 0;
        List<Video> videoList = videoMapper.selectList(queryWrapper);
        for (Video video:videoList){
            views+=video.getViewsPoints();
            collects+=video.getCollectPoints();
            likes+=video.getLikePoints();
        }
        user.setVideos(videoList.size());
        user.setLikes(likes);
        user.setCollects(collects);
        user.setViews(views);

        QueryWrapper<Collects> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("user_id",user.getId());
        List<Collects> collectsList = collectMapper.selectList(queryWrapper1);

        user.setSendCollects(collectsList.size());

        return Result.success(user);
    }

    @Override
    public Result updateInfo(UserInfoReqDto dto) {
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User user = loginUser.getUser();

        if(dto.getSexual()!=0 && dto.getSexual()!=1 && dto.getSexual()!=2){
            return Result.build(null,ResultCodeEnum.SEXUAL_PARAM_WRONG);
        }
         if(dto.getPhoto().isEmpty()){
            return Result.build(null,ResultCodeEnum.PHOTO_PARAMS_WRONG);
        }
        if(!dto.getEmail().isEmpty() && !dto.getEmail().contains("@")){
            return Result.build(null,ResultCodeEnum.EMAIL_PARAM_WRONG);
        }
        if(dto.getNickname().isEmpty()){
            return Result.build(null,ResultCodeEnum.NICKNAME_PARAM_WRONG);
        }
        if(dto.getLikehidden()!=0 && dto.getLikehidden()!=1){
            return Result.build(null,ResultCodeEnum.LIKE_HIDDEN_PARAM_WRONG);
        }
        if(dto.getCollecthidden()!=0 && dto.getCollecthidden()!=1){
            return Result.build(null,ResultCodeEnum.COLLECT_HIDDEN_PARAM_WRONG);
        }

        user.setPhoto(dto.getPhoto());
        user.setNickname(dto.getNickname());
        user.setPhoto(dto.getPhoto());
        user.setEmail(dto.getEmail());
        user.setLikeHidden(dto.getLikehidden());
        user.setCollectHidden(dto.getCollecthidden());

        userMapper.updateById(user);

        return Result.success(null);
    }

    @Override
    public Result addfriend(Integer userId) {
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User user = loginUser.getUser();

        User user1 = userMapper.selectById(userId);
        if(user1==null){
            return Result.build(null,ResultCodeEnum.USER_NAME_NOT_EXIST);
        }

        if(Objects.equals(user1.getId(), user.getId())){
            return Result.build(null,ResultCodeEnum.USER_NOT_FRIEND_MY_SELF);
        }

        QueryWrapper<Friend> friendQueryWrapper = new QueryWrapper<>();
        friendQueryWrapper.eq("recv_userid",userId).eq("send_userid",user.getId());
        Friend friend1 = friendMapper.selectOne(friendQueryWrapper);
        if(friend1!=null){
            return Result.build(null,ResultCodeEnum.FRIEND_ADD_WRONG);
        }

        messageService.addMessage(MessageConsts.FRIENDAPPLY,user1.getUsername()+"申请添加您为好友",user.getId(),user1.getId());
        Date now = new Date();
        Friend friend = new Friend(
                null,
                userId,
                user.getId(),
                now,
                now
        );
        friendMapper.insert(friend);

        //在user表中新增
        user1.setFriends(user1.getFriends()+1);
        userMapper.updateById(user1);

        user.setSendFriends(user.getSendFriends()+1);
        userMapper.updateById(user);

        return Result.success(null);
    }

    @Override
    public Result delfriend(Integer userId) {
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User user = loginUser.getUser();

        QueryWrapper<Friend> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("recv_userid",userId).eq("send_userid",user.getId());
        friendMapper.delete(queryWrapper);

        //在user表中减去
        User videoUser = userMapper.selectById(userId);
        videoUser.setFriends(videoUser.getFriends()-1);
        userMapper.updateById(videoUser);

        user.setSendFriends(user.getSendFriends()-1);
        userMapper.updateById(user);

        return Result.success(null);
    }

    //被关注数
    @Override
    public Result getfriend() {
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User user = loginUser.getUser();

        QueryWrapper<Friend> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("recv_userid",user.getId());
        List<Friend> friends = friendMapper.selectList(queryWrapper);

        List<User> friendInfo = new ArrayList<>();
        for (Friend friend:friends) {
            User user1 = userMapper.selectById(friend.getSendUserid());
            user1.setPasswordReal(null);
            user1.setPassword(null);

            //共同好友(互相关注)
            QueryWrapper<Friend> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("recv_userid",user1.getId()).eq("send_userid",user.getId());
            Friend friend1 = friendMapper.selectOne(queryWrapper1);
            if(friend1 != null){
                user1.setBothfriend(true);
            }else{
                user1.setBothfriend(false);
            }

            friendInfo.add(user1);
        }

        return Result.success(friendInfo);
    }

    //关注他人数量
    @Override
    public Result getSendFriend() {
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User user = loginUser.getUser();

        QueryWrapper<Friend> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("send_userid",user.getId());
        List<Friend> friends = friendMapper.selectList(queryWrapper);

        List<User> friendInfo = new ArrayList<>();
        for (Friend friend:friends) {
            User user1 = userMapper.selectById(friend.getRecvUserid());
            user1.setPasswordReal(null);
            user1.setPassword(null);

            //共同好友(互相关注)
            QueryWrapper<Friend> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("send_userid",user1.getId()).eq("recv_userid",user.getId());
            Friend friend1 = friendMapper.selectOne(queryWrapper1);
            if(friend1 != null){
                user1.setBothfriend(true);
            }else{
                user1.setBothfriend(false);
            }
            friendInfo.add(user1);
        }

        return Result.success(friendInfo);
    }
}
