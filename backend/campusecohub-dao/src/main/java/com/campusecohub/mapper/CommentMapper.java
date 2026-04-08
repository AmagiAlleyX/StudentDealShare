package com.campusecohub.mapper;

import com.campusecohub.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Delete;

import java.util.List;

/**
 * 评论Mapper
 */
@Mapper
public interface CommentMapper {
    /**
     * 根据ID查询评论
     */
    @Select("SELECT * FROM comment WHERE id = #{id}")
    Comment selectById(Long id);

    /**
     * 根据帖子ID查询评论
     */
    @Select("SELECT * FROM comment WHERE post_id = #{postId} ORDER BY create_time DESC")
    List<Comment> selectByPostId(Long postId);

    /**
     * 根据用户ID查询评论
     */
    @Select("SELECT * FROM comment WHERE user_id = #{userId} ORDER BY create_time DESC")
    List<Comment> selectByUserId(Long userId);

    /**
     * 插入评论
     */
    @Insert("INSERT INTO comment (post_id, user_id, content, create_time) VALUES (#{postId}, #{userId}, #{content}, NOW())")
    int insert(Comment comment);

    /**
     * 删除评论
     */
    @Delete("DELETE FROM comment WHERE id = #{id}")
    int delete(Long id);

    /**
     * 根据帖子ID删除评论
     */
    @Delete("DELETE FROM comment WHERE post_id = #{postId}")
    int deleteByPostId(Long postId);

    /**
     * 根据用户ID删除评论
     */
    @Delete("DELETE FROM comment WHERE user_id = #{userId}")
    int deleteByUserId(Long userId);
}
