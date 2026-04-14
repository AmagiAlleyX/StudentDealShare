package com.student.dealshare.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("sensitive_word")
public class SensitiveWord implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "word_id", type = IdType.ASSIGN_ID)
    private Long wordId;

    private String word;

    private Integer type;

    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableLogic
    private Integer deleted;
}
