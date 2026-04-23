package com.student.dealshare.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.student.dealshare.common.exception.BusinessException;
import com.student.dealshare.common.result.ResultCodeEnum;
import com.student.dealshare.converter.UserConverter;
import com.student.dealshare.mapper.UserFollowMapper;
import com.student.dealshare.mapper.UserMapper;
import com.student.dealshare.model.dto.UserLoginDTO;
import com.student.dealshare.model.dto.UserRegisterDTO;
import com.student.dealshare.model.dto.UserUpdateDTO;
import com.student.dealshare.model.entity.User;
import com.student.dealshare.model.entity.UserFollow;
import com.student.dealshare.model.vo.LoginVO;
import com.student.dealshare.model.vo.UserVO;
import com.student.dealshare.security.JwtTokenProvider;
import com.student.dealshare.security.SecurityUtils;
import com.student.dealshare.service.api.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final UserMapper userMapper;
    private final UserFollowMapper userFollowMapper;
    private final UserConverter userConverter;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public LoginVO register(UserRegisterDTO dto) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, dto.getUsername());
        User existUser = userMapper.selectOne(wrapper);
        if (existUser != null) {
            throw new BusinessException(ResultCodeEnum.USERNAME_ALREADY_EXISTS);
        }

        User user = userConverter.toEntity(dto);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setStatus(1);
        userMapper.insert(user);

        log.info("用户注册成功，userId: {}", user.getId());

        return buildLoginVO(user);
    }

    @Override
    public LoginVO login(UserLoginDTO dto) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, dto.getUsername());
        User user = userMapper.selectOne(wrapper);
        
        if (user == null || !passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new BusinessException(ResultCodeEnum.INVALID_CREDENTIALS);
        }

        if (user.getStatus() == 0) {
            throw new BusinessException(ResultCodeEnum.USER_DISABLED);
        }

        log.info("用户登录成功，userId: {}", user.getId());

        return buildLoginVO(user);
    }

    @Override
    public UserVO getUserById(Long userId) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException(ResultCodeEnum.USER_NOT_FOUND);
        }
        return userConverter.toVO(user);
    }

    @Override
    public UserVO getCurrentUser() {
        Long userId = SecurityUtils.getCurrentUserId();
        return getUserById(userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCurrentUser(UserUpdateDTO dto) {
        Long userId = SecurityUtils.getCurrentUserId();
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException(ResultCodeEnum.USER_NOT_FOUND);
        }

        userConverter.updateUserFromDTO(dto, user);
        userMapper.updateById(user);
        
        log.info("用户信息更新成功，userId: {}", userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void followUser(Long followeeId) {
        Long followerId = SecurityUtils.getCurrentUserId();
        
        if (followerId.equals(followeeId)) {
            throw new BusinessException(ResultCodeEnum.CANNOT_FOLLOW_SELF);
        }

        LambdaQueryWrapper<UserFollow> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserFollow::getUserId, followerId)
               .eq(UserFollow::getFollowUserId, followeeId);
        UserFollow exist = userFollowMapper.selectOne(wrapper);
        if (exist != null) {
            throw new BusinessException(ResultCodeEnum.ALREADY_FOLLOWED);
        }

        UserFollow userFollow = new UserFollow();
        userFollow.setUserId(followerId);
        userFollow.setFollowUserId(followeeId);
        userFollowMapper.insert(userFollow);
        
        log.info("用户关注成功，followerId: {}, followeeId: {}", followerId, followeeId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void unfollowUser(Long followeeId) {
        Long followerId = SecurityUtils.getCurrentUserId();

        LambdaQueryWrapper<UserFollow> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserFollow::getUserId, followerId)
               .eq(UserFollow::getFollowUserId, followeeId);
        userFollowMapper.delete(wrapper);
        
        log.info("用户取消关注成功，followerId: {}, followeeId: {}", followerId, followeeId);
    }

    @Override
    public Page<UserVO> pageUsers(int page, int size) {
        Page<User> userPage = new Page<>(page, size);
        Page<User> result = userMapper.selectPage(userPage, null);
        
        return (Page<UserVO>) result.convert(userConverter::toVO);
    }

    @Override
    public boolean isFollowing(Long followerId, Long followeeId) {
        LambdaQueryWrapper<UserFollow> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserFollow::getUserId, followerId)
               .eq(UserFollow::getFollowUserId, followeeId);
        return userFollowMapper.selectCount(wrapper) > 0;
    }

    private LoginVO buildLoginVO(User user) {
        String token = jwtTokenProvider.createToken(user.getId());
        Long expireTime = jwtTokenProvider.getExpireTime();

        LoginVO loginVO = new LoginVO();
        loginVO.setUserInfo(userConverter.toVO(user));
        loginVO.setToken(token);
        loginVO.setExpireTime(expireTime);
        loginVO.setExpiresIn(expireTime / 1000);
        
        return loginVO;
    }
}
