package com.campusecohub.dto;

import lombok.Data;

/**
 * 活动查询请求DTO
 */
@Data
public class ActivityQueryDTO {
    /**
     * 活动分类
     */
    private String category;

    /**
     * 活动来源
     */
    private String source;

    /**
     * 学校ID
     */
    private Long schoolId;

    /**
     * 关键词
     */
    private String keyword;

    /**
     * 是否学生专属
     */
    private Integer isStudentExclusive;

    /**
     * 页码
     */
    private Integer pageNum;

    /**
     * 每页数量
     */
    private Integer pageSize;
}
