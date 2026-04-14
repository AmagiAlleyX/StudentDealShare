package com.student.dealshare.common.util;

import cn.hutool.core.util.StrUtil;

/**
 * 字符串工具类
 *
 * @author student-deal-share
 * @since 1.0.0
 */
public class StringUtils extends StrUtil {

    private StringUtils() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 判断字符串是否为空
     *
     * @param str 字符串
     * @return 是否为空
     */
    public static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    /**
     * 判断字符串是否不为空
     *
     * @param str 字符串
     * @return 是否不为空
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 判断字符串是否为空白
     *
     * @param str 字符串
     * @return 是否为空白
     */
    public static boolean isBlank(String str) {
        return str == null || str.trim().length() == 0;
    }

    /**
     * 判断字符串是否不为空白
     *
     * @param str 字符串
     * @return 是否不为空白
     */
    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    /**
     * 去除首尾空白
     *
     * @param str 字符串
     * @return 处理后的字符串
     */
    public static String trim(String str) {
        return str == null ? null : str.trim();
    }

    /**
     * 转小写
     *
     * @param str 字符串
     * @return 小写字符串
     */
    public static String toLowerCase(String str) {
        return str == null ? null : str.toLowerCase();
    }

    /**
     * 转大写
     *
     * @param str 字符串
     * @return 大写字符串
     */
    public static String toUpperCase(String str) {
        return str == null ? null : str.toUpperCase();
    }

    /**
     * 判断字符串是否等于指定值（忽略大小写）
     *
     * @param str1   字符串 1
     * @param str2   字符串 2
     * @param ignoreCase 是否忽略大小写
     * @return 是否相等
     */
    public static boolean equals(String str1, String str2, boolean ignoreCase) {
        if (str1 == null || str2 == null) {
            return false;
        }
        return ignoreCase ? str1.equalsIgnoreCase(str2) : str1.equals(str2);
    }
}
