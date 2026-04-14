package com.student.dealshare.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("private_message")
public class PrivateMessage implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "message_id", type = IdType.ASSIGN_ID)
    private Long messageId;

    private Long senderId;

    private Long receiverId;

    private String content;

    private Integer isRead;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableLogic
    private Integer deleted;
}
