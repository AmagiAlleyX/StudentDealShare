package com.campusecohub.config;

import com.campusecohub.dto.ResponseDTO;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseDTO<?> handleRuntimeException(RuntimeException e) {
        return ResponseDTO.error(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseDTO<?> handleException(Exception e) {
        e.printStackTrace();
        return ResponseDTO.error("系统异常：" + e.getMessage());
    }
}
