package com.vediosharing.backend.service.Impl;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.vediosharing.backend.core.common.constant.result.ResultCodeEnum;
import com.vediosharing.backend.core.config.QiniuStorageConfig;
import com.vediosharing.backend.core.constant.Result;
import com.vediosharing.backend.core.utils.PathUtils;
import com.vediosharing.backend.core.utils.VideoFrameGrabber;
import com.vediosharing.backend.dao.entity.User;
import com.vediosharing.backend.dao.entity.Video;
import com.vediosharing.backend.dao.mapper.VideoMapper;
import com.vediosharing.backend.service.Impl.utils.UserDetailsImpl;
import com.vediosharing.backend.service.ResourceService;
import lombok.Cleanup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.qiniu.http.Response;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
    QiniuStorageConfig config;
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
        Map<String,String> res = new HashMap<>();
        try {
            @Cleanup
            InputStream inputStream = file.getInputStream();
            String url = UploadOss(inputStream,filePath);
            res.put("url",url);
        } catch (IOException e) {
            return Result.build(null, ResultCodeEnum.VIDEO_INPUT_WRONG);
        }

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

        Map<String,String> res = new HashMap<>();
        try {
            String video_url = UploadOss(file.getInputStream(),filePath);
            @Cleanup
            InputStream stream = VideoFrameGrabber.grabberVideoFramer(file);
            String photo_url = UploadOss(stream,UUID.randomUUID().toString().replaceAll("-", "")+".jpg");
            res.put("url",video_url);
            res.put("photo_url",photo_url);
        } catch (Exception e) {
            return Result.build(null, ResultCodeEnum.VIDEO_INPUT_WRONG);
        }
        int lastDotIndex = originalFilename.lastIndexOf(".mp4");
        String fileNameWithoutExtension = originalFilename.substring(0,lastDotIndex);

        res.put("file_title",fileNameWithoutExtension);
        return Result.success(res);
    }


    private String UploadOss(InputStream inputStream, String filePath) throws QiniuException {
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = filePath;
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.huanan());
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
        //...其他参数参考类注释

        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传

        String accessKey = config.getQiniuAccessKey();
        String secretKey = config.getQiniuSecretKey();
        String bucket = config.getQiniuBucketName();


        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        Response response = uploadManager.put(inputStream, key, upToken, null, null);
        //解析上传成功的结果
        DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);

        return config.getQiniuDomain() + key;
    }

    private Boolean DeleteOss(String fileName){
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.autoRegion());
        String accessKey = config.getQiniuAccessKey();
        String secretKey = config.getQiniuSecretKey();
        String bucket = config.getQiniuBucketName();

        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(bucket, fileName);
        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            System.err.println(ex.code());
            System.err.println(ex.response.toString());
            return false;
        }
        return true;
    }
}

