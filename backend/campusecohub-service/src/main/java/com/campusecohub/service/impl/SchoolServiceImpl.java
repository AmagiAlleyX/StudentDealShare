package com.campusecohub.service.impl;

import com.campusecohub.entity.School;
import com.campusecohub.mapper.SchoolMapper;
import com.campusecohub.service.SchoolService;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 学校服务实现类
 */
@Service
public class SchoolServiceImpl implements SchoolService {

    @Resource
    private SchoolMapper schoolMapper;

    @Override
    public List<School> selectAll() {
        return schoolMapper.selectAll();
    }

    @Override
    public School selectById(Long id) {
        return schoolMapper.selectById(id);
    }

    @Override
    public List<School> selectByRegion(String region) {
        return schoolMapper.selectByRegion(region);
    }
}
