package com.campusecohub.service;

import com.campusecohub.dto.UserLoginDTO;
import com.campusecohub.dto.UserRegisterDTO;
import com.campusecohub.dto.UserInfoDTO;
import com.campusecohub.dto.StudentAuthDTO;

/**
 * 用户服务接口
 */
public interface UserService {
    /**
     * 用户注册
     */
    UserInfoDTO register(UserRegisterDTO userRegisterDTO);

    /**
     * 用户登录
     */
    UserInfoDTO login(UserLoginDTO userLoginDTO);

    /**
     * 根据ID查询用户
     */
    UserInfoDTO selectById(Long id);

    /**
     * 学生认证
     */
    boolean studentAuth(Long userId, StudentAuthDTO studentAuthDTO);

    /**
     * 微信登录
     */
    UserInfoDTO wechatLogin(String code);
}
