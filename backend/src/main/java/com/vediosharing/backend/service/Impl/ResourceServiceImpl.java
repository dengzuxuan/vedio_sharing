package com.vediosharing.backend.service.Impl;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.vediosharing.backend.core.common.constant.result.ResultCodeEnum;
import com.vediosharing.backend.core.constant.Result;
import com.vediosharing.backend.core.utils.PathUtils;
import com.vediosharing.backend.dao.entity.User;
import com.vediosharing.backend.dao.entity.Video;
import com.vediosharing.backend.dao.mapper.VideoMapper;
import com.vediosharing.backend.service.Impl.utils.UserDetailsImpl;
import com.vediosharing.backend.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.qiniu.http.Response;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName ResourceServiceImpl
 * @Description TODO
 * @Author Colin
 * @Date 2023/10/26 17:57
 * @Version 1.0
 */
@Service
public class ResourceServiceImpl implements ResourceService {
    @Autowired
    VideoMapper vedioMapper;
    @Override
    public Result uploadPhoto(MultipartFile file) {
        //判断文件类型或者文件大小
        String originalFilename = file.getOriginalFilename();
        //如果判断通过再上传文件到OSS
        String filePath = PathUtils.generateFilePath(originalFilename);

        //如果判断没通过抛出异常
        if (!originalFilename.endsWith(".png") && !originalFilename.endsWith(".jpg")){
            return Result.build(null, ResultCodeEnum.PHOTO_PARAMS_WRONG);
        }

        String url = UploadOss(file,filePath);
        Map<String,String> res = new HashMap<>();
        res.put("photo_url",url);

        return Result.success(res);
    }

    @Override
    public Result uploadVideo(MultipartFile file) {
            //判断文件类型或者文件大小
            String originalFilename = file.getOriginalFilename();
            //如果判断通过再上传文件到OSS
            String filePath = PathUtils.generateFilePath(originalFilename);

            //如果判断没通过抛出异常
            if (!originalFilename.endsWith(".mp4")){
                return Result.build(null, ResultCodeEnum.VIDEO_PARAMS_WRONG);
            }

            String url = UploadOss(file,filePath);
//
//            UsernamePasswordAuthenticationToken authentication =
//                    (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
//            UserDetailsImpl loginUser = (UserDetailsImpl) authentication.getPrincipal();
//            User user = loginUser.getUser();
//
//            Date now = new Date();
//
//            Video vedio = new Video(
//                    null,
//                    user.getId(),
//                    type,
//                    url,
//                    0,
//                    0,
//                    0,
//                    0,
//                    now,
//                    now
//            );
//
//            vedioMapper.insert(vedio);

            Map<String,String> res = new HashMap<>();
            res.put("video_url",url);

            return Result.success(res);
    }



        //图片上传操作
        private String UploadOss(MultipartFile file, String filePath){
            //构造一个带指定 Region 对象的配置类
            Configuration cfg = new Configuration(Region.huanan());
            cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
            //...其他参数参考类注释

            UploadManager uploadManager = new UploadManager(cfg);
            //...生成上传凭证，然后准备上传
            String accessKey = "97OSddKvoSFShgtTJ4MVpBkPROimA1l3xN4KNyFK";
            String secretKey = "v7xyZ8rjRaCA9SIAhf-wAk2PnCeE2qpSxqcYh3cu";
            String bucket = "vedio-sharing";

            //默认不指定key的情况下，以文件内容的hash值作为文件名
            String key = filePath;

            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);
            try {
                InputStream inputStream = file.getInputStream();
                try {
                    Response response = uploadManager.put(inputStream,key,upToken,null, null);
                    //解析上传成功的结果
                    DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                } catch (QiniuException ex) {
                    Response r = ex.response;
                    System.err.println(r.toString());
                    try {
                        System.err.println(r.bodyString());
                    } catch (QiniuException ex2) {
                        //ignore
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


            return "s34n6l898.hn-bkt.clouddn.com/" + key;
        }
    }

