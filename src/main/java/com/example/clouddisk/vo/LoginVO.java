package com.example.clouddisk.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author R.Q.
 * brief:接口响应参数
 */
@Schema(description = "登录VO")
@Data
public class LoginVO {
    @Schema(description = "用户名")
    private String username;
    @Schema(description = "token")
    private String token;
}
