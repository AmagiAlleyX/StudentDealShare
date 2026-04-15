package com.student.dealshare.service.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.student.dealshare.model.dto.UserLoginDTO;
import com.student.dealshare.model.dto.UserRegisterDTO;
import com.student.dealshare.model.dto.UserUpdateDTO;
import com.student.dealshare.model.vo.LoginVO;
import com.student.dealshare.model.vo.UserVO;

/**
 * 用户服务接口
 */
public interface UserService {

    /**
     * 用户注册
     * @param dto 注册信息
     * @return 登录信息
     */
    LoginVO register(UserRegisterDTO dto);

    /**
     * 用户登录
     * @param dto 登录信息
     * @return 登录信息
     */
    LoginVO login(UserLoginDTO dto);

    /**
     * 获取用户信息
     * @param userId 用户 ID
     * @return 用户信息
     */
    UserVO getUserById(Long userId);

    /**
     * 获取当前登录用户信息
     * @return 用户信息
     */
    UserVO getCurrentUser();

    /**
     * 更新当前用户信息
     * @param dto 更新信息
     */
    void updateCurrentUser(UserUpdateDTO dto);

    /**
     * 分页查询用户列表
     * @param page 页码
     * @param size 每页数量
     * @return 用户列表
     */
    Page<UserVO> pageUsers(int page, int size);

    /**
     * 关注用户
     * @param followeeId 被关注用户 ID
     */
    void followUser(Long followeeId);

    /**
     * 取消关注用户
     * @param followeeId 被关注用户 ID
     */
    void unfollowUser(Long followeeId);

    /**
     * 检查是否已关注
     * @param followerId 关注者 ID
     * @param followeeId 被关注者 ID
     * @return true-已关注，false-未关注
     */
    boolean isFollowing(Long followerId, Long followeeId);
}
