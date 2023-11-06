package com.vediosharing.backend.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.vediosharing.backend.core.constant.Result;
import com.vediosharing.backend.dao.entity.Message;
import com.vediosharing.backend.dao.entity.User;
import com.vediosharing.backend.dao.mapper.MessageMapper;
import com.vediosharing.backend.dao.mapper.UserMapper;
import com.vediosharing.backend.dto.resp.MessageDetailRespDto;
import com.vediosharing.backend.service.Impl.utils.UserDetailsImpl;
import com.vediosharing.backend.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName MessageServiceImpl
 * @Description 消息相关服务层
 * @Author Colin
 * @Date 2023/10/28 2:07
 * @Version 1.0
 */
@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private UserMapper userMapper;


    @Override
    public Result getNotReadMessageCount() {
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User user = loginUser.getUser();

        QueryWrapper<Message> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("recv_userid",user.getId()).eq("status",0);
        List<Message> messagesNotRead = messageMapper.selectList(queryWrapper);
        return Result.success(messagesNotRead.size());
    }

    @Override
    public void addMessage(int type,String pre, String message, int recvUserId, int sendUserId) {
        if(sendUserId == recvUserId){
            return;
        }

        Date now = new Date();
        Message newMessage = new Message(
                null,
                pre,
                message,
                recvUserId,
                sendUserId,
                type,
                0,
                now,
                now
        );

        messageMapper.insert(newMessage);
    }

    @Override
    public Result getTypeMessage(int type) {
        List<MessageDetailRespDto> respDtoList = new ArrayList<>();
        List<Message> messages = new ArrayList<>();
        QueryWrapper<Message> queryWrapper = new QueryWrapper<>();

        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
        User user = loginUser.getUser();

        if(type == 0){
            queryWrapper.eq("recv_userid",user.getId());
            messages = messageMapper.selectList(queryWrapper);
        }else{
            queryWrapper.eq("recv_userid",user.getId()).eq("type",type);
            messages = messageMapper.selectList(queryWrapper);
        }

        //重置未读状态
        UpdateWrapper<Message> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("recv_userid",user.getId()).set("status",1);
        messageMapper.update(null,updateWrapper);

        for(Message message:messages){
            User user2 = userMapper.selectById(message.getSendUserid());
            user2.setPasswordReal(null);
            user2.setPassword(null);

            respDtoList.add(new MessageDetailRespDto(message,user2));
        }

        queryWrapper.eq("type",type);

        return Result.success(respDtoList);
    }

}
