package com.campusecohub.entity;

import lombok.Data;

import java.util.Date;

/**
 * 学校实体类
 */
@Data
public class School {
    /**
     * 学校ID
     */
    private Long id;

    /**
     * 学校名称
     */
    private String name;

    /**
     * 地区
     */
    private String region;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
