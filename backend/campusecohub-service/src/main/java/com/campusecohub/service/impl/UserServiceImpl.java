package com.campusecohub.service.impl;

import com.campusecohub.dto.UserLoginDTO;
import com.campusecohub.dto.UserRegisterDTO;
import com.campusecohub.dto.UserInfoDTO;
import com.campusecohub.dto.StudentAuthDTO;
import com.campusecohub.entity.User;
import com.campusecohub.mapper.UserMapper;
import com.campusecohub.service.UserService;
import com.campusecohub.service.SchoolService;
import cn.hutool.crypto.digest.DigestUtil;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;



/**
 * 用户服务实现类
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private SchoolService schoolService;

    @Override
    public UserInfoDTO register(UserRegisterDTO userRegisterDTO) {
        // 检查手机号是否已注册
        User existingUser = userMapper.selectByPhone(userRegisterDTO.getPhone());
        if (existingUser != null) {
            throw new RuntimeException("手机号已注册");
        }

        // 密码加密
        String encryptedPassword = DigestUtil.md5Hex(userRegisterDTO.getPassword());

        // 创建用户
        User user = new User();
        user.setUsername(userRegisterDTO.getUsername());
        user.setPassword(encryptedPassword);
        user.setPhone(userRegisterDTO.getPhone());
        user.setStudentStatus(0); // 未认证

        userMapper.insert(user);

        // 转换为DTO返回
        return convertToUserInfoDTO(user);
    }

    @Override
    public UserInfoDTO login(UserLoginDTO userLoginDTO) {
        // 根据手机号查询用户
        User user = userMapper.selectByPhone(userLoginDTO.getPhone());
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        // 验证密码
        String encryptedPassword = DigestUtil.md5Hex(userLoginDTO.getPassword());
        if (!encryptedPassword.equals(user.getPassword())) {
            throw new RuntimeException("密码错误");
        }

        // 转换为DTO返回
        return convertToUserInfoDTO(user);
    }

    @Override
    public UserInfoDTO selectById(Long id) {
        User user = userMapper.selectById(id);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        return convertToUserInfoDTO(user);
    }

    @Override
    public boolean studentAuth(Long userId, StudentAuthDTO studentAuthDTO) {
        // 检查学校是否存在
        if (schoolService.selectById(studentAuthDTO.getSchoolId()) == null) {
            throw new RuntimeException("学校不存在");
        }

        // 更新学生认证状态
        int result = userMapper.updateStudentStatus(userId, 1, studentAuthDTO.getSchoolId());
        return result > 0;
    }

    @Override
    public UserInfoDTO wechatLogin(String code) {
        // 这里需要调用微信API获取openid，暂时模拟
        String openid = "mock_openid_" + code;

        // 根据openid查询用户
        User user = userMapper.selectByWechatOpenid(openid);
        if (user == null) {
            // 新用户，创建账号
            user = new User();
            user.setUsername("微信用户" + openid.substring(0, 8));
            user.setPassword(DigestUtil.md5Hex(openid)); // 随机密码
            user.setWechatOpenid(openid);
            user.setStudentStatus(0);
            userMapper.insert(user);
        }

        return convertToUserInfoDTO(user);
    }

    /**
     * 将User实体转换为UserInfoDTO
     */
    private UserInfoDTO convertToUserInfoDTO(User user) {
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setId(user.getId());
        userInfoDTO.setUsername(user.getUsername());
        userInfoDTO.setPhone(user.getPhone());
        userInfoDTO.setStudentStatus(user.getStudentStatus());
        userInfoDTO.setSchoolId(user.getSchoolId());

        // 如果有学校ID，查询学校名称
        if (user.getSchoolId() != null) {
            try {
                userInfoDTO.setSchoolName(schoolService.selectById(user.getSchoolId()).getName());
            } catch (Exception e) {
                // 学校不存在，忽略
            }
        }

        return userInfoDTO;
    }
}
