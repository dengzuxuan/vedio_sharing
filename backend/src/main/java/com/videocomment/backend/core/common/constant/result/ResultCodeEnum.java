package com.videocomment.backend.core.common.constant.result;

import lombok.Getter;

/**
 * @ClassName ResultCodeEnum
 * @Description 返回值枚举
 * @Author Colin
 * @Date 2023/10/26 0:54
 * @Version 1.0
 */
@Getter
public enum ResultCodeEnum {

    SUCCESS(200,"成功"),
    FAIL(201, "失败"),

    USER_NOT_EXIST(2001,"用户不存在"),

    USER_PASSWORD_WRONG(2002,"用户密码错误"),
    USER_NAME_PARAM_WRONG(2003,"用户名格式有误"),
    USER_NAME_ALREADY_EXIST(2004,"用户已存在"),
    USER_NAME_NOT_EXIST(2005,"该用户不存在"),
    PASSWORD_NOT_EMPTY(2006,"密码不能为空"),
    PASSWORD_NOT_EQUAL(2007,"两次密码输入不等"),
    PASSWORD_PARAM_WRONG(2008,"密码格式有误"),
    SEXUAL_PARAM_WRONG(2009,"性别格式有误"),
    PHOTO_PARAM_WRONG(2010,"头像格式有误"),
    EMAIL_PARAM_WRONG(2011,"邮箱格式有误"),
    NICKNAME_PARAM_WRONG(2012,"昵称格式有误"),
    LIKE_HIDDEN_PARAM_WRONG(2013,"隐藏点赞记录有误"),
    COLLECT_HIDDEN_PARAM_WRONG(2014,"隐藏收藏记录有误"),
    USER_NOT_FRIEND_MY_SELF(2005,"用户不能关注自己"),


    VIDEO_PARAMS_WRONG(3001,"视频上传格式有误"),
    VIDEO_INPUT_WRONG(3002,"上传失败"),
    PHOTO_PARAMS_WRONG(3003,"图片上传格式有误"),
    VIDEO_CANT_DELTE(3004,"无法删除该视频"),
    TYPE_PARAMS_WRONG(3005,"类型格式有误"),
    FRIEND_ADD_WRONG(4001,"你已经关注过了"),

    VIDEO_NOT_ENOUGHT(5000,"视频数量不足"),
    VIDEO_NOT_EXIST(5001,"该视频不存在"),
    LIKE_NOT_EXIST(5002,"您尚未点赞"),
    LIKE_ALREADY_EXIST(5003,"您已经点过赞了"),
    COLLECT_NOT_EXIST(5004,"您尚未收藏该视频"),
    COLLECT_ALREADY_EXIST(5005,"您已经收藏过了"),
    COMMENT_NOT_EXIST(5006,"评论不存在"),
    COMMENT_CANT_DELTE(5007,"无法删除该评论"),
    COMMENT_LIKE_CANT_DELTE(5008,"无法取消该点赞"),
    COMMENT_PARAMS_WRONG(5009,"评论格式有误");

    private final Integer code;

    private final String message;

    private ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}

