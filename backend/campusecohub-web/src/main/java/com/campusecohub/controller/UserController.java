package com.campusecohub.controller;
import com.campusecohub.dto.UserLoginDTO;
import com.campusecohub.dto.UserRegisterDTO;
import com.campusecohub.dto.UserInfoDTO;
import com.campusecohub.dto.StudentAuthDTO;
import com.campusecohub.dto.ResponseDTO;
import com.campusecohub.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.*;


/**
 * 用户控制器
 */
@RestController
@RequestMapping("/api/user")
@Api(tags = "用户管理")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 用户注册
     */
    @PostMapping("/register")
    @ApiOperation("用户注册")
    public ResponseDTO<UserInfoDTO> register(@RequestBody UserRegisterDTO userRegisterDTO) {
        UserInfoDTO userInfoDTO = userService.register(userRegisterDTO);
        return ResponseDTO.success(userInfoDTO);
    }

    /**
     * 用户登录
     */
    @PostMapping("/login")
    @ApiOperation("用户登录")
    public ResponseDTO<UserInfoDTO> login(@RequestBody UserLoginDTO userLoginDTO) {
        UserInfoDTO userInfoDTO = userService.login(userLoginDTO);
        return ResponseDTO.success(userInfoDTO);
    }

    /**
     * 获取用户信息
     */
    @GetMapping("/info")
    @ApiOperation("获取用户信息")
    public ResponseDTO<UserInfoDTO> getInfo(@RequestParam Long userId) {
        UserInfoDTO userInfoDTO = userService.selectById(userId);
        return ResponseDTO.success(userInfoDTO);
    }

    /**
     * 学生认证
     */
    @PostMapping("/student/auth")
    @ApiOperation("学生认证")
    public ResponseDTO<Boolean> studentAuth(@RequestParam Long userId, @RequestBody StudentAuthDTO studentAuthDTO) {
        boolean result = userService.studentAuth(userId, studentAuthDTO);
        return ResponseDTO.success(result);
    }

    /**
     * 微信登录
     */
    @PostMapping("/wechat/login")
    @ApiOperation("微信登录")
    public ResponseDTO<UserInfoDTO> wechatLogin(@RequestParam String code) {
        UserInfoDTO userInfoDTO = userService.wechatLogin(code);
        return ResponseDTO.success(userInfoDTO);
    }
}
