package com.campusecohub.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 帖子信息响应DTO
 */
@Data
public class PostInfoDTO {
    /**
     * 帖子ID
     */
    private Long id;

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

    /**
     * 发布用户ID
     */
    private Long userId;

    /**
     * 发布用户名
     */
    private String username;

    /**
     * 点赞数
     */
    private Integer likeCount;

    /**
     * 评论数
     */
    private Integer commentCount;

    /**
     * 是否已点赞
     */
    private Boolean isLiked;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 评论列表
     */
    private List<CommentInfoDTO> comments;
}
