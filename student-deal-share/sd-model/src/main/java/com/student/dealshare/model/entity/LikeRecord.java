package com.student.dealshare.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("like_record")
public class LikeRecord implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "record_id", type = IdType.ASSIGN_ID)
    private Long recordId;

    private Long userId;

    private Long targetType;

    private Long targetId;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableLogic
    private Integer deleted;
}
