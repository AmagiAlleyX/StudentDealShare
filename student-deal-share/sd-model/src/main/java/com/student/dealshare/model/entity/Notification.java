package com.student.dealshare.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("notification")
public class Notification implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "notification_id", type = IdType.ASSIGN_ID)
    private Long notificationId;

    private Long userId;

    private Integer type;

    private String title;

    private String content;

    private Long relatedId;

    private Integer isRead;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableLogic
    private Integer deleted;
}
