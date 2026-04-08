package com.campusecohub.task;

import com.campusecohub.service.ReminderService;
import javax.annotation.Resource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


/**
 * 提醒定时任务
 */
@Component
public class ReminderTask {

    @Resource
    private ReminderService reminderService;

    /**
     * 每分钟执行一次，检查并发送提醒
     */
    @Scheduled(cron = "0 * * * * ?")
    public void sendReminders() {
        reminderService.sendReminders();
    }
}
