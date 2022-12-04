package com.example.clouddisk.advice;

import com.example.clouddisk.common.RestResult;
import com.example.clouddisk.common.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author R.Q.
 * brief:全局通用异常处理类，避免出现异常场景，前台无响应
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandlerAdvice {

    /**
     * function:通用异常处理方法
     * @param e 异常
     * @return 失败的统一结果返回
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public RestResult error(Exception e){
        e.printStackTrace();
        log.error("全局异常捕获:"+e);
        return RestResult.fail();
    }

    /**
     * function:空指针异常处理
     * @param e 异常
     * @return 空指针错误统一结果返回
     */
    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public RestResult error(NullPointerException e){
        e.printStackTrace();
        log.error("全局异常捕获:"+e);
        return RestResult.setResult(ResultCodeEnum.NULL_POINT);
    }

    /**
     * function:下标越界异常处理
     * @param e 异常
     * @return 下标越界错误统一结果返回
     */
    @ExceptionHandler(IndexOutOfBoundsException.class)
    @ResponseBody
    public RestResult error(IndexOutOfBoundsException e){
        e.printStackTrace();
        log.error("全局异常捕获:"+e);
        return RestResult.setResult(ResultCodeEnum.INDEX_OUT_OF_BOUNDS);
    }
}
