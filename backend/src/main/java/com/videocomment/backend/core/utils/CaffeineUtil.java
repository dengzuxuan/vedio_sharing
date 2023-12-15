package com.videocomment.backend.core.utils;

import com.videocomment.backend.dao.entity.History;
import com.videocomment.backend.dao.mapper.VideoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName CaffeineUtil
 * @Description 内存相关操作
 * @Author Colin
 * @Date 2023/11/6 1:03
 * @Version 1.0
 */
@Component
public class CaffeineUtil {
    @Autowired
    VideoMapper videoMapper;
    //新增缓存
    @Cacheable(value = "videoHistory" ,key = "#userId")
    public History getHistory(Integer userId){
        //根据推荐算法获得视频 【初始化】
        Map<Integer,Integer> history = new HashMap<>();
        return new History(userId,history,0);
    }

    //正常向下新增视频
    @CachePut(value = "videoHistory" ,key = "#history.userId")
    public History addNextHistory(History history,Integer videoId){
        //根据推荐算法获得视频 -> 向map里新增一个视频
        Map<Integer, Integer> historypre = history.getHistory();
        historypre.put(historypre.size()+1,videoId);
        history.setHistory(historypre);
        history.setCurrentIndex(history.getCurrentIndex()+1);
        return history;
    }

    //在历史视频中向下滑动视频 获取历史视频
    @CachePut(value = "videoHistory" ,key = "#history.userId")
    public History addCurrentIndex(History history){
        history.setCurrentIndex(history.getCurrentIndex()+1);
        return history;
    }

    //向上滑动视频 获取之前的历史视频
    @CachePut(value = "videoHistory" ,key = "#history.userId")
    public History deCurrentIndex(History history){
        //更新currentIndex 根据currentIndex获取下一条视频
        history.setCurrentIndex(history.getCurrentIndex()-1);
        return history;
    }

    @CachePut(value = "videoHistory" ,key = "#history.userId")
    public History clearHistory(History history){
        //根据推荐算法获得视频 -> 向map里新增一个视频
        Map<Integer,Integer> historyClear = new HashMap<>();
        history.setCurrentIndex(0);
        history.setHistory(historyClear);
        return history;
    }
}
