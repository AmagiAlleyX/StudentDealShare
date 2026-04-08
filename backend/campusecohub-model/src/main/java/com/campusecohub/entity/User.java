package com.campusecohub.entity;

import lombok.Data;

import java.util.Date;

/**
 * 用户实体类
 */
@Data
public class User {
    /**
     * 用户ID
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 微信openid
     */
    private String wechatOpenid;

    /**
     * 学生认证状态（0：未认证，1：已认证）
     */
    private Integer studentStatus;

    /**
     * 学校ID
     */
    private Long schoolId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 学校信息（关联查询）
     */
    private School school;
}
