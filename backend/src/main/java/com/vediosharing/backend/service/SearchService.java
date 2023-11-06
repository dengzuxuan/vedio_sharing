package com.vediosharing.backend.service;

import com.vediosharing.backend.core.constant.Result;

/**
 * @ClassName SearchService
 * @Description TODO
 * @Author Colin
 * @Date 2023/11/5 21:57
 * @Version 1.0
 */
public interface SearchService {
    Result search(String content);
}
