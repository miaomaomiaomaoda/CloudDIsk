package com.example.clouddisk.config.jwt;

import lombok.Data;

/**
 * @author R.Q.
 */
@Data
public class JwtHeader {
    private String alg;     //签名算法
    private String typ;     //类型
}
