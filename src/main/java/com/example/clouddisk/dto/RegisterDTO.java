package com.example.clouddisk.dto;

import lombok.Data;

/**
 * @author R.Q.
 * brief:存放接口请求参数
 */
@Data
public class RegisterDTO {
    private String username;
    private String telephone;
    private String password;
}
