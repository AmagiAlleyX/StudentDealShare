package com.campusecohub.controller;

import com.campusecohub.dto.ActivityCreateDTO;
import com.campusecohub.dto.ActivityQueryDTO;
import com.campusecohub.dto.ActivityInfoDTO;
import com.campusecohub.dto.ActivityListDTO;
import com.campusecohub.dto.ResponseDTO;
import com.campusecohub.service.ActivityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.*;


/**
 * 活动控制器
 */
@RestController
@RequestMapping("/api/activity")
@Api(tags = "活动管理")
public class ActivityController {

    @Resource
    private ActivityService activityService;

    /**
     * 创建活动
     */
    @PostMapping("/create")
    @ApiOperation("创建活动")
    public ResponseDTO<ActivityInfoDTO> create(@RequestBody ActivityCreateDTO activityCreateDTO) {
        ActivityInfoDTO activityInfoDTO = activityService.create(activityCreateDTO);
        return ResponseDTO.success(activityInfoDTO);
    }

    /**
     * 获取活动详情
     */
    @GetMapping("/detail/{id}")
    @ApiOperation("获取活动详情")
    public ResponseDTO<ActivityInfoDTO> getDetail(@PathVariable Long id, @RequestParam(required = false) Long userId) {
        ActivityInfoDTO activityInfoDTO = activityService.selectById(id, userId);
        return ResponseDTO.success(activityInfoDTO);
    }

    /**
     * 获取活动列表
     */
    @GetMapping("/list")
    @ApiOperation("获取活动列表")
    public ResponseDTO<ActivityListDTO> getList(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String source,
            @RequestParam(required = false) Long schoolId,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer isStudentExclusive,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) Long userId
    ) {
        ActivityQueryDTO activityQueryDTO = new ActivityQueryDTO();
        activityQueryDTO.setCategory(category);
        activityQueryDTO.setSource(source);
        activityQueryDTO.setSchoolId(schoolId);
        activityQueryDTO.setKeyword(keyword);
        activityQueryDTO.setIsStudentExclusive(isStudentExclusive);
        activityQueryDTO.setPageNum(pageNum);
        activityQueryDTO.setPageSize(pageSize);

        ActivityListDTO activityListDTO = activityService.selectList(activityQueryDTO, userId);
        return ResponseDTO.success(activityListDTO);
    }

    /**
     * 收藏活动
     */
    @PostMapping("/collect")
    @ApiOperation("收藏活动")
    public ResponseDTO<Boolean> collect(@RequestParam Long userId, @RequestParam Long activityId) {
        boolean result = activityService.collect(userId, activityId);
        return ResponseDTO.success(result);
    }

    /**
     * 取消收藏
     */
    @PostMapping("/uncollect")
    @ApiOperation("取消收藏")
    public ResponseDTO<Boolean> uncollect(@RequestParam Long userId, @RequestParam Long activityId) {
        boolean result = activityService.uncollect(userId, activityId);
        return ResponseDTO.success(result);
    }

    /**
     * 获取收藏列表
     */
    @GetMapping("/collections")
    @ApiOperation("获取收藏列表")
    public ResponseDTO<ActivityListDTO> getCollections(
            @RequestParam Long userId,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize
    ) {
        ActivityListDTO activityListDTO = activityService.selectCollections(userId, pageNum, pageSize);
        return ResponseDTO.success(activityListDTO);
    }

    /**
     * 根据分类获取活动
     */
    @GetMapping("/category/{category}")
    @ApiOperation("根据分类获取活动")
    public ResponseDTO<ActivityListDTO> getByCategory(
            @PathVariable String category,
            @RequestParam(required = false) Long userId,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize
    ) {
        ActivityListDTO activityListDTO = activityService.selectByCategory(category, userId, pageNum, pageSize);
        return ResponseDTO.success(activityListDTO);
    }

    /**
     * 根据来源获取活动
     */
    @GetMapping("/source/{source}")
    @ApiOperation("根据来源获取活动")
    public ResponseDTO<ActivityListDTO> getBySource(
            @PathVariable String source,
            @RequestParam(required = false) Long userId,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize
    ) {
        ActivityListDTO activityListDTO = activityService.selectBySource(source, userId, pageNum, pageSize);
        return ResponseDTO.success(activityListDTO);
    }

    /**
     * 根据学校获取活动
     */
    @GetMapping("/school/{schoolId}")
    @ApiOperation("根据学校获取活动")
    public ResponseDTO<ActivityListDTO> getBySchoolId(
            @PathVariable Long schoolId,
            @RequestParam(required = false) Long userId,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize
    ) {
        ActivityListDTO activityListDTO = activityService.selectBySchoolId(schoolId, userId, pageNum, pageSize);
        return ResponseDTO.success(activityListDTO);
    }

    /**
     * 获取学生专属活动
     */
    @GetMapping("/student/exclusive")
    @ApiOperation("获取学生专属活动")
    public ResponseDTO<ActivityListDTO> getStudentExclusive(
            @RequestParam(required = false) Long userId,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize
    ) {
        ActivityListDTO activityListDTO = activityService.selectStudentExclusive(userId, pageNum, pageSize);
        return ResponseDTO.success(activityListDTO);
    }
}
