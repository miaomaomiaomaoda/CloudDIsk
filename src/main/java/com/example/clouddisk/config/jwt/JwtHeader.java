package com.example.clouddisk.config.jwt;

import lombok.Data;

/**
 * @author R.Q.
 * brief:描述JWT的元数据，定义了生成签名的算法以及 Token 的类型
 */
@Data
public class JwtHeader {
    /**
     * 签名算法
     */
    private String alg;
    /**
     * token的类型
     */
    private String typ;
}
