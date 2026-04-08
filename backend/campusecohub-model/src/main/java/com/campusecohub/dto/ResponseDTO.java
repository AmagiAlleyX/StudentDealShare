package com.campusecohub.dto;

import lombok.Data;

/**
 * 通用响应DTO
 */
@Data
public class ResponseDTO<T> {
    /**
     * 响应码
     */
    private Integer code;

    /**
     * 响应消息
     */
    private String message;

    /**
     * 响应数据
     */
    private T data;

    /**
     * 成功响应
     */
    public static <T> ResponseDTO<T> success(T data) {
        ResponseDTO<T> response = new ResponseDTO<>();
        response.setCode(200);
        response.setMessage("成功");
        response.setData(data);
        return response;
    }

    /**
     * 成功响应（无数据）
     */
    public static <T> ResponseDTO<T> success() {
        return success(null);
    }

    /**
     * 失败响应
     */
    public static <T> ResponseDTO<T> fail(Integer code, String message) {
        ResponseDTO<T> response = new ResponseDTO<>();
        response.setCode(code);
        response.setMessage(message);
        return response;
    }

    /**
     * 失败响应（默认错误码）
     */
    public static <T> ResponseDTO<T> fail(String message) {
        return fail(500, message);
    }
}
