package com.student.dealshare.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("user")
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "user_id", type = IdType.ASSIGN_ID)
    private Long userId;

    private String username;

    private String password;

    private String nickname;

    private String avatar;

    private Integer gender;

    private String phone;

    private String email;

    private String school;

    private String studentId;

    private Integer studentVerified;

    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableLogic
    private Integer deleted;
}
