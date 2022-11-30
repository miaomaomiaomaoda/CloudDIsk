package com.example.clouddisk.config.jwt;

import lombok.Data;

/**
 * @author R.Q.
 */
@Data
public class RegisterdClaims {
    private String iss;
    private String exp;
    private String sub;
    private String aud;
}
