package com.student.dealshare.model.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class AdminVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long adminId;

    private String username;

    private String nickname;

    private String avatar;

    private String phone;

    private String email;

    private Integer role;

    private Integer status;

    private LocalDateTime createTime;
}
