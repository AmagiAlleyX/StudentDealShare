package com.campusecohub.dto;

import lombok.Data;

/**
 * 评论创建请求DTO
 */
@Data
public class CommentCreateDTO {
    /**
     * 帖子ID
     */
    private Long postId;

    /**
     * 评论内容
     */
    private String content;
}
