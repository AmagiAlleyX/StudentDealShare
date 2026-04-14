package com.student.dealshare.model.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
public class SensitiveWordDTO {

    @NotBlank(message = "敏感词不能为空")
    @Size(max = 50, message = "敏感词最多 50 字")
    private String word;

    private Integer type;
}
