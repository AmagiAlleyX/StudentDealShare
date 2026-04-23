package com.student.dealshare.model.dto;

import lombok.Data;

@Data
public class DealQueryDTO {

    private Long categoryId;

    private Integer type;

    private String keyword;

    private Integer status;

    private Integer page = 1;

    private Integer size = 10;

    private String sortBy = "createTime";

    private String sortOrder = "desc";
}
