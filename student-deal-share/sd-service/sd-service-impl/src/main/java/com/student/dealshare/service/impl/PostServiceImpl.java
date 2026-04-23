package com.student.dealshare.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.student.dealshare.common.exception.BusinessException;
import com.student.dealshare.common.result.ResultCodeEnum;
import com.student.dealshare.converter.PostConverter;
import com.student.dealshare.mapper.LikeRecordMapper;
import com.student.dealshare.mapper.PostMapper;
import com.student.dealshare.mapper.PostTopicMapper;
import com.student.dealshare.model.dto.PostCreateDTO;
import com.student.dealshare.model.dto.PostUpdateDTO;
import com.student.dealshare.model.entity.LikeRecord;
import com.student.dealshare.model.entity.Post;
import com.student.dealshare.model.entity.PostTopic;
import com.student.dealshare.model.vo.PostVO;
import com.student.dealshare.security.SecurityUtils;
import com.student.dealshare.service.api.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 帖子服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PostServiceImpl extends ServiceImpl<PostMapper, Post> implements PostService {

    private final PostMapper postMapper;
    private final LikeRecordMapper likeRecordMapper;
    private final PostTopicMapper postTopicMapper;
    private final PostConverter postConverter;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public PostVO createPost(PostCreateDTO dto) {
        Post post = postConverter.toEntity(dto);
        post.setUserId(SecurityUtils.getCurrentUserId());
        post.setStatus(1);
        post.setIsTop(0);
        post.setIsEssence(0);
        post.setViewCount(0L);
        post.setCommentCount(0L);
        post.setLikeCount(0L);
        post.setShareCount(0L);
        
        postMapper.insert(post);
        log.info("帖子发布成功，postId: {}", post.getPostId());

        // 关联话题
        if (dto.getTopicIds() != null && dto.getTopicIds().length > 0) {
            for (Long topicId : dto.getTopicIds()) {
                PostTopic postTopic = new PostTopic();
                postTopic.setPostId(post.getPostId());
                postTopic.setTopicId(topicId);
                postTopicMapper.insert(postTopic);
            }
            log.info("帖子关联话题成功，postId: {}, topicIds: {}", post.getPostId(), dto.getTopicIds());
        }

        return postConverter.toVO(post);
    }

    @Override
    public PostVO getPostById(Long postId) {
        Post post = postMapper.selectById(postId);
        if (post == null) {
            throw new BusinessException(ResultCodeEnum.POST_NOT_FOUND);
        }
        return postConverter.toVO(post);
    }

    @Override
    public Page<PostVO> pagePosts(int page, int size) {
        Page<Post> postPage = new Page<>(page, size);
        LambdaQueryWrapper<Post> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Post::getStatus, 1)
               .orderByDesc(Post::getCreateTime);
        
        Page<Post> result = postMapper.selectPage(postPage, wrapper);
        return (Page<PostVO>) result.convert(postConverter::toVO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePost(PostUpdateDTO dto) {
        Post post = postMapper.selectById(dto.getPostId());
        if (post == null) {
            throw new BusinessException(ResultCodeEnum.POST_NOT_FOUND);
        }

        postConverter.updatePostFromDTO(dto, post);
        postMapper.updateById(post);
        log.info("帖子更新成功，postId: {}", dto.getPostId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deletePost(Long postId) {
        Post post = postMapper.selectById(postId);
        if (post == null) {
            throw new BusinessException(ResultCodeEnum.POST_NOT_FOUND);
        }
        
        postMapper.deleteById(postId);
        log.info("帖子删除成功，postId: {}", postId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void incrementViewCount(Long postId) {
        Post post = postMapper.selectById(postId);
        if (post != null) {
            post.setViewCount(post.getViewCount() + 1);
            postMapper.updateById(post);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void likePost(Long postId) {
        Long userId = SecurityUtils.getCurrentUserId();
        
        LambdaQueryWrapper<LikeRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LikeRecord::getUserId, userId)
               .eq(LikeRecord::getTargetType, 1)
               .eq(LikeRecord::getTargetId, postId);
        
        LikeRecord exist = likeRecordMapper.selectOne(wrapper);
        if (exist != null) {
            throw new BusinessException(ResultCodeEnum.ALREADY_LIKED);
        }

        LikeRecord likeRecord = new LikeRecord();
        likeRecord.setUserId(userId);
        likeRecord.setTargetType(1L);
        likeRecord.setTargetId(postId);
        likeRecordMapper.insert(likeRecord);

        Post post = postMapper.selectById(postId);
        post.setLikeCount(post.getLikeCount() + 1);
        postMapper.updateById(post);
        
        log.info("帖子点赞成功，postId: {}, userId: {}", postId, userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void unlikePost(Long postId) {
        Long userId = SecurityUtils.getCurrentUserId();

        LambdaQueryWrapper<LikeRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LikeRecord::getUserId, userId)
               .eq(LikeRecord::getTargetType, 1)
               .eq(LikeRecord::getTargetId, postId);
        likeRecordMapper.delete(wrapper);

        Post post = postMapper.selectById(postId);
        if (post != null && post.getLikeCount() > 0) {
            post.setLikeCount(post.getLikeCount() - 1);
            postMapper.updateById(post);
        }
        
        log.info("帖子取消点赞成功，postId: {}, userId: {}", postId, userId);
    }

    @Override
    public boolean isLiked(Long userId, Long postId) {
        LambdaQueryWrapper<LikeRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LikeRecord::getUserId, userId)
               .eq(LikeRecord::getTargetType, 1)
               .eq(LikeRecord::getTargetId, postId);
        return likeRecordMapper.selectCount(wrapper) > 0;
    }

    @Override
    public Page<PostVO> pageUserPosts(Long userId, int page, int size) {
        Page<Post> postPage = new Page<>(page, size);
        LambdaQueryWrapper<Post> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Post::getUserId, userId)
               .eq(Post::getStatus, 1)
               .orderByDesc(Post::getCreateTime);
        
        Page<Post> result = postMapper.selectPage(postPage, wrapper);
        return (Page<PostVO>) result.convert(postConverter::toVO);
    }

    @Override
    public List<PostVO> listHotPosts(int limit) {
        LambdaQueryWrapper<Post> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Post::getStatus, 1)
               .orderByDesc(Post::getViewCount)
               .last("LIMIT " + limit);
        
        List<Post> posts = postMapper.selectList(wrapper);
        return posts.stream()
                .map(postConverter::toVO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PostVO> listEssencePosts(int limit) {
        LambdaQueryWrapper<Post> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Post::getStatus, 1)
               .eq(Post::getIsEssence, 1)
               .orderByDesc(Post::getCreateTime)
               .last("LIMIT " + limit);
        
        List<Post> posts = postMapper.selectList(wrapper);
        return posts.stream()
                .map(postConverter::toVO)
                .collect(Collectors.toList());
    }
}
