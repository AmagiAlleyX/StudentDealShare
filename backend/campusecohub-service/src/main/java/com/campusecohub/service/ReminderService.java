package com.campusecohub.service;

import com.campusecohub.dto.ReminderCreateDTO;
import com.campusecohub.dto.ReminderInfoDTO;

import java.util.List;

/**
 * 提醒服务接口
 */
public interface ReminderService {
    /**
     * 创建提醒
     */
    ReminderInfoDTO create(ReminderCreateDTO reminderCreateDTO, Long userId);

    /**
     * 根据ID查询提醒
     */
    ReminderInfoDTO selectById(Long id);

    /**
     * 根据用户ID查询提醒
     */
    List<ReminderInfoDTO> selectByUserId(Long userId);

    /**
     * 删除提醒
     */
    boolean delete(Long id, Long userId);

    /**
     * 发送提醒
     */
    void sendReminders();
}
