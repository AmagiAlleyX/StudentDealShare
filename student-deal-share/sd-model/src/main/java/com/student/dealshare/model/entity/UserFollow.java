package com.student.dealshare.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("user_follow")
public class UserFollow implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "follow_id", type = IdType.ASSIGN_ID)
    private Long followId;

    private Long followerId;

    private Long followeeId;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableLogic
    private Integer deleted;
}
