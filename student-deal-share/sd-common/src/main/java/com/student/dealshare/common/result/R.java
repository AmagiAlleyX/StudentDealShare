package com.student.dealshare.common.result;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 统一响应结果类
 *
 * @param <T> 数据类型
 * @author student-deal-share
 * @since 1.0.0
 */
@Data
@Schema(description = "统一响应结果")
public class R<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "状态码")
    private Integer code;

    @Schema(description = "响应消息")
    private String message;

    @Schema(description = "响应数据")
    private T data;

    @Schema(description = "时间戳")
    private Long timestamp;

    /**
     * 私有构造函数，使用静态方法创建
     */
    private R() {
        this.timestamp = System.currentTimeMillis();
    }

    /**
     * 成功响应（200）
     */
    public static <T> R<T> ok() {
        R<T> r = new R<>();
        r.setCode(ResultCodeEnum.SUCCESS.getCode());
        r.setMessage(ResultCodeEnum.SUCCESS.getMessage());
        return r;
    }

    /**
     * 成功响应（200）
     *
     * @param data 数据
     */
    public static <T> R<T> ok(T data) {
        R<T> r = new R<>();
        r.setCode(ResultCodeEnum.SUCCESS.getCode());
        r.setMessage(ResultCodeEnum.SUCCESS.getMessage());
        r.setData(data);
        return r;
    }

    /**
     * 成功响应（200）- 自定义消息
     *
     * @param message 消息
     * @param data    数据
     */
    public static <T> R<T> ok(String message, T data) {
        R<T> r = new R<>();
        r.setCode(ResultCodeEnum.SUCCESS.getCode());
        r.setMessage(message);
        r.setData(data);
        return r;
    }

    /**
     * 失败响应
     *
     * @param resultCodeEnum 错误码枚举
     */
    public static <T> R<T> fail(ResultCodeEnum resultCodeEnum) {
        R<T> r = new R<>();
        r.setCode(resultCodeEnum.getCode());
        r.setMessage(resultCodeEnum.getMessage());
        return r;
    }

    /**
     * 失败响应
     *
     * @param message 消息
     */
    public static <T> R<T> fail(String message) {
        R<T> r = new R<>();
        r.setCode(ResultCodeEnum.FAIL.getCode());
        r.setMessage(message);
        return r;
    }

    /**
     * 失败响应
     *
     * @param code    状态码
     * @param message 消息
     */
    public static <T> R<T> fail(Integer code, String message) {
        R<T> r = new R<>();
        r.setCode(code);
        r.setMessage(message);
        return r;
    }

    /**
     * 判断是否成功
     *
     * @return 是否成功
     */
    public boolean isSuccess() {
        return ResultCodeEnum.SUCCESS.getCode().equals(this.code);
    }
}
