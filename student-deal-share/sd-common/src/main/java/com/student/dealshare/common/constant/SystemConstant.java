package com.student.dealshare.common.constant;

/**
 * 系统常量
 *
 * @author student-deal-share
 * @since 1.0.0
 */
public class SystemConstant {

    private SystemConstant() {
        throw new IllegalStateException("Constant class");
    }

    /**
     * 默认分页大小
     */
    public static final Integer DEFAULT_PAGE_SIZE = 10;

    /**
     * 最大分页大小
     */
    public static final Integer MAX_PAGE_SIZE = 100;

    /**
     * 默认页码
     */
    public static final Integer DEFAULT_PAGE = 1;

    /**
     * 删除标识：未删除
     */
    public static final Integer DELETED_NO = 0;

    /**
     * 删除标识：已删除
     */
    public static final Integer DELETED_YES = 1;

    /**
     * 状态：禁用
     */
    public static final Integer STATUS_DISABLE = 0;

    /**
     * 状态：正常
     */
    public static final Integer STATUS_NORMAL = 1;

    /**
     * 状态：审核中
     */
    public static final Integer STATUS_AUDITING = 2;

    /**
     * 是
     */
    public static final Integer YES = 1;

    /**
     * 否
     */
    public static final Integer NO = 0;

    /**
     * JWT 前缀
     */
    public static final String JWT_PREFIX = "Bearer ";

    /**
     * JWT 请求头
     */
    public static final String JWT_HEADER = "Authorization";

    /**
     * Redis Key 前缀
     */
    public static class RedisKey {
        /**
         * 用户 Token
         */
        public static final String USER_TOKEN = "user:token:";

        /**
         * 短信验证码
         */
        public static final String SMS_CODE = "sms:code:";

        /**
         * 用户信息
         */
        public static final String USER_INFO = "user:info:";

        /**
         * 热门搜索
         */
        public static final String HOT_SEARCH = "search:hot:";

        /**
         * 搜索历史
         */
        public static final String SEARCH_HISTORY = "search:history:";
    }

    /**
     * 缓存过期时间（秒）
     */
    public static class CacheExpire {
        /**
         * 短信验证码：5 分钟
         */
        public static final Long SMS_CODE = 300L;

        /**
         * 用户 Token：2 小时
         */
        public static final Long USER_TOKEN = 7200L;

        /**
         * 用户信息：30 分钟
         */
        public static final Long USER_INFO = 1800L;

        /**
         * 热门搜索：1 小时
         */
        public static final Long HOT_SEARCH = 3600L;

        /**
         * 搜索历史：1 天
         */
        public static final Long SEARCH_HISTORY = 86400L;
    }
}
