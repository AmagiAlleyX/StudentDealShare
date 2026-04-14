package com.student.dealshare.service.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.student.dealshare.model.dto.PasswordChangeDTO;
import com.student.dealshare.model.dto.UserLoginDTO;
import com.student.dealshare.model.dto.UserRegisterDTO;
import com.student.dealshare.model.dto.UserUpdateDTO;
import com.student.dealshare.model.vo.LoginVO;
import com.student.dealshare.model.vo.UserVO;

public interface UserService {

    LoginVO register(UserRegisterDTO dto);

    LoginVO login(UserLoginDTO dto);

    UserVO getUserById(Long userId);

    UserVO getCurrentUser();

    void updateCurrentUser(UserUpdateDTO dto);

    void changePassword(PasswordChangeDTO dto);

    Page<UserVO> pageUsers(int page, int size);

    void followUser(Long followeeId);

    void unfollowUser(Long followeeId);

    boolean isFollowing(Long userId, Long followeeId);
}
