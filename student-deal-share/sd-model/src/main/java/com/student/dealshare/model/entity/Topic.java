package com.student.dealshare.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("topic")
public class Topic implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "topic_id", type = IdType.ASSIGN_ID)
    private Long topicId;

    private String name;

    private String description;

    private String coverImage;

    private Long postCount;

    private Long followCount;

    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableLogic
    private Integer deleted;
}
