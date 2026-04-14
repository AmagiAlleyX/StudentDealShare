package com.student.dealshare.common.result;

import lombok.Getter;

/**
 * 统一状态码枚举
 *
 * @author student-deal-share
 * @since 1.0.0
 */
@Getter
public enum ResultCodeEnum {

    /**
     * 成功
     */
    SUCCESS(200, "操作成功"),

    /**
     * 失败
     */
    FAIL(500, "操作失败"),

    /**
     * 参数验证失败
     */
    VALIDATE_ERROR(400, "参数验证失败"),

    /**
     * 未授权
     */
    UNAUTHORIZED(401, "未授权，请先登录"),

    /**
     * 禁止访问
     */
    FORBIDDEN(403, "禁止访问"),

    /**
     * 资源不存在
     */
    NOT_FOUND(404, "资源不存在"),

    /**
     * 方法不支持
     */
    METHOD_NOT_SUPPORTED(405, "方法不支持"),

    /**
     * 服务器内部错误
     */
    INTERNAL_SERVER_ERROR(500, "服务器内部错误"),

    // ==================== 用户相关错误码（1000-1999）====================

    /**
     * 用户名已存在
     */
    USERNAME_EXISTS(1001, "用户名已存在"),

    /**
     * 手机号已存在
     */
    PHONE_EXISTS(1002, "手机号已存在"),

    /**
     * 邮箱已存在
     */
    EMAIL_EXISTS(1003, "邮箱已存在"),

    /**
     * 用户名或密码错误
     */
    USERNAME_OR_PASSWORD_ERROR(1004, "用户名或密码错误"),

    /**
     * 用户已被禁用
     */
    USER_DISABLED(1005, "用户已被禁用"),

    /**
     * 验证码错误
     */
    CODE_ERROR(1006, "验证码错误"),

    /**
     * 验证码已过期
     */
    CODE_EXPIRED(1007, "验证码已过期"),

    /**
     * 旧密码错误
     */
    OLD_PASSWORD_ERROR(1008, "旧密码错误"),

    /**
     * 用户未登录
     */
    USER_NOT_LOGIN(1009, "用户未登录"),

    // ==================== 优惠相关错误码（2000-2999）====================

    /**
     * 优惠信息不存在
     */
    DEAL_NOT_FOUND(2001, "优惠信息不存在"),

    /**
     * 优惠已下架
     */
    DEAL_OFF_SHELF(2002, "优惠已下架"),

    /**
     * 优惠活动未开始
     */
    DEAL_NOT_START(2003, "优惠活动未开始"),

    /**
     * 优惠活动已结束
     */
    DEAL_END(2004, "优惠活动已结束"),

    /**
     * 分类不存在
     */
    CATEGORY_NOT_FOUND(2005, "分类不存在"),

    // ==================== 社区相关错误码（3000-3999）====================

    /**
     * 帖子不存在
     */
    POST_NOT_FOUND(3001, "帖子不存在"),

    /**
     * 评论不存在
     */
    COMMENT_NOT_FOUND(3002, "评论不存在"),

    /**
     * 话题不存在
     */
    TOPIC_NOT_FOUND(3003, "话题不存在"),

    /**
     * 帖子已被删除
     */
    POST_DELETED(3004, "帖子已被删除"),

    // ==================== 消息相关错误码（4000-4999）====================

    /**
     * 消息不存在
     */
    MESSAGE_NOT_FOUND(4001, "消息不存在"),

    // ==================== 通用业务错误码（5000-5999）====================

    /**
     * 数据不存在
     */
    DATA_NOT_FOUND(5001, "数据不存在"),

    /**
     * 数据已存在
     */
    DATA_EXISTS(5002, "数据已存在"),

    /**
     * 操作被拒绝
     */
    OPERATION_DENIED(5003, "操作被拒绝"),

    /**
     * 无权限操作
     */
    NO_PERMISSION(5004, "无权限操作"),

    /**
     * 超过限制
     */
    EXCEED_LIMIT(5005, "超过限制");

    /**
     * 状态码
     */
    private final Integer code;

    /**
     * 消息
     */
    private final String message;

    ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
