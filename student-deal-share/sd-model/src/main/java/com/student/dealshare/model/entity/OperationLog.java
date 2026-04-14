package com.student.dealshare.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("operation_log")
public class OperationLog implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "log_id", type = IdType.ASSIGN_ID)
    private Long logId;

    private Long operatorId;

    private String operatorType;

    private String operationModule;

    private String operationType;

    private String operationDesc;

    private String requestMethod;

    private String requestUrl;

    private String requestParams;

    private String ipAddress;

    private String userAgent;

    private Long executeTime;

    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableLogic
    private Integer deleted;
}
