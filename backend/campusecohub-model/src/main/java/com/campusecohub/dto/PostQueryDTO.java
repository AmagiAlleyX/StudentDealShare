package com.campusecohub.dto;

import lombok.Data;

/**
 * 帖子查询请求DTO
 */
@Data
public class PostQueryDTO {
    /**
     * 关键词
     */
    private String keyword;

    /**
     * 页码
     */
    private Integer pageNum;

    /**
     * 每页数量
     */
    private Integer pageSize;
}
