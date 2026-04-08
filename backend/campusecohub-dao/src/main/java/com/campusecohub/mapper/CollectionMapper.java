package com.campusecohub.mapper;

import com.campusecohub.entity.Collection;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 收藏Mapper
 */
@Mapper
public interface CollectionMapper {
    /**
     * 根据ID查询收藏
     */
    Collection selectById(Long id);

    /**
     * 根据用户ID和活动ID查询收藏
     */
    Collection selectByUserIdAndActivityId(Long userId, Long activityId);

    /**
     * 根据用户ID查询收藏
     */
    List<Collection> selectByUserId(Long userId);

    /**
     * 插入收藏
     */
    int insert(Collection collection);

    /**
     * 删除收藏
     */
    int delete(Long id);

    /**
     * 根据用户ID和活动ID删除收藏
     */
    int deleteByUserIdAndActivityId(Long userId, Long activityId);

    /**
     * 根据用户ID删除收藏
     */
    int deleteByUserId(Long userId);

    /**
     * 根据活动ID删除收藏
     */
    int deleteByActivityId(Long activityId);
}
