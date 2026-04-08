package com.campusecohub.dto;

import lombok.Data;

/**
 * 用户信息响应DTO
 */
@Data
public class UserInfoDTO {
    /**
     * 用户ID
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 学生认证状态（0：未认证，1：已认证）
     */
    private Integer studentStatus;

    /**
     * 学校ID
     */
    private Long schoolId;

    /**
     * 学校名称
     */
    private String schoolName;
}
