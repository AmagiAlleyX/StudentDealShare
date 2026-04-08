package com.campusecohub.service;

import com.campusecohub.dto.ActivityCreateDTO;
import com.campusecohub.dto.ActivityQueryDTO;
import com.campusecohub.dto.ActivityInfoDTO;
import com.campusecohub.dto.ActivityListDTO;

/**
 * 活动服务接口
 */
public interface ActivityService {
    /**
     * 创建活动
     */
    ActivityInfoDTO create(ActivityCreateDTO activityCreateDTO);

    /**
     * 根据ID查询活动
     */
    ActivityInfoDTO selectById(Long id, Long userId);

    /**
     * 查询活动列表
     */
    ActivityListDTO selectList(ActivityQueryDTO activityQueryDTO, Long userId);

    /**
     * 收藏活动
     */
    boolean collect(Long userId, Long activityId);

    /**
     * 取消收藏
     */
    boolean uncollect(Long userId, Long activityId);

    /**
     * 查询收藏的活动列表
     */
    ActivityListDTO selectCollections(Long userId, Integer pageNum, Integer pageSize);

    /**
     * 根据分类查询活动
     */
    ActivityListDTO selectByCategory(String category, Long userId, Integer pageNum, Integer pageSize);

    /**
     * 根据来源查询活动
     */
    ActivityListDTO selectBySource(String source, Long userId, Integer pageNum, Integer pageSize);

    /**
     * 根据学校ID查询活动
     */
    ActivityListDTO selectBySchoolId(Long schoolId, Long userId, Integer pageNum, Integer pageSize);

    /**
     * 查询学生专属活动
     */
    ActivityListDTO selectStudentExclusive(Long userId, Integer pageNum, Integer pageSize);
}
