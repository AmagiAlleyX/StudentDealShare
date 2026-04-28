package com.student.dealshare.service.api;

import com.student.dealshare.model.vo.CategoryVO;
import java.util.List;

/**
 * 分类服务接口
 */
public interface CategoryService {

    /**
     * 获取分类列表
     * @return 分类列表
     */
    List<CategoryVO> listCategories();
}
