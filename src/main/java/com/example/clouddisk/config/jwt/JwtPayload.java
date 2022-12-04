package com.example.clouddisk.config.jwt;

import lombok.Data;

/**
 * @author R.Q.
 * brief:JWT的载荷,用来存放实际需要传递的数据
 */
@Data
public class JwtPayload {
    /**
     * 关于实体(通常为用户)和其他数据
     */
    private RegisterdClaims registerdClaims;
}
