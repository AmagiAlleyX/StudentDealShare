package com.student.dealshare.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.student.dealshare.common.exception.BusinessException;
import com.student.dealshare.common.result.ResultCodeEnum;
import com.student.dealshare.mapper.PrivateMessageMapper;
import com.student.dealshare.model.dto.PrivateMessageSendDTO;
import com.student.dealshare.model.entity.PrivateMessage;
import com.student.dealshare.model.vo.PrivateMessageVO;
import com.student.dealshare.service.api.PrivateMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 私信服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PrivateMessageServiceImpl extends ServiceImpl<PrivateMessageMapper, PrivateMessage> implements PrivateMessageService {

    private final PrivateMessageMapper privateMessageMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void sendMessage(PrivateMessageSendDTO dto, Long senderId) {
        PrivateMessage message = new PrivateMessage();
        message.setSenderId(senderId);
        message.setReceiverId(dto.getReceiverId());
        message.setContent(dto.getContent());
        message.setIsRead(0);
        
        privateMessageMapper.insert(message);
        log.info("私信发送成功，senderId: {}, receiverId: {}", senderId, dto.getReceiverId());
    }

    @Override
    public Page<PrivateMessageVO> pageMessages(Long userId, Long otherUserId, int page, int size) {
        Page<PrivateMessage> messagePage = new Page<>(page, size);
        LambdaQueryWrapper<PrivateMessage> wrapper = new LambdaQueryWrapper<>();
        wrapper.and(w -> w.eq(PrivateMessage::getSenderId, userId)
                          .eq(PrivateMessage::getReceiverId, otherUserId)
                     .or(w1 -> w1.eq(PrivateMessage::getSenderId, otherUserId)
                                 .eq(PrivateMessage::getReceiverId, userId)))
               .orderByDesc(PrivateMessage::getCreateTime);
        
        Page<PrivateMessage> result = privateMessageMapper.selectPage(messagePage, wrapper);
        return (Page<PrivateMessageVO>) result.convert(this::convertToVO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void markAsRead(Long messageId) {
        PrivateMessage message = privateMessageMapper.selectById(messageId);
        if (message != null) {
            message.setIsRead(1);
            privateMessageMapper.updateById(message);
            log.info("私信标记为已读，messageId: {}", messageId);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMessage(Long messageId, Long userId) {
        PrivateMessage message = privateMessageMapper.selectById(messageId);
        if (message == null) {
            throw new BusinessException(ResultCodeEnum.MESSAGE_NOT_FOUND);
        }
        
        // 只能删除自己的消息
        if (!message.getSenderId().equals(userId) && !message.getReceiverId().equals(userId)) {
            throw new BusinessException(ResultCodeEnum.NO_PERMISSION);
        }
        
        privateMessageMapper.deleteById(messageId);
        log.info("私信删除成功，messageId: {}", messageId);
    }

    /**
     * 转换为 VO
     */
    private PrivateMessageVO convertToVO(PrivateMessage message) {
        PrivateMessageVO vo = new PrivateMessageVO();
        vo.setMessageId(message.getMessageId());
        vo.setSenderId(message.getSenderId());
        vo.setReceiverId(message.getReceiverId());
        vo.setContent(message.getContent());
        vo.setIsRead(message.getIsRead());
        vo.setCreateTime(message.getCreateTime());
        return vo;
    }
}
