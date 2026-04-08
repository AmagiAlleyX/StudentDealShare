package com.campusecohub.service.impl;

import com.campusecohub.dto.ReminderCreateDTO;
import com.campusecohub.dto.ReminderInfoDTO;
import com.campusecohub.entity.Reminder;
import com.campusecohub.mapper.ReminderMapper;
import com.campusecohub.mapper.ActivityMapper;
import com.campusecohub.service.ReminderService;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 提醒服务实现类
 */
@Service
public class ReminderServiceImpl implements ReminderService {

    @Resource
    private ReminderMapper reminderMapper;

    @Resource
    private ActivityMapper activityMapper;

    @Override
    public ReminderInfoDTO create(ReminderCreateDTO reminderCreateDTO, Long userId) {
        // 检查活动是否存在
        if (activityMapper.selectById(reminderCreateDTO.getActivityId()) == null) {
            throw new RuntimeException("活动不存在");
        }

        // 创建提醒
        Reminder reminder = new Reminder();
        reminder.setUserId(userId);
        reminder.setActivityId(reminderCreateDTO.getActivityId());
        reminder.setReminderTime(reminderCreateDTO.getReminderTime());
        reminder.setStatus(0); // 未提醒

        reminderMapper.insert(reminder);

        // 转换为DTO返回
        return convertToReminderInfoDTO(reminder);
    }

    @Override
    public ReminderInfoDTO selectById(Long id) {
        Reminder reminder = reminderMapper.selectById(id);
        if (reminder == null) {
            throw new RuntimeException("提醒不存在");
        }

        return convertToReminderInfoDTO(reminder);
    }

    @Override
    public List<ReminderInfoDTO> selectByUserId(Long userId) {
        List<Reminder> reminders = reminderMapper.selectByUserId(userId);

        return reminders.stream()
                .map(this::convertToReminderInfoDTO)
                .collect(Collectors.toList());
    }

    @Override
    public boolean delete(Long id, Long userId) {
        Reminder reminder = reminderMapper.selectById(id);
        if (reminder == null) {
            throw new RuntimeException("提醒不存在");
        }

        // 检查是否是用户自己的提醒
        if (!reminder.getUserId().equals(userId)) {
            throw new RuntimeException("无权限删除该提醒");
        }

        return reminderMapper.delete(id) > 0;
    }

    @Override
    public void sendReminders() {
        // 查询未提醒且提醒时间小于等于当前时间的提醒
        List<Reminder> reminders = reminderMapper.selectUnsentReminders(new Date());

        for (Reminder reminder : reminders) {
            // 这里可以实现发送提醒的逻辑，比如发送短信、推送通知等
            // 暂时模拟发送成功
            System.out.println("发送提醒：用户ID=" + reminder.getUserId() + "，活动ID=" + reminder.getActivityId());

            // 更新提醒状态为已提醒
            reminderMapper.updateStatus(reminder.getId(), 1);
        }
    }

    /**
     * 将Reminder实体转换为ReminderInfoDTO
     */
    private ReminderInfoDTO convertToReminderInfoDTO(Reminder reminder) {
        ReminderInfoDTO reminderInfoDTO = new ReminderInfoDTO();
        reminderInfoDTO.setId(reminder.getId());
        reminderInfoDTO.setActivityId(reminder.getActivityId());
        reminderInfoDTO.setReminderTime(reminder.getReminderTime());
        reminderInfoDTO.setStatus(reminder.getStatus());
        reminderInfoDTO.setCreateTime(reminder.getCreateTime());

        // 查询活动标题
        try {
            reminderInfoDTO.setActivityTitle(activityMapper.selectById(reminder.getActivityId()).getTitle());
        } catch (Exception e) {
            // 活动不存在，忽略
        }

        return reminderInfoDTO;
    }
}
