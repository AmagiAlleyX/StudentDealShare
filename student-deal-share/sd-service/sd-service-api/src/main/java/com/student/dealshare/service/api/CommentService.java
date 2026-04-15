package com.student.dealshare.service.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.student.dealshare.model.dto.CommentCreateDTO;
import com.student.dealshare.model.vo.CommentVO;
import java.util.List;

/**
 * 评论服务接口
 */
public interface CommentService {

    /**
     * 发表评论
     * @param dto 评论信息
     * @return 评论信息
     */
    CommentVO createComment(CommentCreateDTO dto);

    /**
     * 查询帖子评论列表
     * @param postId 帖子 ID
     * @param limit 数量限制
     * @return 评论列表
     */
    List<CommentVO> listCommentsByPost(Long postId, int limit);

    /**
     * 分页查询评论
     * @param postId 帖子 ID
     * @param page 页码
     * @param size 每页数量
     * @return 评论列表
     */
    Page<CommentVO> pageComments(Long postId, int page, int size);

    /**
     * 删除评论
     * @param commentId 评论 ID
     */
    void deleteComment(Long commentId);

    /**
     * 点赞评论
     * @param commentId 评论 ID
     */
    void likeComment(Long commentId);

    /**
     * 取消点赞评论
     * @param commentId 评论 ID
     */
    void unlikeComment(Long commentId);

    /**
     * 检查是否已点赞评论
     * @param userId 用户 ID
     * @param commentId 评论 ID
     * @return true-已点赞，false-未点赞
     */
    boolean isLiked(Long userId, Long commentId);
}
