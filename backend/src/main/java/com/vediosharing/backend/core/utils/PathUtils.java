package com.vediosharing.backend.core.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @ClassName PathUtils
 * @Description 根据上传日期拼接文件夹
 * @Author Colin
 * @Date 2023/10/29 12:25
 * @Version 1.0
 */
public class PathUtils {

    public static String generateFilePath(String fileName){
        //根据日期生成路径
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddss");
        String datePath = sdf.format(new Date());
        //uuid作为文件名
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        //后缀和文件后缀一致
        int index = fileName.lastIndexOf(".");
        // test.jpg -> .jpg
        String fileType = fileName.substring(index);
        return new StringBuilder().append(datePath).append(uuid).append(fileType).toString();
    }
}
