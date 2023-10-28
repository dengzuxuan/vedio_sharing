package com.vediosharing.backend.service.Impl;

import com.vediosharing.backend.core.constant.Result;
import com.vediosharing.backend.dao.entity.Message;
import com.vediosharing.backend.dao.mapper.MessageMapper;
import com.vediosharing.backend.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

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

    @Override
    public int getNotReadMessage(int userId) {
        return 0;
    }

    @Override
    public void addMessage(int type, String message, int recvUserId, int sendUserId) {
        Date now = new Date();
        Message newMessage = new Message(
                null,
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

}
