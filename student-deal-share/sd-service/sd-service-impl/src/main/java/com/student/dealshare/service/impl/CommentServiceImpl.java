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
        comment.setLikeCount(0);
        
        commentMapper.insert(comment);
        log.info("评论发表成功，commentId: {}", comment.getId());

        return commentConverter.toVO(comment);
    }

    @Override
    public List<CommentVO> listCommentsByPost(Long targetId, int limit) {
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Comment::getTargetType, 2)
               .eq(Comment::getTargetId, targetId)
               .eq(Comment::getStatus, 1)
               .eq(Comment::getParentId, 0)
               .orderByDesc(Comment::getCreatedAt)
               .last("LIMIT " + limit);
        
        List<Comment> comments = commentMapper.selectList(wrapper);
        return comments.stream()
                .map(commentConverter::toVO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<CommentVO> pageComments(Long targetId, int page, int size) {
        Page<Comment> commentPage = new Page<>(page, size);
        LambdaQueryWrapper<Comment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Comment::getTargetType, 2)
               .eq(Comment::getTargetId, targetId)
               .eq(Comment::getStatus, 1)
               .orderByDesc(Comment::getCreatedAt);
        
        Page<Comment> result = commentMapper.selectPage(commentPage, wrapper);
        return (Page<CommentVO>) result.convert(commentConverter::toVO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteComment(Long id) {
        Comment comment = commentMapper.selectById(id);
        if (comment == null) {
            throw new BusinessException(ResultCodeEnum.COMMENT_NOT_FOUND);
        }
        
        commentMapper.deleteById(id);
        log.info("评论删除成功，commentId: {}", id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void likeComment(Long id) {
        Long userId = SecurityUtils.getCurrentUserId();
        
        LambdaQueryWrapper<LikeRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LikeRecord::getUserId, userId)
               .eq(LikeRecord::getTargetType, 3)
               .eq(LikeRecord::getTargetId, id);
        
        LikeRecord exist = likeRecordMapper.selectOne(wrapper);
        if (exist != null) {
            throw new BusinessException(ResultCodeEnum.ALREADY_LIKED);
        }

        LikeRecord likeRecord = new LikeRecord();
        likeRecord.setUserId(userId);
        likeRecord.setTargetType(3);
        likeRecord.setTargetId(id);
        likeRecordMapper.insert(likeRecord);

        Comment comment = commentMapper.selectById(id);
        comment.setLikeCount(comment.getLikeCount() + 1);
        commentMapper.updateById(comment);
        
        log.info("评论点赞成功，commentId: {}, userId: {}", id, userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void unlikeComment(Long id) {
        Long userId = SecurityUtils.getCurrentUserId();

        LambdaQueryWrapper<LikeRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LikeRecord::getUserId, userId)
               .eq(LikeRecord::getTargetType, 3)
               .eq(LikeRecord::getTargetId, id);
        likeRecordMapper.delete(wrapper);

        Comment comment = commentMapper.selectById(id);
        if (comment != null && comment.getLikeCount() > 0) {
            comment.setLikeCount(comment.getLikeCount() - 1);
            commentMapper.updateById(comment);
        }
        
        log.info("评论取消点赞成功，commentId: {}, userId: {}", id, userId);
    }

    @Override
    public boolean isLiked(Long userId, Long id) {
        LambdaQueryWrapper<LikeRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LikeRecord::getUserId, userId)
               .eq(LikeRecord::getTargetType, 3)
               .eq(LikeRecord::getTargetId, id);
        return likeRecordMapper.selectCount(wrapper) > 0;
    }
}
