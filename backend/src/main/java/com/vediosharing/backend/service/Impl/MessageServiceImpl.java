package com.vediosharing.backend.service.Impl;

import com.vediosharing.backend.core.constant.Result;
import com.vediosharing.backend.service.MessageService;

/**
 * @ClassName MessageServiceImpl
 * @Description 消息相关服务层
 * @Author Colin
 * @Date 2023/10/28 2:07
 * @Version 1.0
 */
public class MessageServiceImpl implements MessageService {
    @Override
    public int getNotReadMessage(int userId) {
        return 0;
    }

    @Override
    public Result addMessage(int Type, String message, int sendUserId) {
        return null;
    }
}
