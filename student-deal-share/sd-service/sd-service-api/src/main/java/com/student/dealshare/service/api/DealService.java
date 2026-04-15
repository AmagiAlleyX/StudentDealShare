package com.student.dealshare.service.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.student.dealshare.model.dto.DealCreateDTO;
import com.student.dealshare.model.dto.DealQueryDTO;
import com.student.dealshare.model.dto.DealUpdateDTO;
import com.student.dealshare.model.vo.DealVO;
import java.util.List;

/**
 * 优惠信息服务接口
 */
public interface DealService {

    /**
     * 创建优惠信息
     * @param dto 创建信息
     * @return 优惠信息
     */
    DealVO createDeal(DealCreateDTO dto);

    /**
     * 获取优惠详情
     * @param dealId 优惠 ID
     * @return 优惠详情
     */
    DealVO getDealById(Long dealId);

    /**
     * 分页查询优惠
     * @param queryDTO 查询条件
     * @return 优惠列表
     */
    Page<DealVO> pageDeals(DealQueryDTO queryDTO);

    /**
     * 热门优惠列表
     * @param limit 数量限制
     * @return 优惠列表
     */
    List<DealVO> listHotDeals(int limit);

    /**
     * 更新优惠信息
     * @param dto 更新信息
     */
    void updateDeal(DealUpdateDTO dto);

    /**
     * 删除优惠信息
     * @param dealId 优惠 ID
     */
    void deleteDeal(Long dealId);

    /**
     * 审核优惠
     * @param dealId 优惠 ID
     * @param passed 是否通过
     */
    void verifyDeal(Long dealId, boolean passed);

    /**
     * 收藏优惠
     * @param dealId 优惠 ID
     */
    void favoriteDeal(Long dealId);

    /**
     * 取消收藏
     * @param dealId 优惠 ID
     */
    void unfavoriteDeal(Long dealId);

    /**
     * 检查是否已收藏
     * @param userId 用户 ID
     * @param dealId 优惠 ID
     * @return true-已收藏，false-未收藏
     */
    boolean isFavorite(Long userId, Long dealId);

    /**
     * 增加浏览量
     * @param dealId 优惠 ID
     */
    void incrementViewCount(Long dealId);

    /**
     * 查询用户发布的优惠
     * @param userId 用户 ID
     * @param page 页码
     * @param size 每页数量
     * @return 优惠列表
     */
    Page<DealVO> pageUserDeals(Long userId, int page, int size);
}
