package com.student.dealshare.model.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class SystemConfigVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long configId;

    private String configKey;

    private String configValue;

    private String description;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
