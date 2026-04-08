package com.campusecohub.dto;

import lombok.Data;

/**
 * 帖子创建请求DTO
 */
@Data
public class PostCreateDTO {
    /**
     * 帖子标题
     */
    private String title;

    /**
     * 帖子内容
     */
    private String content;

    /**
     * 图片链接（多个用逗号分隔）
     */
    private String images;

    /**
     * 相关链接
     */
    private String link;
}
