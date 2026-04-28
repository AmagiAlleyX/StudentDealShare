package com.student.dealshare.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.student.dealshare.annotation.OperationLog;
import com.student.dealshare.common.result.R;
import com.student.dealshare.model.dto.DealCreateDTO;
import com.student.dealshare.model.dto.DealQueryDTO;
import com.student.dealshare.model.dto.DealUpdateDTO;
import com.student.dealshare.model.vo.DealVO;
import com.student.dealshare.security.SecurityUtils;
import com.student.dealshare.service.api.DealService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 优惠信息控制器
 */
@Tag(name = "优惠信息管理")
@RestController
@RequestMapping("/api/deal")
@RequiredArgsConstructor
public class DealController {
    private final DealService dealService;

    @Operation(summary = "创建优惠信息")
    @PostMapping("/create")
    @OperationLog(module = "优惠管理", type = "CREATE", description = "创建优惠")
    public R<DealVO> createDeal(@Valid @RequestBody DealCreateDTO dto) {
        DealVO result = dealService.createDeal(dto);
        return R.ok(result);
    }

    @Operation(summary = "获取优惠详情")
    @GetMapping("/{id}")
    public R<DealVO> getDealById(@PathVariable("id") Long id) {
        dealService.incrementViewCount(id);
        DealVO result = dealService.getDealById(id);
        return R.ok(result);
    }

    @Operation(summary = "分页查询优惠")
    @GetMapping("/page")
    public R<Page<DealVO>> pageDeals(DealQueryDTO queryDTO) {
        Page<DealVO> dealPage = dealService.pageDeals(queryDTO);
        return R.ok(dealPage);
    }

    @Operation(summary = "热门优惠列表")
    @GetMapping("/hot")
    public R<List<DealVO>> listHotDeals(@RequestParam(value="limit", defaultValue = "10") int limit) {
        List<DealVO> list = dealService.listHotDeals(limit);
        return R.ok(list);
    }

    @Operation(summary = "更新优惠信息")
    @PutMapping("/update")
    @OperationLog(module = "优惠管理", type = "UPDATE", description = "更新优惠")
    public R<Void> updateDeal(@Valid @RequestBody DealUpdateDTO dto) {
        dealService.updateDeal(dto);
        return R.ok();
    }

    @Operation(summary = "删除优惠信息")
    @DeleteMapping("/{id}")
    @OperationLog(module = "优惠管理", type = "DELETE", description = "删除优惠")
    public R<Void> deleteDeal(@PathVariable("id") Long id) {
        dealService.deleteDeal(id);
        return R.ok();
    }

    @Operation(summary = "审核优惠")
    @PutMapping("/verify/{id}")
    @OperationLog(module = "优惠管理", type = "VERIFY", description = "审核优惠")
    public R<Void> verifyDeal(
            @PathVariable("id") Long id,
            @RequestParam("passed") boolean passed) {
        dealService.verifyDeal(id, passed);
        return R.ok();
    }

    @Operation(summary = "收藏优惠")
    @PostMapping("/favorite/{id}")
    @OperationLog(module = "优惠管理", type = "FAVORITE", description = "收藏优惠")
    public R<Void> favoriteDeal(@PathVariable("id") Long id) {
        dealService.favoriteDeal(id);
        return R.ok();
    }

    @Operation(summary = "取消收藏")
    @DeleteMapping("/favorite/{id}")
    @OperationLog(module = "优惠管理", type = "UNFAVORITE", description = "取消收藏")
    public R<Void> unfavoriteDeal(@PathVariable("id") Long id) {
        dealService.unfavoriteDeal(id);
        return R.ok();
    }

    @Operation(summary = "检查是否已收藏")
    @GetMapping("/favorite/check/{id}")
    public R<Boolean> isFavorite(@PathVariable("id") Long id) {
        Long userId = SecurityUtils.getCurrentUserId();
        boolean favorite = dealService.isFavorite(userId, id);
        return R.ok(favorite);
    }

    @Operation(summary = "查询用户发布的优惠")
    @GetMapping("/user/{userId}/page")
    public R<Page<DealVO>> pageUserDeals(
            @PathVariable("userId") Long userId,
            @RequestParam(value="page",defaultValue = "1") int page,
            @RequestParam(value="size",defaultValue = "10") int size) {
        Page<DealVO> dealPage = dealService.pageUserDeals(userId, page, size);
        return R.ok(dealPage);
    }
}
