package com.vediosharing.backend.core.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName CaffeineConfig
 * @Description TODO
 * @Author Colin
 * @Date 2023/11/5 23:33
 * @Version 1.0
 */
@Configuration
public class CaffeineConfig {
    //配置内存缓存过期时间，不超过1天
    @Bean
    public CacheManager caffeineCacheManager(){
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        List<CaffeineCache> caches = new ArrayList<>();
        caches.add(new CaffeineCache("videoHistory", Caffeine.newBuilder().expireAfterWrite(1, TimeUnit.DAYS).build()) );
        cacheManager.setCaches(caches);
        return cacheManager;
    }
}
