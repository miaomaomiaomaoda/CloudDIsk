package com.example.clouddisk.common;

import lombok.Data;

/**
 * @author R.Q.
 * brief:统一结果返回类,封装结果统一返回
 * @param<T>
 */
@Data
public class RestResult<T> {
    private Boolean success = true;
    private Integer code;
    private String message;
    private T data;

    /**
     * function:通用返回成功
     * @return 成功返回
     */
    public static RestResult success(){
        RestResult r = new RestResult();
        r.setSuccess(ResultCodeEnum.SUCCESS.getSuccess());
        r.setCode(ResultCodeEnum.SUCCESS.getCode());
        r.setMessage(ResultCodeEnum.SUCCESS.getMessage());
        return r;
    }

    /**
     * function:通用返回失败,未知错误
     * @return 失败返回
     */
    public static RestResult fail(){
        RestResult r = new RestResult();
        r.setSuccess(ResultCodeEnum.UNKNOWN_ERROR.getSuccess());
        r.setCode(ResultCodeEnum.UNKNOWN_ERROR.getCode());
        r.setMessage(ResultCodeEnum.UNKNOWN_ERROR.getMessage());
        return r;
    }

    /**
     * function:自定义返回数据
     * @param param 自定义返回数据
     * @return 统一返回结果
     */
    public RestResult data(T param){
        this.setData(param);
        return this;
    }

    /**
     * function:自定义返回信息
     * @param message 自定义返回信息
     * @return 统一返回结果
     */
    public RestResult message(String message){
        this.setMessage(message);
        return this;
    }
    /**
     * function:自定义返回状态码
     * @param code 自定义返回状态码
     * @return 统一返回结果
     */
    public RestResult data(Integer code){
        this.setCode(code);
        return this;
    }

    /**
     * function:使用返回结果枚举设置返回
     * @param result 返回结果枚举类
     * @return 返回结果
     */
    public static RestResult setResult(ResultCodeEnum result){
        RestResult r = new RestResult();
        r.setSuccess(result.getSuccess());
        r.setCode(result.getCode());
        r.setMessage(result.getMessage());
        return r;
    }
}
