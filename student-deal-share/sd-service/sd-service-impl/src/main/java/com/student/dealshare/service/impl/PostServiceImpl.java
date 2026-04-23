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
        post.setTop(0);
        post.setEssence(0);
        post.setViewCount(0);
        post.setCommentCount(0);
        post.setLikeCount(0);
        post.setShareCount(0);
        
        postMapper.insert(post);
        log.info("帖子发布成功，postId: {}", post.getId());

        if (dto.getTopicIds() != null && dto.getTopicIds().length > 0) {
            for (Long topicId : dto.getTopicIds()) {
                PostTopic postTopic = new PostTopic();
                postTopic.setPostId(post.getId());
                postTopic.setTopicId(topicId);
                postTopicMapper.insert(postTopic);
            }
            log.info("帖子关联话题成功，postId: {}, topicIds: {}", post.getId(), dto.getTopicIds());
        }

        return postConverter.toVO(post);
    }

    @Override
    public PostVO getPostById(Long id) {
        Post post = postMapper.selectById(id);
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
               .orderByDesc(Post::getCreatedAt);
        
        Page<Post> result = postMapper.selectPage(postPage, wrapper);
        return (Page<PostVO>) result.convert(postConverter::toVO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePost(PostUpdateDTO dto) {
        Post post = postMapper.selectById(dto.getId());
        if (post == null) {
            throw new BusinessException(ResultCodeEnum.POST_NOT_FOUND);
        }

        postConverter.updatePostFromDTO(dto, post);
        postMapper.updateById(post);
        log.info("帖子更新成功，postId: {}", dto.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deletePost(Long id) {
        Post post = postMapper.selectById(id);
        if (post == null) {
            throw new BusinessException(ResultCodeEnum.POST_NOT_FOUND);
        }
        
        postMapper.deleteById(id);
        log.info("帖子删除成功，postId: {}", id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void incrementViewCount(Long id) {
        Post post = postMapper.selectById(id);
        if (post != null) {
            post.setViewCount(post.getViewCount() + 1);
            postMapper.updateById(post);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void likePost(Long id) {
        Long userId = SecurityUtils.getCurrentUserId();
        
        LambdaQueryWrapper<LikeRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LikeRecord::getUserId, userId)
               .eq(LikeRecord::getTargetType, 2)
               .eq(LikeRecord::getTargetId, id);
        
        LikeRecord exist = likeRecordMapper.selectOne(wrapper);
        if (exist != null) {
            throw new BusinessException(ResultCodeEnum.ALREADY_LIKED);
        }

        LikeRecord likeRecord = new LikeRecord();
        likeRecord.setUserId(userId);
        likeRecord.setTargetType(2);
        likeRecord.setTargetId(id);
        likeRecordMapper.insert(likeRecord);

        Post post = postMapper.selectById(id);
        post.setLikeCount(post.getLikeCount() + 1);
        postMapper.updateById(post);
        
        log.info("帖子点赞成功，postId: {}, userId: {}", id, userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void unlikePost(Long id) {
        Long userId = SecurityUtils.getCurrentUserId();

        LambdaQueryWrapper<LikeRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LikeRecord::getUserId, userId)
               .eq(LikeRecord::getTargetType, 2)
               .eq(LikeRecord::getTargetId, id);
        likeRecordMapper.delete(wrapper);

        Post post = postMapper.selectById(id);
        if (post != null && post.getLikeCount() > 0) {
            post.setLikeCount(post.getLikeCount() - 1);
            postMapper.updateById(post);
        }
        
        log.info("帖子取消点赞成功，postId: {}, userId: {}", id, userId);
    }

    @Override
    public boolean isLiked(Long userId, Long id) {
        LambdaQueryWrapper<LikeRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LikeRecord::getUserId, userId)
               .eq(LikeRecord::getTargetType, 2)
               .eq(LikeRecord::getTargetId, id);
        return likeRecordMapper.selectCount(wrapper) > 0;
    }

    @Override
    public Page<PostVO> pageUserPosts(Long userId, int page, int size) {
        Page<Post> postPage = new Page<>(page, size);
        LambdaQueryWrapper<Post> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Post::getUserId, userId)
               .eq(Post::getStatus, 1)
               .orderByDesc(Post::getCreatedAt);
        
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
               .eq(Post::getEssence, 1)
               .orderByDesc(Post::getCreatedAt)
               .last("LIMIT " + limit);
        
        List<Post> posts = postMapper.selectList(wrapper);
        return posts.stream()
                .map(postConverter::toVO)
                .collect(Collectors.toList());
    }
}
