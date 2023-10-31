package com.vediosharing.backend.core.constant;

/**
 * @ClassName ApiRouterConsts
 * @Description 路由前缀
 * @Author Colin
 * @Date 2023/10/26 11:51
 * @Version 1.0
 */
public class ApiRouterConsts {
    public static final String API_URL_PREFIX = "/api/v1";
    /**
     * 用户模块请求路径前缀
     */
    public static final String USER_URL_PREFIX = API_URL_PREFIX + "/user";
    /**
     * 资源模块请求路径前缀
     */
    public static final String RESOURCE_URL_PREFIX = API_URL_PREFIX + "/resource";
    /**
     * 用户视频相关请求路径前缀
     */
    public static final String VIDEO_URL_PREFIX = API_URL_PREFIX + "/video";
    /**
     * 用户操作视频相关请求路径前缀
     */
    public static final String OPTVIDEO_URL_PREFIX = API_URL_PREFIX + "/optvideo";
}
