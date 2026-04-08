package com.campusecohub.mapper;

import com.campusecohub.entity.School;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 学校Mapper
 */
@Mapper
public interface SchoolMapper {
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
    @Select("SELECT * FROM school WHERE region = #{region}")
    List<School> selectByRegion(String region);
}
