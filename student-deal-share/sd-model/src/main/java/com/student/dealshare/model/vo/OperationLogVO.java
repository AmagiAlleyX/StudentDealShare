package com.student.dealshare.model.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class OperationLogVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

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

    private LocalDateTime createTime;
}
