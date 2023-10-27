package com.vediosharing.backend.service;

import com.vediosharing.backend.core.constant.Result;

/**
 * @ClassName MessageService
 * @Description TODO
 * @Author Colin
 * @Date 2023/10/28 1:32
 * @Version 1.0
 */
public interface MessageService {
    int getNotReadMessage(int userId);

    Result addMessage(int Type,String message,int sendUserId);
}
