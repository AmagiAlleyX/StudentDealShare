package com.student.dealshare.service.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.student.dealshare.model.dto.PrivateMessageSendDTO;
import com.student.dealshare.model.vo.NotificationVO;
import com.student.dealshare.model.vo.PrivateMessageVO;

import java.util.List;

public interface NotificationService {

    void sendNotification(Long userId, Integer type, String title, String content, Long relatedId);

    List<NotificationVO> listUnreadNotifications(Long userId);

    Page<NotificationVO> pageNotifications(Long userId, int page, int size);

    void markAsRead(Long notificationId);

    void markAllAsRead(Long userId);

    int countUnread(Long userId);
}
