package com.student.dealshare.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.student.dealshare.annotation.OperationLog;
import com.student.dealshare.common.result.R;
import com.student.dealshare.model.dto.PostCreateDTO;
import com.student.dealshare.model.dto.PostUpdateDTO;
import com.student.dealshare.model.vo.PostVO;
import com.student.dealshare.security.SecurityUtils;
import com.student.dealshare.service.api.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 帖子控制器
 */
@Tag(name = "帖子管理")
@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @Operation(summary = "发布帖子")
    @PostMapping("/create")
    @OperationLog(module = "社区管理", type = "CREATE", description = "发布帖子")
    public R<PostVO> createPost(@Valid @RequestBody PostCreateDTO dto) {
        PostVO result = postService.createPost(dto);
        return R.ok(result);
    }

    @Operation(summary = "获取帖子详情")
    @GetMapping("/{id}")
    public R<PostVO> getPostById(@PathVariable("id") Long id) {
        postService.incrementViewCount(id);
        PostVO result = postService.getPostById(id);
        return R.ok(result);
    }

    @Operation(summary = "分页查询帖子")
    @GetMapping("/page")
    public R<Page<PostVO>> pagePosts(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        Page<PostVO> postPage = postService.pagePosts(page, size);
        return R.ok(postPage);
    }

    @Operation(summary = "更新帖子")
    @PutMapping("/update")
    @OperationLog(module = "社区管理", type = "UPDATE", description = "更新帖子")
    public R<Void> updatePost(@Valid @RequestBody PostUpdateDTO dto) {
        postService.updatePost(dto);
        return R.ok();
    }

    @Operation(summary = "删除帖子")
    @DeleteMapping("/{id}")
    @OperationLog(module = "社区管理", type = "DELETE", description = "删除帖子")
    public R<Void> deletePost(@PathVariable("id") Long id) {
        postService.deletePost(id);
        return R.ok();
    }

    @Operation(summary = "点赞帖子")
    @PostMapping("/like/{id}")
    @OperationLog(module = "社区管理", type = "LIKE", description = "点赞帖子")
    public R<Void> likePost(@PathVariable("id") Long id) {
        postService.likePost(id);
        return R.ok();
    }

    @Operation(summary = "取消点赞")
    @DeleteMapping("/like/{id}")
    @OperationLog(module = "社区管理", type = "UNLIKE", description = "取消点赞")
    public R<Void> unlikePost(@PathVariable("id") Long id) {
        postService.unlikePost(id);
        return R.ok();
    }

    @Operation(summary = "检查是否已点赞")
    @GetMapping("/like/check/{id}")
    public R<Boolean> isLiked(@PathVariable("id") Long id) {
        Long userId = SecurityUtils.getCurrentUserId();
        boolean liked = postService.isLiked(userId, id);
        return R.ok(liked);
    }

    @Operation(summary = "查询用户发布的帖子")
    @GetMapping("/user/{userId}/page")
    public R<Page<PostVO>> pageUserPosts(
            @PathVariable("userId") Long userId,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        Page<PostVO> postPage = postService.pageUserPosts(userId, page, size);
        return R.ok(postPage);
    }


    @Operation(summary = "热门帖子列表")
    @GetMapping("/hot")
    public R<List<PostVO>> listHotPosts(@RequestParam(value = "limit", defaultValue = "10") int limit) {
        List<PostVO> list = postService.listHotPosts(limit);
        return R.ok(list);
    }

    @Operation(summary = "精华帖子列表")
    @GetMapping("/essence")
    public R<List<PostVO>> listEssencePosts(@RequestParam(value = "limit", defaultValue = "10") int limit) {
        List<PostVO> list = postService.listEssencePosts(limit);
        return R.ok(list);
    }
}
