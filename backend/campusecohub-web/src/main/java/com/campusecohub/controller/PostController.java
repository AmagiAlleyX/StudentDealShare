package com.campusecohub.controller;

import com.campusecohub.dto.PostCreateDTO;
import com.campusecohub.dto.PostQueryDTO;
import com.campusecohub.dto.PostInfoDTO;
import com.campusecohub.dto.PostListDTO;
import com.campusecohub.dto.CommentCreateDTO;
import com.campusecohub.dto.CommentInfoDTO;
import com.campusecohub.dto.ResponseDTO;
import com.campusecohub.service.PostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 帖子控制器
 */
@RestController
@RequestMapping("/api/post")
@Api(tags = "帖子管理")
public class PostController {

    @Resource
    private PostService postService;

    /**
     * 创建帖子
     */
    @PostMapping("/create")
    @ApiOperation("创建帖子")
    public ResponseDTO<PostInfoDTO> create(@RequestBody PostCreateDTO postCreateDTO, @RequestParam Long userId) {
        PostInfoDTO postInfoDTO = postService.create(postCreateDTO, userId);
        return ResponseDTO.success(postInfoDTO);
    }

    /**
     * 获取帖子详情
     */
    @GetMapping("/detail/{id}")
    @ApiOperation("获取帖子详情")
    public ResponseDTO<PostInfoDTO> getDetail(@PathVariable Long id, @RequestParam(required = false) Long userId) {
        PostInfoDTO postInfoDTO = postService.selectById(id, userId);
        return ResponseDTO.success(postInfoDTO);
    }

    /**
     * 获取帖子列表
     */
    @GetMapping("/list")
    @ApiOperation("获取帖子列表")
    public ResponseDTO<PostListDTO> getList(
            @RequestParam(required = false) String keyword,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) Long userId
    ) {
        PostQueryDTO postQueryDTO = new PostQueryDTO();
        postQueryDTO.setKeyword(keyword);
        postQueryDTO.setPageNum(pageNum);
        postQueryDTO.setPageSize(pageSize);

        PostListDTO postListDTO = postService.selectList(postQueryDTO, userId);
        return ResponseDTO.success(postListDTO);
    }

    /**
     * 获取用户发布的帖子
     */
    @GetMapping("/user/{userId}")
    @ApiOperation("获取用户发布的帖子")
    public ResponseDTO<PostListDTO> getByUserId(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize
    ) {
        PostListDTO postListDTO = postService.selectByUserId(userId, pageNum, pageSize);
        return ResponseDTO.success(postListDTO);
    }

    /**
     * 点赞帖子
     */
    @PostMapping("/like")
    @ApiOperation("点赞帖子")
    public ResponseDTO<Boolean> like(@RequestParam Long userId, @RequestParam Long postId) {
        boolean result = postService.like(userId, postId);
        return ResponseDTO.success(result);
    }

    /**
     * 取消点赞
     */
    @PostMapping("/unlike")
    @ApiOperation("取消点赞")
    public ResponseDTO<Boolean> unlike(@RequestParam Long userId, @RequestParam Long postId) {
        boolean result = postService.unlike(userId, postId);
        return ResponseDTO.success(result);
    }

    /**
     * 评论帖子
     */
    @PostMapping("/comment")
    @ApiOperation("评论帖子")
    public ResponseDTO<CommentInfoDTO> comment(@RequestBody CommentCreateDTO commentCreateDTO, @RequestParam Long userId) {
        CommentInfoDTO commentInfoDTO = postService.comment(commentCreateDTO, userId);
        return ResponseDTO.success(commentInfoDTO);
    }

    /**
     * 获取帖子评论
     */
    @GetMapping("/comments/{postId}")
    @ApiOperation("获取帖子评论")
    public ResponseDTO<List<CommentInfoDTO>> getComments(@PathVariable Long postId) {
        List<CommentInfoDTO> commentInfoDTOs = postService.selectCommentsByPostId(postId);
        return ResponseDTO.success(commentInfoDTOs);
    }
}
