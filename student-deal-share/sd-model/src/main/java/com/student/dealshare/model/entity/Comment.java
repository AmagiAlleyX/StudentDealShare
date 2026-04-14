package com.student.dealshare.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("comment")
public class Comment implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "comment_id", type = IdType.ASSIGN_ID)
    private Long commentId;

    private Long postId;

    private Long userId;

    private Long parentId;

    private Integer level;

    private String content;

    private String[] images;

    private Integer status;

    private Long likeCount;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableLogic
    private Integer deleted;
}
