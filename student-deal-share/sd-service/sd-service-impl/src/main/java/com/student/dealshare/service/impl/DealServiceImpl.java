package com.student.dealshare.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.student.dealshare.common.exception.BusinessException;
import com.student.dealshare.common.result.ResultCodeEnum;
import com.student.dealshare.converter.DealConverter;
import com.student.dealshare.mapper.DealMapper;
import com.student.dealshare.mapper.UserFavoriteMapper;
import com.student.dealshare.model.dto.DealCreateDTO;
import com.student.dealshare.model.dto.DealQueryDTO;
import com.student.dealshare.model.dto.DealUpdateDTO;
import com.student.dealshare.model.entity.Deal;
import com.student.dealshare.model.entity.UserFavorite;
import com.student.dealshare.model.vo.DealVO;
import com.student.dealshare.security.SecurityUtils;
import com.student.dealshare.service.api.DealService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 优惠信息服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DealServiceImpl extends ServiceImpl<DealMapper, Deal> implements DealService {

    private final DealMapper dealMapper;
    private final UserFavoriteMapper userFavoriteMapper;
    private final DealConverter dealConverter;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DealVO createDeal(DealCreateDTO dto) {
        Deal deal = dealConverter.toEntity(dto);
        deal.setUserId(SecurityUtils.getCurrentUserId());
        deal.setStatus(0);
        deal.setIsVerified(0);
        deal.setViewCount(0L);
        deal.setFavoriteCount(0L);
        deal.setShareCount(0L);
        
        dealMapper.insert(deal);
        log.info("优惠信息创建成功，dealId: {}", deal.getDealId());
        
        return dealConverter.toVO(deal);
    }

    @Override
    public DealVO getDealById(Long dealId) {
        Deal deal = dealMapper.selectById(dealId);
        if (deal == null) {
            throw new BusinessException(ResultCodeEnum.DEAL_NOT_FOUND);
        }
        return dealConverter.toVO(deal);
    }

    @Override
    public Page<DealVO> pageDeals(DealQueryDTO queryDTO) {
        Page<Deal> page = new Page<>(queryDTO.getPage(), queryDTO.getSize());
        LambdaQueryWrapper<Deal> wrapper = new LambdaQueryWrapper<>();
        
        if (queryDTO.getCategoryId() != null) {
            wrapper.eq(Deal::getCategoryId, queryDTO.getCategoryId());
        }
        if (queryDTO.getStatus() != null) {
            wrapper.eq(Deal::getStatus, queryDTO.getStatus());
        }
        if (queryDTO.getActivityType() != null) {
            wrapper.eq(Deal::getActivityType, queryDTO.getActivityType());
        }
        
        wrapper.eq(Deal::getStatus, 1)
               .orderByDesc(Deal::getCreateTime);
        
        Page<Deal> dealPage = dealMapper.selectPage(page, wrapper);
        return (Page<DealVO>) dealPage.convert(dealConverter::toVO);
    }

    @Override
    public List<DealVO> listHotDeals(int limit) {
        LambdaQueryWrapper<Deal> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Deal::getStatus, 1)
               .orderByDesc(Deal::getViewCount)
               .last("LIMIT " + limit);
        
        List<Deal> deals = dealMapper.selectList(wrapper);
        return deals.stream()
                .map(dealConverter::toVO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDeal(DealUpdateDTO dto) {
        Deal deal = dealMapper.selectById(dto.getDealId());
        if (deal == null) {
            throw new BusinessException(ResultCodeEnum.DEAL_NOT_FOUND);
        }

        dealConverter.updateDealFromDTO(dto, deal);
        dealMapper.updateById(deal);
        log.info("优惠信息更新成功，dealId: {}", dto.getDealId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDeal(Long dealId) {
        Deal deal = dealMapper.selectById(dealId);
        if (deal == null) {
            throw new BusinessException(ResultCodeEnum.DEAL_NOT_FOUND);
        }
        
        dealMapper.deleteById(dealId);
        log.info("优惠信息删除成功，dealId: {}", dealId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void verifyDeal(Long dealId, boolean passed) {
        Deal deal = dealMapper.selectById(dealId);
        if (deal == null) {
            throw new BusinessException(ResultCodeEnum.DEAL_NOT_FOUND);
        }

        deal.setIsVerified(passed ? 1 : 0);
        deal.setStatus(passed ? 1 : 2);
        dealMapper.updateById(deal);
        log.info("优惠审核完成，dealId: {}, passed: {}", dealId, passed);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void favoriteDeal(Long dealId) {
        Long userId = SecurityUtils.getCurrentUserId();
        
        LambdaQueryWrapper<UserFavorite> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserFavorite::getUserId, userId)
               .eq(UserFavorite::getTargetType, 1)
               .eq(UserFavorite::getTargetId, dealId);
        
        UserFavorite exist = userFavoriteMapper.selectOne(wrapper);
        if (exist != null) {
            throw new BusinessException(ResultCodeEnum.ALREADY_FAVORITED);
        }

        UserFavorite favorite = new UserFavorite();
        favorite.setUserId(userId);
        favorite.setTargetType(1L);
        favorite.setTargetId(dealId);
        userFavoriteMapper.insert(favorite);

        Deal deal = dealMapper.selectById(dealId);
        deal.setFavoriteCount(deal.getFavoriteCount() + 1);
        dealMapper.updateById(deal);
        
        log.info("优惠收藏成功，dealId: {}, userId: {}", dealId, userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void unfavoriteDeal(Long dealId) {
        Long userId = SecurityUtils.getCurrentUserId();

        LambdaQueryWrapper<UserFavorite> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserFavorite::getUserId, userId)
               .eq(UserFavorite::getTargetType, 1)
               .eq(UserFavorite::getTargetId, dealId);
        userFavoriteMapper.delete(wrapper);

        Deal deal = dealMapper.selectById(dealId);
        if (deal != null && deal.getFavoriteCount() > 0) {
            deal.setFavoriteCount(deal.getFavoriteCount() - 1);
            dealMapper.updateById(deal);
        }
        
        log.info("优惠取消收藏成功，dealId: {}, userId: {}", dealId, userId);
    }

    @Override
    public boolean isFavorite(Long userId, Long dealId) {
        LambdaQueryWrapper<UserFavorite> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserFavorite::getUserId, userId)
               .eq(UserFavorite::getTargetType, 1)
               .eq(UserFavorite::getTargetId, dealId);
        return userFavoriteMapper.selectCount(wrapper) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void incrementViewCount(Long dealId) {
        Deal deal = dealMapper.selectById(dealId);
        if (deal != null) {
            deal.setViewCount(deal.getViewCount() + 1);
            dealMapper.updateById(deal);
        }
    }

    @Override
    public Page<DealVO> pageUserDeals(Long userId, int page, int size) {
        Page<Deal> dealPage = new Page<>(page, size);
        LambdaQueryWrapper<Deal> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Deal::getUserId, userId)
               .orderByDesc(Deal::getCreateTime);
        
        Page<Deal> result = dealMapper.selectPage(dealPage, wrapper);
        return (Page<DealVO>) result.convert(dealConverter::toVO);
    }
}
