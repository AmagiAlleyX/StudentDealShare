package com.student.dealshare.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.student.dealshare.annotation.OperationLog;
import com.student.dealshare.common.result.R;
import com.student.dealshare.model.dto.PrivateMessageSendDTO;
import com.student.dealshare.model.vo.NotificationVO;
import com.student.dealshare.model.vo.PrivateMessageVO;
import com.student.dealshare.security.SecurityUtils;
import com.student.dealshare.service.api.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 通知控制器
 */
@Tag(name = "通知管理")
@RestController
@RequestMapping("/api/notification")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @Operation(summary = "查询未读通知列表")
    @GetMapping("/unread")
    public R<List<NotificationVO>> listUnreadNotifications() {
        Long userId = SecurityUtils.getCurrentUserId();
        List<NotificationVO> list = notificationService.listUnreadNotifications(userId);
        return R.ok(list);
    }

    @Operation(summary = "分页查询通知")
    @GetMapping("/page")
    public R<Page<NotificationVO>> pageNotifications(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        Long userId = SecurityUtils.getCurrentUserId();
        Page<NotificationVO> notificationPage = notificationService.pageNotifications(userId, page, size);
        return R.ok(notificationPage);
    }

    @Operation(summary = "标记通知为已读")
    @PutMapping("/read/{id}")
    @OperationLog(module = "消息管理", type = "READ", description = "标记通知为已读")
    public R<Void> markAsRead(@PathVariable Long id) {
        notificationService.markAsRead(id);
        return R.ok();
    }

    @Operation(summary = "标记所有通知为已读")
    @PutMapping("/read-all")
    @OperationLog(module = "消息管理", type = "READ_ALL", description = "全部已读")
    public R<Void> markAllAsRead() {
        Long userId = SecurityUtils.getCurrentUserId();
        notificationService.markAllAsRead(userId);
        return R.ok();
    }

    @Operation(summary = "统计未读通知数量")
    @GetMapping("/unread/count")
    public R<Integer> countUnread() {
        Long userId = SecurityUtils.getCurrentUserId();
        int count = notificationService.countUnread(userId);
        return R.ok(count);
    }
}
