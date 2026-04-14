package com.student.dealshare.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("system_config")
public class SystemConfig implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "config_id", type = IdType.ASSIGN_ID)
    private Long configId;

    private String configKey;

    private String configValue;

    private String description;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableLogic
    private Integer deleted;
}
