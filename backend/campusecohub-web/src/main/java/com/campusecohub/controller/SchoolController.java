package com.campusecohub.controller;

import com.campusecohub.dto.ResponseDTO;
import com.campusecohub.entity.School;
import com.campusecohub.service.SchoolService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 学校控制器
 */
@RestController
@RequestMapping("/api/school")
@Api(tags = "学校管理")
public class SchoolController {

    @Resource
    private SchoolService schoolService;

    /**
     * 获取所有学校列表
     */
    @GetMapping("/list")
    @ApiOperation("获取所有学校列表")
    public ResponseDTO<List<School>> getList() {
        List<School> schoolInfoDTOs = schoolService.selectAll();
        return ResponseDTO.success(schoolInfoDTOs);
    }
}
