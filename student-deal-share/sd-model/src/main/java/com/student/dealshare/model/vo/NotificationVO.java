package com.student.dealshare.model.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class NotificationVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    private Long userId;

    private Integer type;

    private String title;

    private String content;

    private Integer targetType;

    private Long targetId;

    private Long fromUserId;

    private String fromUserName;

    private String fromUserAvatar;

    private Integer isRead;

    private LocalDateTime createdAt;
}
