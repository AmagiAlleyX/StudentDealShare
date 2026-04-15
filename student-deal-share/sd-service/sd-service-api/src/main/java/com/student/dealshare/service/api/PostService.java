package com.student.dealshare.service.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.student.dealshare.model.dto.PostCreateDTO;
import com.student.dealshare.model.dto.PostUpdateDTO;
import com.student.dealshare.model.vo.PostVO;
import java.util.List;

/**
 * 帖子服务接口
 */
public interface PostService {

    /**
     * 发布帖子
     * @param dto 创建信息
     * @return 帖子信息
     */
    PostVO createPost(PostCreateDTO dto);

    /**
     * 获取帖子详情
     * @param postId 帖子 ID
     * @return 帖子详情
     */
    PostVO getPostById(Long postId);

    /**
     * 分页查询帖子
     * @param page 页码
     * @param size 每页数量
     * @return 帖子列表
     */
    Page<PostVO> pagePosts(int page, int size);

    /**
     * 更新帖子
     * @param dto 更新信息
     */
    void updatePost(PostUpdateDTO dto);

    /**
     * 删除帖子
     * @param postId 帖子 ID
     */
    void deletePost(Long postId);

    /**
     * 增加浏览量
     * @param postId 帖子 ID
     */
    void incrementViewCount(Long postId);

    /**
     * 点赞帖子
     * @param postId 帖子 ID
     */
    void likePost(Long postId);

    /**
     * 取消点赞
     * @param postId 帖子 ID
     */
    void unlikePost(Long postId);

    /**
     * 检查是否已点赞
     * @param userId 用户 ID
     * @param postId 帖子 ID
     * @return true-已点赞，false-未点赞
     */
    boolean isLiked(Long userId, Long postId);

    /**
     * 查询用户发布的帖子
     * @param userId 用户 ID
     * @param page 页码
     * @param size 每页数量
     * @return 帖子列表
     */
    Page<PostVO> pageUserPosts(Long userId, int page, int size);

    /**
     * 热门帖子列表
     * @param limit 数量限制
     * @return 帖子列表
     */
    List<PostVO> listHotPosts(int limit);

    /**
     * 精华帖子列表
     * @param limit 数量限制
     * @return 帖子列表
     */
    List<PostVO> listEssencePosts(int limit);
}
