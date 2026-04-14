package com.student.dealshare.model.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class UserVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long userId;

    private String username;

    private String nickname;

    private String avatar;

    private Integer gender;

    private String phone;

    private String email;

    private String school;

    private Integer studentVerified;

    private Integer status;

    private LocalDateTime createTime;
}
