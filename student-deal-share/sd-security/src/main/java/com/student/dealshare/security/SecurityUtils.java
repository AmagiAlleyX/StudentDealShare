package com.student.dealshare.security;

import com.student.dealshare.common.exception.BusinessException;
import com.student.dealshare.common.result.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * 安全工具类
 */
@Slf4j
@Component
public class SecurityUtils {

    private static boolean devMode;
    private static Long devUserId;

    @Value("${app.dev-mode:true}")
    public void setDevMode(boolean devMode) {
        SecurityUtils.devMode = devMode;
    }
    
    @Value("${app.dev-user-id:1}")
    public void setDevUserId(Long devUserId) {
        SecurityUtils.devUserId = devUserId;
    }

    /**
     * 获取当前登录用户 ID
     */
    public static Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        // 开发模式：如果没有认证，返回默认测试用户
        if (devMode && (authentication == null || !authentication.isAuthenticated())) {
            log.debug("开发模式：使用默认测试用户 ID: {}", devUserId);
            return devUserId;
        }
        
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new BusinessException(ResultCodeEnum.UNAUTHORIZED);
        }
        
        Object principal = authentication.getPrincipal();
        if (principal instanceof Long) {
            return (Long) principal;
        }
        
        throw new BusinessException(ResultCodeEnum.UNAUTHORIZED);
    }

    /**
     * 检查是否已登录
     */
    public static boolean isLoggedIn() {
        if (devMode) {
            return true;
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated();
    }
}
