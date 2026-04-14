package com.student.dealshare.service.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.student.dealshare.model.dto.CommentCreateDTO;
import com.student.dealshare.model.vo.CommentVO;

import java.util.List;

public interface CommentService {

    CommentVO createComment(CommentCreateDTO dto);

    List<CommentVO> listCommentsByPost(Long postId, int limit);

    Page<CommentVO> pageComments(Long postId, int page, int size);

    void deleteComment(Long commentId);

    void likeComment(Long commentId);

    void unlikeComment(Long commentId);

    boolean isLiked(Long userId, Long commentId);
}
