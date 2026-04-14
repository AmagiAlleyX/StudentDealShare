package com.student.dealshare.model.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class PrivateMessageVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long messageId;

    private Long senderId;

    private String senderName;

    private String senderAvatar;

    private Long receiverId;

    private String receiverName;

    private String receiverAvatar;

    private String content;

    private Integer isRead;

    private LocalDateTime createTime;
}
