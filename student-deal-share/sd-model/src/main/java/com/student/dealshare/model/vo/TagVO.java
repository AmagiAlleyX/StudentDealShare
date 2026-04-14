package com.student.dealshare.model.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class TagVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long tagId;

    private String name;

    private String type;
}
