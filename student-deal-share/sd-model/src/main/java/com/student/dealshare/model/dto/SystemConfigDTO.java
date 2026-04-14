package com.student.dealshare.model.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
public class SystemConfigDTO {

    @NotBlank(message = "配置键不能为空")
    private String configKey;

    @NotBlank(message = "配置值不能为空")
    private String configValue;

    private String description;
}
