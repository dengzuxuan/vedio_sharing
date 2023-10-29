package com.vediosharing.backend.service;

import com.vediosharing.backend.core.constant.Result;
import org.springframework.web.multipart.MultipartFile;

/**
 * @ClassName ResourceService
 * @Description TODO
 * @Author Colin
 * @Date 2023/10/26 17:57
 * @Version 1.0
 */
public interface ResourceService {
    Result uploadPhoto(MultipartFile file);
    Result uploadVideo(MultipartFile file,int type);
}
