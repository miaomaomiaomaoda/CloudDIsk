package com.example.clouddisk.controller;

import com.example.clouddisk.common.RestResult;
import com.example.clouddisk.dto.LoginVO;
import com.example.clouddisk.dto.RegisterDTO;
import com.example.clouddisk.model.User;
import com.example.clouddisk.service.UserService;
import com.example.clouddisk.util.JWTUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author R.Q.
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    UserService userService;

    @Resource
    JWTUtil jwtUtil;

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

    /**
     * function:用户注册
     * @param registerDTO 接口请求参数
     * @return
     */
    @PostMapping(value = "/register")
    @ResponseBody
    public RestResult<String> register(@RequestBody RegisterDTO registerDTO){
        RestResult<String> restResult = null;
        User user = new User();
        user.setUsername(registerDTO.getUsername());
        user.setTelephone(registerDTO.getTelephone());
        user.setPassword(registerDTO.getPassword());

        restResult = userService.registerUser(user);
        return restResult;
    }

    /**
     * function:用户登录
     * @param telephone
     * @param password
     * @return
     */
    @GetMapping(value = "/login")
    @ResponseBody
    public RestResult<LoginVO> userLogin(String telephone,String password){
        RestResult<LoginVO> restResult = new RestResult<>();
        LoginVO loginVO = new LoginVO();
        User user = new User();
        user.setTelephone(telephone);
        user.setPassword(password);
        RestResult<User> loginResult = userService.login(user);
        if(!loginResult.getSuccess()){
            return RestResult.fail().message("登录失败!");
        }

        loginVO.setUsername(loginResult.getData().getUsername());
        String jwt = "";
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            jwt = jwtUtil.createJWT(objectMapper.writeValueAsString(loginResult.getData()));
        }catch(Exception e){
            return RestResult.fail().message("登录失败!");
        }
        loginVO.setToken(jwt);
        return RestResult.success().data(loginVO);
    }

    /**
     * brief:token校验接口
     * function:如果 token 不正确，或者 token 过期，就会导致解码失败，返回认证失败，如果能够正确解析，那么就会返回成功。
     * @param token
     * @return
     */
    @GetMapping(value="/checkuserlogininfo")
    @ResponseBody
    public RestResult<User> checkToken(@RequestHeader("token") String token){
        RestResult<User> restResult = new RestResult<>();
        User tokenUserInfo = null;
        try{
            Claims claims = jwtUtil.parseJWT(token);
            String subject = claims.getSubject();
            ObjectMapper objectMapper = new ObjectMapper();
            tokenUserInfo = objectMapper.readValue(subject,User.class);
        }catch (Exception e){
            log.error("解码异常");
            return RestResult.fail().message("认证失败");
        }
        if(tokenUserInfo!=null){
            return RestResult.success().data(tokenUserInfo);
        }else{
            return RestResult.fail().message("用户暂未登录");
        }
    }

}
