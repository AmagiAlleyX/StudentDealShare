package com.campusecohub.mapper;

import com.campusecohub.entity.Activity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 活动Mapper
 */
@Mapper
public interface ActivityMapper {
    /**
     * 根据ID查询活动
     */
    Activity selectById(Long id);

    /**
     * 查询所有活动
     */
    List<Activity> selectAll();

    /**
     * 根据条件查询活动
     */
    List<Activity> selectByCondition(Map<String, Object> condition);

    /**
     * 插入活动
     */
    int insert(Activity activity);

    /**
     * 更新活动
     */
    int update(Activity activity);

    /**
     * 删除活动
     */
    int delete(Long id);

    /**
     * 根据分类查询活动
     */
    List<Activity> selectByCategory(String category);

    /**
     * 根据来源查询活动
     */
    List<Activity> selectBySource(String source);

    /**
     * 根据学校ID查询活动
     */
    List<Activity> selectBySchoolId(Long schoolId);

    /**
     * 查询学生专属活动
     */
    List<Activity> selectStudentExclusive();
}
