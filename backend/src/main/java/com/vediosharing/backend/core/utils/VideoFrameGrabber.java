package com.vediosharing.backend.core.utils;

import org.bytedeco.opencv.opencv_core.*;
import org.bytedeco.opencv.global.opencv_imgcodecs;
import org.bytedeco.javacv.*;

import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;


/**
 * @ClassName VideoFrameGrabber
 * @Description 获取视频第一帧图片
 * @Author Colin
 * @Date 2023/10/31 17:00
 * @Version 1.0
 */
@Component
public class VideoFrameGrabber {
    public static InputStream grabberVideoFramer(MultipartFile file) throws Exception {
        InputStream inputStream = file.getInputStream();
        InputStream outputStrem = null;
        // 最后获取到的视频的图片缓存
        BufferedImage bufferedImage = null;
        // Frame对象
        Frame frame = null;
        // 标识
        int flag = 0;
        FFmpegFrameGrabber fFmpegFrameGrabber = null;
        try {
            //获取视频文件
            fFmpegFrameGrabber = new FFmpegFrameGrabber(inputStream);
            fFmpegFrameGrabber.start();

            // 获取视频总帧数
            int ftp = fFmpegFrameGrabber.getLengthInFrames();

            //对视屏 帧数处理
            while (flag <= ftp) {
                frame = fFmpegFrameGrabber.grabImage();
                //对视频的第10帧进行处理
                if (frame != null && flag == 10) {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    // 图片缓存对象
                    bufferedImage = FrameToBufferedImage(frame);
                    ImageIO.write(bufferedImage, "jpg", byteArrayOutputStream);
                    outputStrem = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
                    break;
                }
                flag++;
            }
        }finally {
            if(fFmpegFrameGrabber != null) {
                fFmpegFrameGrabber.stop();
                fFmpegFrameGrabber.close();
            }
        }
        return outputStrem;
    }
    private static BufferedImage FrameToBufferedImage(Frame frame) {
        // 创建BufferedImage对象
        Java2DFrameConverter converter = new Java2DFrameConverter();
        BufferedImage bufferedImage = converter.getBufferedImage(frame);
        return bufferedImage;
    }


}
