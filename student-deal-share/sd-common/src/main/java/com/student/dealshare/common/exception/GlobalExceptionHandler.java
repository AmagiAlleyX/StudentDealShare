package com.student.dealshare.common.exception;

import com.student.dealshare.common.result.R;
import com.student.dealshare.common.result.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

/**
 * 全局异常处理器
 *
 * @author student-deal-share
 * @since 1.0.0
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理业务异常
     *
     * @param e 业务异常
     * @return 响应结果
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.OK)
    public R<Void> handleBusinessException(BusinessException e) {
        log.warn("业务异常：{}", e.getMessage());
        return R.fail(e.getCode(), e.getMessage());
    }

    /**
     * 处理参数验证异常（@Validated）
     *
     * @param e 参数验证异常
     * @return 响应结果
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R<Void> handleValidationException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining("; "));
        log.warn("参数验证异常：{}", message);
        return R.fail(ResultCodeEnum.VALIDATE_ERROR.getCode(), message);
    }

    /**
     * 处理绑定异常
     *
     * @param e 绑定异常
     * @return 响应结果
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R<Void> handleBindException(BindException e) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining("; "));
        log.warn("参数绑定异常：{}", message);
        return R.fail(ResultCodeEnum.VALIDATE_ERROR.getCode(), message);
    }

    /**
     * 处理 IllegalArgumentException
     *
     * @param e 非法参数异常
     * @return 响应结果
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R<Void> handleIllegalArgumentException(IllegalArgumentException e) {
        log.warn("非法参数异常：{}", e.getMessage());
        return R.fail(ResultCodeEnum.VALIDATE_ERROR.getCode(), e.getMessage());
    }

    /**
     * 处理 NullPointerException
     *
     * @param e 空指针异常
     * @return 响应结果
     */
    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public R<Void> handleNullPointerException(NullPointerException e) {
        log.error("空指针异常", e);
        return R.fail(ResultCodeEnum.INTERNAL_SERVER_ERROR);
    }

    /**
     * 处理其他未知异常
     *
     * @param e 异常
     * @return 响应结果
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public R<Void> handleException(Exception e) {
        log.error("系统异常：{}", e.getMessage(), e);
        return R.fail(ResultCodeEnum.INTERNAL_SERVER_ERROR);
    }
}
