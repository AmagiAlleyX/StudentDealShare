package com.campusecohub.service.impl;

import com.campusecohub.dto.ActivityCreateDTO;
import com.campusecohub.dto.ActivityQueryDTO;
import com.campusecohub.dto.ActivityInfoDTO;
import com.campusecohub.dto.ActivityListDTO;
import com.campusecohub.entity.Activity;
import com.campusecohub.entity.Collection;
import com.campusecohub.mapper.ActivityMapper;
import com.campusecohub.mapper.CollectionMapper;
import com.campusecohub.service.ActivityService;
import com.campusecohub.service.SchoolService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 活动服务实现类
 */
@Service
public class ActivityServiceImpl implements ActivityService {

    @Resource
    private ActivityMapper activityMapper;

    @Resource
    private CollectionMapper collectionMapper;

    @Resource
    private SchoolService schoolService;

    @Override
    public ActivityInfoDTO create(ActivityCreateDTO activityCreateDTO) {
        // 创建活动
        Activity activity = new Activity();
        activity.setTitle(activityCreateDTO.getTitle());
        activity.setDescription(activityCreateDTO.getDescription());
        activity.setCategory(activityCreateDTO.getCategory());
        activity.setSource(activityCreateDTO.getSource());
        activity.setStartTime(activityCreateDTO.getStartTime());
        activity.setEndTime(activityCreateDTO.getEndTime());
        activity.setParticipateWay(activityCreateDTO.getParticipateWay());
        activity.setLink(activityCreateDTO.getLink());
        activity.setIsStudentExclusive(activityCreateDTO.getIsStudentExclusive());
        activity.setSchoolId(activityCreateDTO.getSchoolId());

        activityMapper.insert(activity);

        // 转换为DTO返回
        return convertToActivityInfoDTO(activity, null);
    }

    @Override
    public ActivityInfoDTO selectById(Long id, Long userId) {
        Activity activity = activityMapper.selectById(id);
        if (activity == null) {
            throw new RuntimeException("活动不存在");
        }

        return convertToActivityInfoDTO(activity, userId);
    }

    @Override
    public ActivityListDTO selectList(ActivityQueryDTO activityQueryDTO, Long userId) {
        // 设置分页
        PageHelper.startPage(activityQueryDTO.getPageNum(), activityQueryDTO.getPageSize());

        // 构建查询条件
        Map<String, Object> condition = Map.of(
                "category", activityQueryDTO.getCategory(),
                "source", activityQueryDTO.getSource(),
                "schoolId", activityQueryDTO.getSchoolId(),
                "keyword", activityQueryDTO.getKeyword(),
                "isStudentExclusive", activityQueryDTO.getIsStudentExclusive()
        );

        // 查询活动列表
        List<Activity> activities = activityMapper.selectByCondition(condition);
        PageInfo<Activity> pageInfo = new PageInfo<>(activities);

        // 转换为DTO列表
        List<ActivityInfoDTO> activityInfoDTOs = activities.stream()
                .map(activity -> convertToActivityInfoDTO(activity, userId))
                .collect(Collectors.toList());

        // 构建返回结果
        ActivityListDTO activityListDTO = new ActivityListDTO();
        activityListDTO.setActivities(activityInfoDTOs);
        activityListDTO.setTotal(pageInfo.getTotal());
        activityListDTO.setPageNum(pageInfo.getPageNum());
        activityListDTO.setPageSize(pageInfo.getPageSize());
        activityListDTO.setPages(pageInfo.getPages());

        return activityListDTO;
    }

    @Override
    public boolean collect(Long userId, Long activityId) {
        // 检查活动是否存在
        if (activityMapper.selectById(activityId) == null) {
            throw new RuntimeException("活动不存在");
        }

        // 检查是否已经收藏
        Collection collection = collectionMapper.selectByUserIdAndActivityId(userId, activityId);
        if (collection != null) {
            throw new RuntimeException("已经收藏过该活动");
        }

        // 创建收藏
        collection = new Collection();
        collection.setUserId(userId);
        collection.setActivityId(activityId);

        return collectionMapper.insert(collection) > 0;
    }

    @Override
    public boolean uncollect(Long userId, Long activityId) {
        // 检查是否已经收藏
        Collection collection = collectionMapper.selectByUserIdAndActivityId(userId, activityId);
        if (collection == null) {
            throw new RuntimeException("未收藏该活动");
        }

        // 删除收藏
        return collectionMapper.deleteByUserIdAndActivityId(userId, activityId) > 0;
    }

    @Override
    public ActivityListDTO selectCollections(Long userId, Integer pageNum, Integer pageSize) {
        // 设置分页
        PageHelper.startPage(pageNum, pageSize);

        // 查询收藏列表
        List<Collection> collections = collectionMapper.selectByUserId(userId);
        PageInfo<Collection> pageInfo = new PageInfo<>(collections);

        // 转换为活动DTO列表
        List<ActivityInfoDTO> activityInfoDTOs = collections.stream()
                .map(collection -> {
                    Activity activity = activityMapper.selectById(collection.getActivityId());
                    return activity != null ? convertToActivityInfoDTO(activity, userId) : null;
                })
                .filter(java.util.Objects::nonNull)
                .collect(Collectors.toList());

        // 构建返回结果
        ActivityListDTO activityListDTO = new ActivityListDTO();
        activityListDTO.setActivities(activityInfoDTOs);
        activityListDTO.setTotal(pageInfo.getTotal());
        activityListDTO.setPageNum(pageInfo.getPageNum());
        activityListDTO.setPageSize(pageInfo.getPageSize());
        activityListDTO.setPages(pageInfo.getPages());

        return activityListDTO;
    }

