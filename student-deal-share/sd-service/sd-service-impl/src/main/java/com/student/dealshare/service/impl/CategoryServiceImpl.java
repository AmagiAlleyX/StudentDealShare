package com.student.dealshare.service.impl;

import com.student.dealshare.mapper.CategoryMapper;
import com.student.dealshare.model.entity.Category;
import com.student.dealshare.model.vo.CategoryVO;
import com.student.dealshare.service.api.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 分类服务实现
 */
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryVO> listCategories() {
        // 查询所有一级分类（parentId 为 null 或 0）
        List<Category> categories = categoryMapper.selectList(null);
        
        return categories.stream()
                .filter(category -> category.getParentId() == null || category.getParentId() == 0)
                .map(this::toVO)
                .collect(Collectors.toList());
    }

    private CategoryVO toVO(Category category) {
        CategoryVO vo = new CategoryVO();
        BeanUtils.copyProperties(category, vo);
        vo.setCategoryId(category.getId());
        return vo;
    }
}
