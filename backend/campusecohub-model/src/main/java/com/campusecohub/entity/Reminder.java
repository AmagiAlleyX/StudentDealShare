package com.campusecohub.entity;

import lombok.Data;

import java.util.Date;

/**
 * 提醒实体类
 */
@Data
public class Reminder {
    /**
     * 提醒ID
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
     * 提醒时间
     */
    private Date reminderTime;

    /**
     * 提醒状态（0：未提醒，1：已提醒）
     */
    private Integer status;

    /**
     * 创建时间
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
