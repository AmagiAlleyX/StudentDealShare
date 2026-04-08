package com.campusecohub.dto;

import lombok.Data;

import java.util.List;

/**
 * 活动列表响应DTO
 */
@Data
public class ActivityListDTO {
    /**
     * 活动列表
     */
    private List<ActivityInfoDTO> activities;

    /**
     * 总记录数
     */
    private Long total;

    /**
     * 页码
     */
    private Integer pageNum;

    /**
     * 每页数量
     */
    private Integer pageSize;

    /**
     * 总页数
     */
    private Integer pages;
}
