package com.student.dealshare.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("category")
public class Category implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "category_id", type = IdType.ASSIGN_ID)
    private Long categoryId;

    private String name;

    private Long parentId;

    private Integer level;

    private String icon;

    private Integer sortOrder;

    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableLogic
    private Integer deleted;
}
