package com.example.clouddisk.common;

import lombok.Getter;

/**
 * @author R.Q.
 * brief:返回结果枚举类
 * params
 * -success:响应是否成功
 * -code:响应状态码
 * -message:响应消息
 */
@Getter
public enum ResultCodeEnum {
    /**
     * 成功
     */
    SUCCESS(true,20000,"成功"),
    /**
     * 未知错误
     */
    UNKNOWN_ERROR(false,20001,"未知错误"),
    /**
     * 参数错误
     */
    PARAM_ERROR(false,20002,"参数错误"),
    /**
     * 空指针异常
     */
    NULL_POINT(false,20003,"空指针异常"),
    /**
     * 下标越界错误
     */
    INDEX_OUT_OF_BOUNDS(false,20004,"下标越界错误");

    private Boolean success;
    private Integer code;
    private String message;

    ResultCodeEnum(boolean success,Integer code,String message){
        this.success = success;
        this.code = code;
        this.message = message;
    }
}
