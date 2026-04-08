package com.campusecohub.entity;

import lombok.Data;

import java.util.Date;

/**
 * 收藏实体类
 */
@Data
public class Collection {
    /**
     * 收藏ID
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 活动ID
     */
    private Long activityId;

    /**
     * 收藏时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 活动信息（关联查询）
     */
    private Activity activity;
}
