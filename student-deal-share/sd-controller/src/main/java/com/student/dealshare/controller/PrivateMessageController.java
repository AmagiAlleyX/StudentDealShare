package com.student.dealshare.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.student.dealshare.annotation.OperationLog;
import com.student.dealshare.common.result.R;
import com.student.dealshare.model.dto.PrivateMessageSendDTO;
import com.student.dealshare.model.vo.PrivateMessageVO;
import com.student.dealshare.security.SecurityUtils;
import com.student.dealshare.service.api.PrivateMessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 私信控制器
 */
@Tag(name = "私信管理")
@RestController
@RequestMapping("/api/message")
@RequiredArgsConstructor
public class PrivateMessageController {

    private final PrivateMessageService privateMessageService;

    @Operation(summary = "发送私信")
    @PostMapping("/send")
    @OperationLog(module = "消息管理", type = "SEND", description = "发送私信")
    public R<Void> sendMessage(@Valid @RequestBody PrivateMessageSendDTO dto) {
        Long senderId = SecurityUtils.getCurrentUserId();
        privateMessageService.sendMessage(dto, senderId);
        return R.ok();
    }

    @Operation(summary = "查询会话消息列表")
    @GetMapping("/conversation/{otherUserId}")
    public R<Page<PrivateMessageVO>> pageMessages(
            @PathVariable Long otherUserId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size) {
        Long userId = SecurityUtils.getCurrentUserId();
        Page<PrivateMessageVO> messagePage = privateMessageService.pageMessages(userId, otherUserId, page, size);
        return R.ok(messagePage);
    }

    @Operation(summary = "标记消息为已读")
    @PutMapping("/read/{id}")
    @OperationLog(module = "消息管理", type = "READ", description = "标记私信为已读")
    public R<Void> markAsRead(@PathVariable Long id) {
        privateMessageService.markAsRead(id);
        return R.ok();
    }

    @Operation(summary = "删除消息")
    @DeleteMapping("/{id}")
    @OperationLog(module = "消息管理", type = "DELETE", description = "删除私信")
    public R<Void> deleteMessage(@PathVariable Long id) {
        Long userId = SecurityUtils.getCurrentUserId();
        privateMessageService.deleteMessage(id, userId);
        return R.ok();
    }
}
