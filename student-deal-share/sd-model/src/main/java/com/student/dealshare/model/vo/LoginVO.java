package com.student.dealshare.model.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class LoginVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private UserVO userInfo;

    private String token;

    private Long expireTime;

    private Long expiresIn;
}
