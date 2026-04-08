package com.campusecohub.mapper;

import com.campusecohub.entity.Like;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Delete;

import java.util.List;

/**
 * 点赞Mapper
 */
@Mapper
public interface LikeMapper {
    /**
     * 根据ID查询点赞
     */
    @Select("SELECT * FROM `like` WHERE id = #{id}")
    Like selectById(Long id);

    /**
     * 根据用户ID和帖子ID查询点赞
     */
    @Select("SELECT * FROM `like` WHERE user_id = #{userId} AND post_id = #{postId}")
    Like selectByUserIdAndPostId(Long userId, Long postId);

    /**
     * 根据用户ID查询点赞
     */
    @Select("SELECT * FROM `like` WHERE user_id = #{userId} ORDER BY create_time DESC")
    List<Like> selectByUserId(Long userId);

    /**
     * 根据帖子ID查询点赞数
     */
    @Select("SELECT COUNT(*) FROM `like` WHERE post_id = #{postId}")
    Integer countByPostId(Long postId);

    /**
     * 插入点赞
     */
    @Insert("INSERT INTO `like` (user_id, post_id, create_time) VALUES (#{userId}, #{postId}, NOW())")
    int insert(Like like);

    /**
     * 删除点赞
     */
    @Delete("DELETE FROM `like` WHERE id = #{id}")
    int delete(Long id);

    /**
     * 根据用户ID和帖子ID删除点赞
     */
    @Delete("DELETE FROM `like` WHERE user_id = #{userId} AND post_id = #{postId}")
    int deleteByUserIdAndPostId(Long userId, Long postId);

    /**
     * 根据用户ID删除点赞
     */
    @Delete("DELETE FROM `like` WHERE user_id = #{userId}")
    int deleteByUserId(Long userId);

    /**
     * 根据帖子ID删除点赞
     */
    @Delete("DELETE FROM `like` WHERE post_id = #{postId}")
    int deleteByPostId(Long postId);
}
