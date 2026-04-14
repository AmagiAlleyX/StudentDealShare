package com.student.dealshare.model.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class NotificationVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long notificationId;

    private Long userId;

    private Integer type;

    private String typeName;

    private String title;

    private String content;

    private Long relatedId;

    private Integer isRead;

    private LocalDateTime createTime;
}
