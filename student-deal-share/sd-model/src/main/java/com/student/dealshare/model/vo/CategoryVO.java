package com.student.dealshare.model.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class CategoryVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long categoryId;

    private String name;

    private Long parentId;

    private Integer level;

    private String icon;

    private Integer sortOrder;
}
