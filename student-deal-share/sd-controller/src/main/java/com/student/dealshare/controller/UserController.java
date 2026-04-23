package com.student.dealshare.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.student.dealshare.annotation.OperationLog;
import com.student.dealshare.common.result.R;
import com.student.dealshare.model.dto.UserLoginDTO;
import com.student.dealshare.model.dto.UserRegisterDTO;
import com.student.dealshare.model.dto.UserUpdateDTO;
import com.student.dealshare.model.vo.LoginVO;
import com.student.dealshare.model.vo.UserVO;
import com.student.dealshare.security.SecurityUtils;
import com.student.dealshare.service.api.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 用户控制器
 */
@Tag(name = "用户管理")
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "用户注册")
    @PostMapping("/register")
    @OperationLog(module = "用户管理", type = "CREATE", description = "用户注册")
    public R<LoginVO> register(@Valid @RequestBody UserRegisterDTO dto) {
        LoginVO result = userService.register(dto);
        return R.ok(result);
    }

    @Operation(summary = "用户登录")
    @PostMapping("/login")
    @OperationLog(module = "用户管理", type = "LOGIN", description = "用户登录")
    public R<LoginVO> login(@Valid @RequestBody UserLoginDTO dto) {
        LoginVO result = userService.login(dto);
        return R.ok(result);
    }

    @Operation(summary = "获取当前用户信息")
    @GetMapping("/me")
    public R<UserVO> getCurrentUser() {
        UserVO user = userService.getCurrentUser();
        return R.ok(user);
    }

    @Operation(summary = "获取用户详情")
    @GetMapping("/{id}")
    public R<UserVO> getUserById(@PathVariable Long id) {
        UserVO user = userService.getUserById(id);
        return R.ok(user);
    }

    @Operation(summary = "更新用户信息")
    @PutMapping("/update")
    @OperationLog(module = "用户管理", type = "UPDATE", description = "更新用户信息")
    public R<Void> updateCurrentUser(@Valid @RequestBody UserUpdateDTO dto) {
        userService.updateCurrentUser(dto);
        return R.ok();
    }

    @Operation(summary = "分页查询用户列表")
    @GetMapping("/page")
    public R<Page<UserVO>> pageUsers(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<UserVO> userPage = userService.pageUsers(page, size);
        return R.ok(userPage);
    }

    @Operation(summary = "关注用户")
    @PostMapping("/follow/{followeeId}")
    @OperationLog(module = "用户管理", type = "FOLLOW", description = "关注用户")
    public R<Void> followUser(@PathVariable Long followeeId) {
        userService.followUser(followeeId);
        return R.ok();
    }

    @Operation(summary = "取消关注用户")
    @DeleteMapping("/follow/{followeeId}")
    @OperationLog(module = "用户管理", type = "UNFOLLOW", description = "取消关注")
    public R<Void> unfollowUser(@PathVariable Long followeeId) {
        userService.unfollowUser(followeeId);
        return R.ok();
    }

    @Operation(summary = "检查是否已关注")
    @GetMapping("/follow/check/{followeeId}")
    public R<Boolean> isFollowing(@PathVariable Long followeeId) {
        boolean following = userService.isFollowing(SecurityUtils.getCurrentUserId(), followeeId);
        return R.ok(following);
    }
}
