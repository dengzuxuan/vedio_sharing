package com.vediosharing.backend.core.constant;

import com.vediosharing.backend.core.common.constant.result.ResultCodeEnum;
import lombok.Data;

/**
 * @ClassName Result
 * @Description 返回值
 * @Author Colin
 * @Date 2023/10/26 0:53
 * @Version 1.0
 */
@Data
public class Result<T> {
    //状态码
    private Integer code;
    //信息
    private String message;
    //数据
    private T data;
    //构造私有化
    private Result(){}
    //设置数据
    public static<T> Result<T> build(T data, ResultCodeEnum resultCodeEnum){
        //创建result对象 设置值 返回对象
        Result<T> result = new Result<>();
        if(data!=null){
            result.setData(data);
        }
        result.setCode(resultCodeEnum.getCode());
        result.setMessage(resultCodeEnum.getMessage());

        return result;
    }
    //成功
    public static<T> Result<T> success(T data){
        Result<T> result = build(data,ResultCodeEnum.SUCCESS);
        return result;
    }
    //失败
    public static<T> Result<T> fail(T data){
        Result<T> result = build(data,ResultCodeEnum.FAIL);
        return result;
    }
}
