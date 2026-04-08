package com.campusecohub.dto;

import lombok.Data;

/**
 * 用户登录请求DTO
 */
@Data
public class UserLoginDTO {
    /**
     * 手机号
     */
    private String phone;

    /**
     * 密码
     */
    private String password;
}
