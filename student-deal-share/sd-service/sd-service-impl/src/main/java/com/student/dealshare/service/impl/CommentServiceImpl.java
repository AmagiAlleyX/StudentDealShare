package com.student.dealshare.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.student.dealshare.common.exception.BusinessException;
import com.student.dealshare.common.result.ResultCodeEnum;
import com.student.dealshare.converter.CommentConverter;
import com.student.dealshare.mapper.CommentMapper;
import com.student.dealshare.mapper.LikeRecordMapper;
import com.student.dealshare.model.dto.CommentCreateDTO;
import com.student.dealshare.model.entity.Comment;
import com.student.dealshare.model.entity.LikeRecord;
import com.student.dealshare.model.vo.CommentVO;
import com.student.dealshare.security.SecurityUtils;
import com.student.dealshare.service.api.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 评论服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    private final CommentMapper commentMapper;
    private final LikeRecordMapper likeRecordMapper;
    private final CommentConverter commentConverter;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommentVO createComment(CommentCreateDTO dto) {
        Comment comment = commentConverter.toEntity(dto);
        comment.setUserId(SecurityUtils.getCurrentUserId());
        comment.setStatus(1);
        comment.setLikeCount(0L);
        
        // 设置评论级别
        if (dto.getParentId() == null || dto.getParentId() == 0) {
            comment.setLevel(1);
        } else {
            Comment parent = commentMapper.selectById(dto.getParentId());
            if (parent == null) {
                throw new BusinessException(ResultCodeEnum.COMMENT_NOT_FOUND);
            }
            comment.setLevel(parent.getLevel() + 1);
        }
        
        commentMapper.insert(comment);
        log.info("评论发表成功，commentId: {}", comment.getCommentId());

        return commentConverter.toVO(comment);
    }

    @Override
    public List<CommentVO> listCommentsByPost(Long postId, int limit) {
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Comment::getPostId, postId)
               .eq(Comment::getStatus, 1)
               .eq(Comment::getParentId, 0)
               .orderByDesc(Comment::getCreateTime)
               .last("LIMIT " + limit);
        
        List<Comment> comments = commentMapper.selectList(wrapper);
        return comments.stream()
                .map(commentConverter::toVO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<CommentVO> pageComments(Long postId, int page, int size) {
        Page<Comment> commentPage = new Page<>(page, size);
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Comment::getPostId, postId)
               .eq(Comment::getStatus, 1)
               .orderByDesc(Comment::getCreateTime);
        
        Page<Comment> result = commentMapper.selectPage(commentPage, wrapper);
        return (Page<CommentVO>) result.convert(commentConverter::toVO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteComment(Long commentId) {
        Comment comment = commentMapper.selectById(commentId);
        if (comment == null) {
            throw new BusinessException(ResultCodeEnum.COMMENT_NOT_FOUND);
        }
        
        commentMapper.deleteById(commentId);
        log.info("评论删除成功，commentId: {}", commentId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void likeComment(Long commentId) {
        Long userId = SecurityUtils.getCurrentUserId();
        
        LambdaQueryWrapper<LikeRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LikeRecord::getUserId, userId)
               .eq(LikeRecord::getTargetType, 2)
               .eq(LikeRecord::getTargetId, commentId);
        
        LikeRecord exist = likeRecordMapper.selectOne(wrapper);
        if (exist != null) {
            throw new BusinessException(ResultCodeEnum.ALREADY_LIKED);
        }

        LikeRecord likeRecord = new LikeRecord();
        likeRecord.setUserId(userId);
        likeRecord.setTargetType(2L);
        likeRecord.setTargetId(commentId);
        likeRecordMapper.insert(likeRecord);

        Comment comment = commentMapper.selectById(commentId);
        comment.setLikeCount(comment.getLikeCount() + 1);
        commentMapper.updateById(comment);
        
        log.info("评论点赞成功，commentId: {}, userId: {}", commentId, userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void unlikeComment(Long commentId) {
        Long userId = SecurityUtils.getCurrentUserId();

        LambdaQueryWrapper<LikeRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LikeRecord::getUserId, userId)
               .eq(LikeRecord::getTargetType, 2)
               .eq(LikeRecord::getTargetId, commentId);
        likeRecordMapper.delete(wrapper);

        Comment comment = commentMapper.selectById(commentId);
        if (comment != null && comment.getLikeCount() > 0) {
            comment.setLikeCount(comment.getLikeCount() - 1);
            commentMapper.updateById(comment);
        }
        
        log.info("评论取消点赞成功，commentId: {}, userId: {}", commentId, userId);
    }

    @Override
    public boolean isLiked(Long userId, Long commentId) {
        LambdaQueryWrapper<LikeRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LikeRecord::getUserId, userId)
               .eq(LikeRecord::getTargetType, 2)
               .eq(LikeRecord::getTargetId, commentId);
        return likeRecordMapper.selectCount(wrapper) > 0;
    }
}
