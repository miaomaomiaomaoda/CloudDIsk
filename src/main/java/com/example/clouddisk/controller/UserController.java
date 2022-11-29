package com.example.clouddisk.controller;

import com.example.clouddisk.common.RestResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author R.Q.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    /**
     * function:成功响应测试
     * @return 成功响应的统一结果返回
     */
    @GetMapping(value ="/test1")
    @ResponseBody
    public RestResult test1(){
        return RestResult.success();
    }

    /**
     * function:失败响应测试
     * @return 失败响应的统一结果返回
     */
    @GetMapping(value ="/test2")
    @ResponseBody
    public RestResult test2(){
        return RestResult.fail();
    }

    /**
     * function:空指针异常测试
     * @return 空指针异常返回统一结果
     */
    @GetMapping(value = "/test3")
    @ResponseBody
    public RestResult test3(){
        String s = null;
        int i = s.length();
        return RestResult.success();
    }
}
