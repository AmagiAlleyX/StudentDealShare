package com.campusecohub.service;

import com.campusecohub.dto.PostCreateDTO;
import com.campusecohub.dto.PostQueryDTO;
import com.campusecohub.dto.PostInfoDTO;
import com.campusecohub.dto.PostListDTO;
import com.campusecohub.dto.CommentCreateDTO;
import com.campusecohub.dto.CommentInfoDTO;

import java.util.List;

/**
 * 帖子服务接口
 */
public interface PostService {
    /**
     * 创建帖子
     */
    PostInfoDTO create(PostCreateDTO postCreateDTO, Long userId);

    /**
     * 根据ID查询帖子
     */
    PostInfoDTO selectById(Long id, Long userId);

    /**
     * 查询帖子列表
     */
    PostListDTO selectList(PostQueryDTO postQueryDTO, Long userId);

    /**
     * 根据用户ID查询帖子
     */
    PostListDTO selectByUserId(Long userId, Integer pageNum, Integer pageSize);

    /**
     * 点赞帖子
     */
    boolean like(Long userId, Long postId);

    /**
     * 取消点赞
     */
    boolean unlike(Long userId, Long postId);

    /**
     * 评论帖子
     */
    CommentInfoDTO comment(CommentCreateDTO commentCreateDTO, Long userId);

    /**
     * 根据帖子ID查询评论
     */
    List<CommentInfoDTO> selectCommentsByPostId(Long postId);
}
