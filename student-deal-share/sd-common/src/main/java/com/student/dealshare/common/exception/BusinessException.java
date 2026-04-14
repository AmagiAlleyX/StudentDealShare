package com.student.dealshare.common.exception;

import com.student.dealshare.common.result.ResultCodeEnum;
import lombok.Getter;

/**
 * 业务异常类
 *
 * @author student-deal-share
 * @since 1.0.0
 */
@Getter
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * 状态码
     */
    private final Integer code;

    /**
     * 构造业务异常
     *
     * @param message 异常消息
     */
    public BusinessException(String message) {
        super(message);
        this.code = ResultCodeEnum.FAIL.getCode();
    }

    /**
     * 构造业务异常
     *
     * @param code    状态码
     * @param message 异常消息
     */
    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    /**
     * 构造业务异常
     *
     * @param resultCodeEnum 错误码枚举
     */
    public BusinessException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
    }

    /**
     * 构造业务异常
     *
     * @param resultCodeEnum 错误码枚举
     * @param message        异常消息
     */
    public BusinessException(ResultCodeEnum resultCodeEnum, String message) {
        super(message);
        this.code = resultCodeEnum.getCode();
    }
}
