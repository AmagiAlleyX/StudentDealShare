package com.campusecohub.service.impl;

import com.campusecohub.dto.PostCreateDTO;
import com.campusecohub.dto.PostQueryDTO;
import com.campusecohub.dto.PostInfoDTO;
import com.campusecohub.dto.PostListDTO;
import com.campusecohub.dto.CommentCreateDTO;
import com.campusecohub.dto.CommentInfoDTO;
import com.campusecohub.entity.Post;
import com.campusecohub.entity.Comment;
import com.campusecohub.entity.Like;
import com.campusecohub.mapper.PostMapper;
import com.campusecohub.mapper.CommentMapper;
import com.campusecohub.mapper.LikeMapper;
import com.campusecohub.mapper.UserMapper;
import com.campusecohub.service.PostService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 帖子服务实现类
 */
@Service
public class PostServiceImpl implements PostService {

    @Resource
    private PostMapper postMapper;

    @Resource
    private CommentMapper commentMapper;

    @Resource
    private LikeMapper likeMapper;

    @Resource
    private UserMapper userMapper;

    @Override
    public PostInfoDTO create(PostCreateDTO postCreateDTO, Long userId) {
        // 创建帖子
        Post post = new Post();
        post.setTitle(postCreateDTO.getTitle());
        post.setContent(postCreateDTO.getContent());
        post.setImages(postCreateDTO.getImages());
        post.setLink(postCreateDTO.getLink());
        post.setUserId(userId);

        postMapper.insert(post);

        // 转换为DTO返回
        return convertToPostInfoDTO(post, userId);
    }

    @Override
    public PostInfoDTO selectById(Long id, Long userId) {
        Post post = postMapper.selectById(id);
        if (post == null) {
            throw new RuntimeException("帖子不存在");
        }

        PostInfoDTO postInfoDTO = convertToPostInfoDTO(post, userId);

        // 查询评论列表
        List<Comment> comments = commentMapper.selectByPostId(id);
        List<CommentInfoDTO> commentInfoDTOs = comments.stream()
                .map(this::convertToCommentInfoDTO)
                .collect(Collectors.toList());
        postInfoDTO.setComments(commentInfoDTOs);

        return postInfoDTO;
    }

    @Override
    public PostListDTO selectList(PostQueryDTO postQueryDTO, Long userId) {
        // 设置分页
        PageHelper.startPage(postQueryDTO.getPageNum(), postQueryDTO.getPageSize());

        // 构建查询条件
        Map<String, Object> condition = Map.of(
                "keyword", postQueryDTO.getKeyword()
        );

        // 查询帖子列表
        List<Post> posts = postMapper.selectByCondition(condition);
        PageInfo<Post> pageInfo = new PageInfo<>(posts);

        // 转换为DTO列表
        List<PostInfoDTO> postInfoDTOs = posts.stream()
                .map(post -> convertToPostInfoDTO(post, userId))
                .collect(Collectors.toList());

        // 构建返回结果
        PostListDTO postListDTO = new PostListDTO();
        postListDTO.setPosts(postInfoDTOs);
        postListDTO.setTotal(pageInfo.getTotal());
        postListDTO.setPageNum(pageInfo.getPageNum());
        postListDTO.setPageSize(pageInfo.getPageSize());
        postListDTO.setPages(pageInfo.getPages());

        return postListDTO;
    }

    @Override
    public PostListDTO selectByUserId(Long userId, Integer pageNum, Integer pageSize) {
        // 设置分页
        PageHelper.startPage(pageNum, pageSize);

        // 查询帖子列表
        List<Post> posts = postMapper.selectByUserId(userId);
        PageInfo<Post> pageInfo = new PageInfo<>(posts);

        // 转换为DTO列表
        List<PostInfoDTO> postInfoDTOs = posts.stream()
                .map(post -> convertToPostInfoDTO(post, userId))
                .collect(Collectors.toList());

        // 构建返回结果
        PostListDTO postListDTO = new PostListDTO();
        postListDTO.setPosts(postInfoDTOs);
        postListDTO.setTotal(pageInfo.getTotal());
        postListDTO.setPageNum(pageInfo.getPageNum());
        postListDTO.setPageSize(pageInfo.getPageSize());
        postListDTO.setPages(pageInfo.getPages());

        return postListDTO;
    }

