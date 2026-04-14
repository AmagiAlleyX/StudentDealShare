package com.student.dealshare.common.util;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;

/**
 * 验证码工具类
 *
 * @author student-deal-share
 * @since 1.0.0
 */
public class CodeUtil {

    private CodeUtil() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 默认验证码长度
     */
    private static final int DEFAULT_CODE_LENGTH = 6;

    /**
     * 生成数字验证码
     *
     * @return 数字验证码
     */
    public static String generateNumericCode() {
        return generateNumericCode(DEFAULT_CODE_LENGTH);
    }

    /**
     * 生成数字验证码
     *
     * @param length 长度
     * @return 数字验证码
     */
    public static String generateNumericCode(int length) {
        return RandomUtil.randomNumbers(length);
    }

    /**
     * 生成字母和数字混合验证码
     *
     * @return 混合验证码
     */
    public static String generateMixedCode() {
        return generateMixedCode(DEFAULT_CODE_LENGTH);
    }

    /**
     * 生成字母和数字混合验证码
     *
     * @param length 长度
     * @return 混合验证码
     */
    public static String generateMixedCode(int length) {
        return RandomUtil.randomString(RandomUtil.BASE_CHAR_NUMBER, length);
    }

    /**
     * 生成图形验证码
     *
     * @return 图形验证码
     */
    public static String generateImageCode() {
        return generateImageCode(4);
    }

    /**
     * 生成图形验证码
     *
     * @param length 长度
     * @return 图形验证码
     */
    public static String generateImageCode(int length) {
        return RandomUtil.randomString(RandomUtil.BASE_CHAR_NUMBER, length).toUpperCase();
    }

    /**
     * 验证验证码是否正确
     *
     * @param inputCode 输入的验证码
     * @param rightCode 正确的验证码
     * @param ignoreCase 是否忽略大小写
     * @return 是否正确
     */
    public static boolean validateCode(String inputCode, String rightCode, boolean ignoreCase) {
        if (StrUtil.isBlank(inputCode) || StrUtil.isBlank(rightCode)) {
            return false;
        }
        return ignoreCase ? inputCode.equalsIgnoreCase(rightCode) : inputCode.equals(rightCode);
    }

    /**
     * 验证验证码是否正确（默认忽略大小写）
     *
     * @param inputCode 输入的验证码
     * @param rightCode 正确的验证码
     * @return 是否正确
     */
    public static boolean validateCode(String inputCode, String rightCode) {
        return validateCode(inputCode, rightCode, true);
    }
}
