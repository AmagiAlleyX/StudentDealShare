package com.student.dealshare.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("user_favorite")
public class UserFavorite implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "favorite_id", type = IdType.ASSIGN_ID)
    private Long favoriteId;

    private Long userId;

    private Long targetType;

    private Long targetId;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableLogic
    private Integer deleted;
}
