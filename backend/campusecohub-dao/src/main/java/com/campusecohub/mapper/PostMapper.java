package com.campusecohub.mapper;

import com.campusecohub.entity.Post;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Delete;

import java.util.List;
import java.util.Map;

/**
 * 帖子Mapper
 */
@Mapper
public interface PostMapper {
    /**
     * 根据ID查询帖子
     */
    @Select("SELECT * FROM post WHERE id = #{id}")
    Post selectById(Long id);

    /**
     * 查询所有帖子
     */
    @Select("SELECT * FROM post ORDER BY create_time DESC")
    List<Post> selectAll();

    /**
     * 根据条件查询帖子
     */
    List<Post> selectByCondition(Map<String, Object> condition);

    /**
     * 根据用户ID查询帖子
     */
    @Select("SELECT * FROM post WHERE user_id = #{userId} ORDER BY create_time DESC")
    List<Post> selectByUserId(Long userId);

    /**
     * 插入帖子
     */
    @Insert("INSERT INTO post (title, content, images, link, user_id, like_count, comment_count, create_time, update_time) VALUES (#{title}, #{content}, #{images}, #{link}, #{userId}, 0, 0, NOW(), NOW())")
    int insert(Post post);

    /**
     * 更新帖子
     */
    @Update("UPDATE post SET title = #{title}, content = #{content}, images = #{images}, link = #{link}, update_time = NOW() WHERE id = #{id}")
    int update(Post post);

    /**
     * 删除帖子
     */
    @Delete("DELETE FROM post WHERE id = #{id}")
    int delete(Long id);

    /**
     * 更新点赞数
     */
    @Update("UPDATE post SET like_count = #{likeCount}, update_time = NOW() WHERE id = #{id}")
    int updateLikeCount(Long id, Integer likeCount);

    /**
     * 更新评论数
     */
    @Update("UPDATE post SET comment_count = #{commentCount}, update_time = NOW() WHERE id = #{id}")
    int updateCommentCount(Long id, Integer commentCount);
}
