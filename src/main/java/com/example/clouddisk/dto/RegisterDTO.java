package com.example.clouddisk.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author R.Q.
 * brief:存放接口请求参数
 */
@Schema(description = "注册DTO")
@Data
public class RegisterDTO {
    @Schema(description = "用户名")
    private String username;
    @Schema(description = "手机号")
    private String telephone;
    @Schema(description = "密码")
    private String password;
}
