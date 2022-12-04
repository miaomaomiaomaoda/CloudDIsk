package com.example.clouddisk.config.jwt;

import lombok.Data;

/**
 * @author R.Q.
 * brief:Registered Claims(注册声明):预定义的一些声明，建议使用，但不是强制性的
 */
@Data
public class RegisterdClaims {
    /**
     * issuer,JWT 签发方。
     */
    private String iss;
    /**
     * expiration time,JWT 的过期时间
     */
    private String exp;
    /**
     * subject:JWT 主题
     */
    private String sub;
    /**
     * audience:JWT 接收方
     */
    private String aud;
}
