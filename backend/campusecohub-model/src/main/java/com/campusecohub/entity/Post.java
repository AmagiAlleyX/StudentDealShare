package com.campusecohub.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 帖子实体类
 */
@Data
public class Post {
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
     * 点赞数
     */
    private Integer likeCount;

    /**
     * 评论数
     */
    private Integer commentCount;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 发布用户信息（关联查询）
     */
    private User user;

    /**
     * 是否已点赞（关联查询）
     */
    private Boolean isLiked;

    /**
     * 评论列表（关联查询）
     */
    private List<Comment> comments;
}
