package com.student.dealshare.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.student.dealshare.annotation.OperationLog;
import com.student.dealshare.common.result.R;
import com.student.dealshare.model.dto.CommentCreateDTO;
import com.student.dealshare.model.vo.CommentVO;
import com.student.dealshare.security.SecurityUtils;
import com.student.dealshare.service.api.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 评论控制器
 */
@Tag(name = "评论管理")
@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @Operation(summary = "发表评论")
    @PostMapping("/create")
    @OperationLog(module = "社区管理", type = "CREATE", description = "发表评论")
    public R<CommentVO> createComment(@Valid @RequestBody CommentCreateDTO dto) {
        CommentVO result = commentService.createComment(dto);
        return R.ok(result);
    }

    @Operation(summary = "查询帖子评论列表")
    @GetMapping("/post/{postId}")
    public R<List<CommentVO>> listCommentsByPost(
            @PathVariable Long postId,
            @RequestParam(defaultValue = "20") int limit) {
        List<CommentVO> list = commentService.listCommentsByPost(postId, limit);
        return R.ok(list);
    }

    @Operation(summary = "分页查询评论")
    @GetMapping("/post/{postId}/page")
    public R<Page<CommentVO>> pageComments(
            @PathVariable Long postId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<CommentVO> commentPage = (Page<CommentVO>) commentService.pageComments(postId, page, size);
        return R.ok(commentPage);
    }

    @Operation(summary = "删除评论")
    @DeleteMapping("/{id}")
    @OperationLog(module = "社区管理", type = "DELETE", description = "删除评论")
    public R<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return R.ok();
    }

    @Operation(summary = "点赞评论")
    @PostMapping("/like/{id}")
    @OperationLog(module = "社区管理", type = "LIKE", description = "点赞评论")
    public R<Void> likeComment(@PathVariable Long id) {
        commentService.likeComment(id);
        return R.ok();
    }

    @Operation(summary = "取消点赞评论")
    @DeleteMapping("/like/{id}")
    @OperationLog(module = "社区管理", type = "UNLIKE", description = "取消点赞评论")
    public R<Void> unlikeComment(@PathVariable Long id) {
        commentService.unlikeComment(id);
        return R.ok();
    }

    @Operation(summary = "检查是否已点赞评论")
    @GetMapping("/like/check/{id}")
    public R<Boolean> isLiked(@PathVariable Long id) {
        Long userId = SecurityUtils.getCurrentUserId();
        boolean liked = commentService.isLiked(userId, id);
        return R.ok(liked);
    }
}
