package com.campusecohub.dto;

import lombok.Data;

import java.util.List;

/**
 * 帖子列表响应DTO
 */
@Data
public class PostListDTO {
    /**
     * 帖子列表
     */
    private List<PostInfoDTO> posts;

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
