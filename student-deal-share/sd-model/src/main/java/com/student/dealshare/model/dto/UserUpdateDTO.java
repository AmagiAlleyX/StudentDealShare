package com.student.dealshare.model.dto;

import lombok.Data;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Data
public class UserUpdateDTO {

    @Size(max = 20, message = "昵称最多 20 位")
    private String nickname;

    private String avatar;

    private Integer gender;

    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String phone;

    @Email(message = "邮箱格式不正确")
    private String email;

    private String school;
}
