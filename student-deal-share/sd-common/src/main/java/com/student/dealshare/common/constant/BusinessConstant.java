package com.student.dealshare.common.constant;

/**
 * 业务常量
 *
 * @author student-deal-share
 * @since 1.0.0
 */
public class BusinessConstant {

    private BusinessConstant() {
        throw new IllegalStateException("Constant class");
    }

    // ==================== 优惠类型 ====================

    /**
     * 优惠类型：预售
     */
    public static final Integer DEAL_TYPE_PRE_SALE = 1;

    /**
     * 优惠类型：折扣
     */
    public static final Integer DEAL_TYPE_DISCOUNT = 2;

    /**
     * 优惠类型：优惠券
     */
    public static final Integer DEAL_TYPE_COUPON = 3;

    /**
     * 优惠类型：限时抢购
     */
    public static final Integer DEAL_TYPE_FLASH_SALE = 4;

    /**
     * 优惠类型：其他
     */
    public static final Integer DEAL_TYPE_OTHER = 5;

    // ==================== 目标类型 ====================

    /**
     * 目标类型：优惠
     */
    public static final Integer TARGET_TYPE_DEAL = 1;

    /**
     * 目标类型：帖子
     */
    public static final Integer TARGET_TYPE_POST = 2;

    /**
     * 目标类型：评论
     */
    public static final Integer TARGET_TYPE_COMMENT = 3;

    // ==================== 消息类型 ====================

    /**
     * 消息类型：系统
     */
    public static final Integer MESSAGE_TYPE_SYSTEM = 1;

    /**
     * 消息类型：点赞
     */
    public static final Integer MESSAGE_TYPE_LIKE = 2;

    /**
     * 消息类型：评论
     */
    public static final Integer MESSAGE_TYPE_COMMENT = 3;

    /**
     * 消息类型：回复
     */
    public static final Integer MESSAGE_TYPE_REPLY = 4;

    /**
     * 消息类型：关注
     */
    public static final Integer MESSAGE_TYPE_FOLLOW = 5;

    /**
     * 消息类型：私信
     */
    public static final Integer MESSAGE_TYPE_PRIVATE = 6;

    // ==================== 帖子类型 ====================

    /**
     * 帖子类型：图文
     */
    public static final Integer POST_TYPE_IMAGE = 1;

    /**
     * 帖子类型：视频
     */
    public static final Integer POST_TYPE_VIDEO = 2;

    // ==================== 管理员角色 ====================

    /**
     * 管理员角色：超级管理员
     */
    public static final Integer ADMIN_ROLE_SUPER = 1;

    /**
     * 管理员角色：普通管理员
     */
    public static final Integer ADMIN_ROLE_NORMAL = 2;

    // ==================== 标签类型 ====================

    /**
     * 标签类型：系统
     */
    public static final Integer TAG_TYPE_SYSTEM = 1;

    /**
     * 标签类型：用户
     */
    public static final Integer TAG_TYPE_USER = 2;

    // ==================== 敏感词类型 ====================

    /**
     * 敏感词类型：政治
     */
    public static final Integer SENSITIVE_TYPE_POLITICS = 1;

    /**
     * 敏感词类型：色情
     */
    public static final Integer SENSITIVE_TYPE_PORNOGRAPHY = 2;

    /**
     * 敏感词类型：暴力
     */
    public static final Integer SENSITIVE_TYPE_VIOLENCE = 3;

    /**
     * 敏感词类型：广告
     */
    public static final Integer SENSITIVE_TYPE_AD = 4;

    /**
     * 敏感词类型：其他
     */
    public static final Integer SENSITIVE_TYPE_OTHER = 5;
}
