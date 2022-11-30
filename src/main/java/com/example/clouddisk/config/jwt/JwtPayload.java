package com.example.clouddisk.config.jwt;

import lombok.Data;

/**
 * @author R.Q.
 * brief:JWT的载荷
 */
@Data
public class JwtPayload {
    private RegisterdClaims registerdClaims;      //实体
}