    @Override
    public ActivityListDTO selectByCategory(String category, Long userId, Integer pageNum, Integer pageSize) {
        // 设置分页
        PageHelper.startPage(pageNum, pageSize);

        // 查询活动列表
        List<Activity> activities = activityMapper.selectByCategory(category);
        PageInfo<Activity> pageInfo = new PageInfo<>(activities);

        // 转换为DTO列表
        List<ActivityInfoDTO> activityInfoDTOs = activities.stream()
                .map(activity -> convertToActivityInfoDTO(activity, userId))
                .collect(Collectors.toList());

        // 构建返回结果
        ActivityListDTO activityListDTO = new ActivityListDTO();
        activityListDTO.setActivities(activityInfoDTOs);
        activityListDTO.setTotal(pageInfo.getTotal());
        activityListDTO.setPageNum(pageInfo.getPageNum());
        activityListDTO.setPageSize(pageInfo.getPageSize());
        activityListDTO.setPages(pageInfo.getPages());

        return activityListDTO;
    }

    @Override
    public ActivityListDTO selectBySource(String source, Long userId, Integer pageNum, Integer pageSize) {
        // 设置分页
        PageHelper.startPage(pageNum, pageSize);

        // 查询活动列表
        List<Activity> activities = activityMapper.selectBySource(source);
        PageInfo<Activity> pageInfo = new PageInfo<>(activities);

        // 转换为DTO列表
        List<ActivityInfoDTO> activityInfoDTOs = activities.stream()
                .map(activity -> convertToActivityInfoDTO(activity, userId))
                .collect(Collectors.toList());

        // 构建返回结果
        ActivityListDTO activityListDTO = new ActivityListDTO();
        activityListDTO.setActivities(activityInfoDTOs);
        activityListDTO.setTotal(pageInfo.getTotal());
        activityListDTO.setPageNum(pageInfo.getPageNum());
        activityListDTO.setPageSize(pageInfo.getPageSize());
        activityListDTO.setPages(pageInfo.getPages());

        return activityListDTO;
    }

    @Override
    public ActivityListDTO selectBySchoolId(Long schoolId, Long userId, Integer pageNum, Integer pageSize) {
        // 设置分页
        PageHelper.startPage(pageNum, pageSize);

        // 查询活动列表
        List<Activity> activities = activityMapper.selectBySchoolId(schoolId);
        PageInfo<Activity> pageInfo = new PageInfo<>(activities);

        // 转换为DTO列表
        List<ActivityInfoDTO> activityInfoDTOs = activities.stream()
                .map(activity -> convertToActivityInfoDTO(activity, userId))
                .collect(Collectors.toList());

        // 构建返回结果
        ActivityListDTO activityListDTO = new ActivityListDTO();
        activityListDTO.setActivities(activityInfoDTOs);
        activityListDTO.setTotal(pageInfo.getTotal());
        activityListDTO.setPageNum(pageInfo.getPageNum());
        activityListDTO.setPageSize(pageInfo.getPageSize());
        activityListDTO.setPages(pageInfo.getPages());

        return activityListDTO;
    }

    @Override
    public ActivityListDTO selectStudentExclusive(Long userId, Integer pageNum, Integer pageSize) {
        // 设置分页
        PageHelper.startPage(pageNum, pageSize);

        // 查询活动列表
        List<Activity> activities = activityMapper.selectStudentExclusive();
        PageInfo<Activity> pageInfo = new PageInfo<>(activities);

        // 转换为DTO列表
        List<ActivityInfoDTO> activityInfoDTOs = activities.stream()
                .map(activity -> convertToActivityInfoDTO(activity, userId))
                .collect(Collectors.toList());

        // 构建返回结果
        ActivityListDTO activityListDTO = new ActivityListDTO();
        activityListDTO.setActivities(activityInfoDTOs);
        activityListDTO.setTotal(pageInfo.getTotal());
        activityListDTO.setPageNum(pageInfo.getPageNum());
        activityListDTO.setPageSize(pageInfo.getPageSize());
        activityListDTO.setPages(pageInfo.getPages());

        return activityListDTO;
    }

    /**
     * 将Activity实体转换为ActivityInfoDTO
     */
    private ActivityInfoDTO convertToActivityInfoDTO(Activity activity, Long userId) {
        ActivityInfoDTO activityInfoDTO = new ActivityInfoDTO();
        activityInfoDTO.setId(activity.getId());
        activityInfoDTO.setTitle(activity.getTitle());
        activityInfoDTO.setDescription(activity.getDescription());
        activityInfoDTO.setCategory(activity.getCategory());
        activityInfoDTO.setSource(activity.getSource());
        activityInfoDTO.setStartTime(activity.getStartTime());
        activityInfoDTO.setEndTime(activity.getEndTime());
        activityInfoDTO.setParticipateWay(activity.getParticipateWay());
        activityInfoDTO.setLink(activity.getLink());
        activityInfoDTO.setIsStudentExclusive(activity.getIsStudentExclusive());
        activityInfoDTO.setSchoolId(activity.getSchoolId());
        activityInfoDTO.setCreateTime(activity.getCreateTime());

        // 计算剩余时间
        long remainingTime = activity.getEndTime().getTime() - new Date().getTime();
        activityInfoDTO.setRemainingTime(remainingTime > 0 ? remainingTime : 0);

        // 查询学校名称
        if (activity.getSchoolId() != null) {
            try {
                activityInfoDTO.setSchoolName(schoolService.selectById(activity.getSchoolId()).getName());
            } catch (Exception e) {
                // 学校不存在，忽略
            }
        }

        // 查询是否收藏
        if (userId != null) {
            Collection collection = collectionMapper.selectByUserIdAndActivityId(userId, activity.getId());
            activityInfoDTO.setIsCollected(collection != null);
        }

        return activityInfoDTO;
    }
}
