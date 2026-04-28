package com.student.dealshare.controller;

import com.student.dealshare.common.result.R;
import com.student.dealshare.model.vo.CategoryVO;
import com.student.dealshare.service.api.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 分类控制器
 */
@Tag(name = "分类管理")
@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "获取分类列表")
    @GetMapping("/list")
    public R<List<CategoryVO>> listCategories() {
        List<CategoryVO> list = categoryService.listCategories();
        return R.ok(list);
    }
}
