package com.campusecohub.dto;

import lombok.Data;

import java.util.Date;

/**
 * 提醒创建请求DTO
 */
@Data
public class ReminderCreateDTO {
    /**
     * 活动ID
     */
    private Long activityId;

    /**
     * 提醒时间
     */
    private Date reminderTime;
}
