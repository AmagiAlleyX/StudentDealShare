package com.student.dealshare.service.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.student.dealshare.model.vo.NotificationVO;
import java.util.List;

/**
 * 通知服务接口
 */
public interface NotificationService {

    /**
     * 发送通知
     * @param userId 用户 ID
     * @param type 通知类型
     * @param title 标题
     * @param content 内容
     * @param relatedId 关联 ID
     */
    void sendNotification(Long userId, Integer type, String title, String content, Long relatedId);

    /**
     * 查询未读通知列表
     * @param userId 用户 ID
     * @return 通知列表
     */
    List<NotificationVO> listUnreadNotifications(Long userId);

    /**
     * 分页查询通知
     * @param userId 用户 ID
     * @param page 页码
     * @param size 每页数量
     * @return 通知列表
     */
    Page<NotificationVO> pageNotifications(Long userId, int page, int size);

    /**
     * 标记通知为已读
     * @param notificationId 通知 ID
     */
    void markAsRead(Long notificationId);

    /**
     * 标记所有通知为已读
     * @param userId 用户 ID
     */
    void markAllAsRead(Long userId);

    /**
     * 统计未读通知数量
     * @param userId 用户 ID
     * @return 未读数量
     */
    int countUnread(Long userId);
}
