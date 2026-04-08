package com.campusecohub.controller;

import com.campusecohub.dto.ReminderCreateDTO;
import com.campusecohub.dto.ReminderInfoDTO;
import com.campusecohub.dto.ResponseDTO;
import com.campusecohub.service.ReminderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.*;


import java.util.List;

/**
 * 提醒控制器
 */
@RestController
@RequestMapping("/api/reminder")
@Api(tags = "提醒管理")
public class ReminderController {

    @Resource
    private ReminderService reminderService;

    /**
     * 创建提醒
     */
    @PostMapping("/create")
    @ApiOperation("创建提醒")
    public ResponseDTO<ReminderInfoDTO> create(@RequestBody ReminderCreateDTO reminderCreateDTO, @RequestParam Long userId) {
        ReminderInfoDTO reminderInfoDTO = reminderService.create(reminderCreateDTO, userId);
        return ResponseDTO.success(reminderInfoDTO);
    }

    /**
     * 获取提醒详情
     */
    @GetMapping("/detail/{id}")
    @ApiOperation("获取提醒详情")
    public ResponseDTO<ReminderInfoDTO> getDetail(@PathVariable Long id) {
        ReminderInfoDTO reminderInfoDTO = reminderService.selectById(id);
        return ResponseDTO.success(reminderInfoDTO);
    }

    /**
     * 获取用户的提醒列表
     */
    @GetMapping("/list")
    @ApiOperation("获取用户的提醒列表")
    public ResponseDTO<List<ReminderInfoDTO>> getList(@RequestParam Long userId) {
        List<ReminderInfoDTO> reminderInfoDTOs = reminderService.selectByUserId(userId);
        return ResponseDTO.success(reminderInfoDTOs);
    }

    /**
     * 删除提醒
     */
    @PostMapping("/delete")
    @ApiOperation("删除提醒")
    public ResponseDTO<Boolean> delete(@RequestParam Long id, @RequestParam Long userId) {
        boolean result = reminderService.delete(id, userId);
        return ResponseDTO.success(result);
    }
}
