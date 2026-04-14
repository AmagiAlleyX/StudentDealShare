package com.student.dealshare.service.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.student.dealshare.model.dto.CommentCreateDTO;
import com.student.dealshare.model.dto.PostCreateDTO;
import com.student.dealshare.model.dto.PostUpdateDTO;
import com.student.dealshare.model.vo.CommentVO;
import com.student.dealshare.model.vo.PostVO;

import java.util.List;

public interface PostService {

    PostVO createPost(PostCreateDTO dto);

    PostVO getPostById(Long postId);

    Page<PostVO> pagePosts(int page, int size);

    void updatePost(PostUpdateDTO dto);

    void deletePost(Long postId);

    void incrementViewCount(Long postId);

    void likePost(Long postId);

    void unlikePost(Long postId);

    boolean isLiked(Long userId, Long postId);

    Page<PostVO> pageUserPosts(Long userId, int page, int size);

    List<PostVO> listHotPosts(int limit);

    List<PostVO> listEssencePosts(int limit);
}
