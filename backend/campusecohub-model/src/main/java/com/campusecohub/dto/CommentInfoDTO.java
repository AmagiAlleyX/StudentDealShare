package com.campusecohub.dto;

import lombok.Data;

import java.util.Date;

/**
 * 评论信息响应DTO
 */
@Data
public class CommentInfoDTO {
    /**
     * 评论ID
     */
    private Long id;

    /**
     * 帖子ID
     */
    private Long postId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论时间
     */
    private Date createTime;
}
