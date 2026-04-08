package com.campusecohub.dto;

import lombok.Data;

import java.util.Date;

/**
 * 提醒信息响应DTO
 */
@Data
public class ReminderInfoDTO {
    /**
     * 提醒ID
     */
    private Long id;

    /**
     * 活动ID
     */
    private Long activityId;

    /**
     * 活动标题
     */
    private String activityTitle;

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
}
