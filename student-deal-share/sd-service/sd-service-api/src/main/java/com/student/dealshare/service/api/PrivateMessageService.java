package com.student.dealshare.service.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.student.dealshare.model.dto.PrivateMessageSendDTO;
import com.student.dealshare.model.vo.PrivateMessageVO;

public interface PrivateMessageService {

    void sendMessage(PrivateMessageSendDTO dto, Long senderId);

    Page<PrivateMessageVO> pageMessages(Long userId, Long otherUserId, int page, int size);

    void markAsRead(Long messageId);

    void deleteMessage(Long messageId, Long userId);
}
