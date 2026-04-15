package com.student.dealshare.model.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 私信视图对象
 */
@Data
public class PrivateMessageVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long messageId;

    private Long senderId;

    private Long receiverId;

    private String content;

    private Integer isRead;

    private LocalDateTime createTime;
}
