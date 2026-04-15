package com.student.dealshare.model.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * 私信发送 DTO
 */
@Data
public class PrivateMessageSendDTO {

    @NotNull(message = "接收人 ID 不能为空")
    private Long receiverId;

    @NotBlank(message = "消息内容不能为空")
    @Size(max = 1000, message = "消息最多 1000 字")
    private String content;
}
