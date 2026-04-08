package com.campusecohub.service;

import com.campusecohub.entity.School;

import java.util.List;

/**
 * 学校服务接口
 */
public interface SchoolService {
    /**
     * 查询所有学校
     */
    List<School> selectAll();

    /**
     * 根据ID查询学校
     */
    School selectById(Long id);

    /**
     * 根据地区查询学校
     */
    List<School> selectByRegion(String region);
}
