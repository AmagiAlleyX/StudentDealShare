package com.student.dealshare.model.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class PrivateMessageVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    private Long senderId;

    private Long receiverId;

    private String senderName;

    private String senderAvatar;

    private String receiverName;

    private String receiverAvatar;

    private String content;

    private String imageUrls;

    private Integer isRead;

    private LocalDateTime createdAt;
}