    @Override
    public boolean like(Long userId, Long postId) {
        // 检查帖子是否存在
        if (postMapper.selectById(postId) == null) {
            throw new RuntimeException("帖子不存在");
        }

        // 检查是否已经点赞
        Like like = likeMapper.selectByUserIdAndPostId(userId, postId);
        if (like != null) {
            throw new RuntimeException("已经点赞过该帖子");
        }

        // 创建点赞
        like = new Like();
        like.setUserId(userId);
        like.setPostId(postId);

        int result = likeMapper.insert(like);
        if (result > 0) {
            // 更新帖子点赞数
            Integer likeCount = likeMapper.countByPostId(postId);
            postMapper.updateLikeCount(postId, likeCount);
            return true;
        }

        return false;
    }

    @Override
    public boolean unlike(Long userId, Long postId) {
        // 检查是否已经点赞
        Like like = likeMapper.selectByUserIdAndPostId(userId, postId);
        if (like == null) {
            throw new RuntimeException("未点赞该帖子");
        }

        // 删除点赞
        int result = likeMapper.deleteByUserIdAndPostId(userId, postId);
        if (result > 0) {
            // 更新帖子点赞数
            Integer likeCount = likeMapper.countByPostId(postId);
            postMapper.updateLikeCount(postId, likeCount);
            return true;
        }

        return false;
    }

    @Override
    public CommentInfoDTO comment(CommentCreateDTO commentCreateDTO, Long userId) {
        // 检查帖子是否存在
        if (postMapper.selectById(commentCreateDTO.getPostId()) == null) {
            throw new RuntimeException("帖子不存在");
        }

        // 创建评论
        Comment comment = new Comment();
        comment.setPostId(commentCreateDTO.getPostId());
        comment.setUserId(userId);
        comment.setContent(commentCreateDTO.getContent());

        int result = commentMapper.insert(comment);
        if (result > 0) {
            // 更新帖子评论数
            List<Comment> comments = commentMapper.selectByPostId(commentCreateDTO.getPostId());
            postMapper.updateCommentCount(commentCreateDTO.getPostId(), comments.size());

            // 转换为DTO返回
            return convertToCommentInfoDTO(comment);
        }

        throw new RuntimeException("评论失败");
    }

    @Override
    public List<CommentInfoDTO> selectCommentsByPostId(Long postId) {
        // 检查帖子是否存在
        if (postMapper.selectById(postId) == null) {
            throw new RuntimeException("帖子不存在");
        }

        // 查询评论列表
        List<Comment> comments = commentMapper.selectByPostId(postId);

        // 转换为DTO列表
        return comments.stream()
                .map(this::convertToCommentInfoDTO)
                .collect(Collectors.toList());
    }

    /**
     * 将Post实体转换为PostInfoDTO
     */
    private PostInfoDTO convertToPostInfoDTO(Post post, Long userId) {
        PostInfoDTO postInfoDTO = new PostInfoDTO();
        postInfoDTO.setId(post.getId());
        postInfoDTO.setTitle(post.getTitle());
        postInfoDTO.setContent(post.getContent());
        postInfoDTO.setImages(post.getImages());
        postInfoDTO.setLink(post.getLink());
        postInfoDTO.setUserId(post.getUserId());
        postInfoDTO.setLikeCount(post.getLikeCount());
        postInfoDTO.setCommentCount(post.getCommentCount());
        postInfoDTO.setCreateTime(post.getCreateTime());

        // 查询发布用户名
        try {
            postInfoDTO.setUsername(userMapper.selectById(post.getUserId()).getUsername());
        } catch (Exception e) {
            // 用户不存在，忽略
        }

        // 查询是否点赞
        if (userId != null) {
            Like like = likeMapper.selectByUserIdAndPostId(userId, post.getId());
            postInfoDTO.setIsLiked(like != null);
        }

        return postInfoDTO;
    }

    /**
     * 将Comment实体转换为CommentInfoDTO
     */
    private CommentInfoDTO convertToCommentInfoDTO(Comment comment) {
        CommentInfoDTO commentInfoDTO = new CommentInfoDTO();
        commentInfoDTO.setId(comment.getId());
        commentInfoDTO.setPostId(comment.getPostId());
        commentInfoDTO.setUserId(comment.getUserId());
        commentInfoDTO.setContent(comment.getContent());
        commentInfoDTO.setCreateTime(comment.getCreateTime());

        // 查询评论用户名
        try {
            commentInfoDTO.setUsername(userMapper.selectById(comment.getUserId()).getUsername());
        } catch (Exception e) {
            // 用户不存在，忽略
        }

        return commentInfoDTO;
    }
}
