package com.campusecohub.entity;

import lombok.Data;

import java.util.Date;

/**
 * 点赞实体类
 */
@Data
public class Like {
    /**
     * 点赞ID
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 帖子ID
     */
    private Long postId;

    /**
     * 点赞时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
