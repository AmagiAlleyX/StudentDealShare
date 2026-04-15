package com.student.dealshare.service.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.student.dealshare.model.dto.PrivateMessageSendDTO;
import com.student.dealshare.model.vo.PrivateMessageVO;

/**
 * 私信服务接口
 */
public interface PrivateMessageService {

    /**
     * 发送私信
     * @param dto 私信内容
     * @param senderId 发送者 ID
     */
    void sendMessage(PrivateMessageSendDTO dto, Long senderId);

    /**
     * 查询会话消息列表
     * @param userId 用户 ID
     * @param otherUserId 对话用户 ID
     * @param page 页码
     * @param size 每页数量
     * @return 消息列表
     */
    Page<PrivateMessageVO> pageMessages(Long userId, Long otherUserId, int page, int size);

    /**
     * 标记消息为已读
     * @param messageId 消息 ID
     */
    void markAsRead(Long messageId);

    /**
     * 删除消息
     * @param messageId 消息 ID
     * @param userId 用户 ID
     */
    void deleteMessage(Long messageId, Long userId);
}
