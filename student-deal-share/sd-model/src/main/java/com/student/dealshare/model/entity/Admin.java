package com.student.dealshare.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("admin")
public class Admin implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "admin_id", type = IdType.ASSIGN_ID)
    private Long adminId;

    private String username;

    private String password;

    private String nickname;

    private String avatar;

    private String phone;

    private String email;

    private Integer role;

    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableLogic
    private Integer deleted;
}
