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
        deal.setStatus(1);
        deal.setViewCount(0);
        deal.setFavoriteCount(0);
        deal.setLikeCount(0);
        deal.setCommentCount(0);
        deal.setShareCount(0);
        deal.setTop(0);
        deal.setRecommend(0);
        
        dealMapper.insert(deal);
        log.info("优惠信息创建成功，dealId: {}", deal.getId());
        
        return dealConverter.toVO(deal);
    }

    @Override
    public DealVO getDealById(Long id) {
        Deal deal = dealMapper.selectById(id);
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
        if (queryDTO.getType() != null) {
            wrapper.eq(Deal::getType, queryDTO.getType());
        }
        if (queryDTO.getStatus() != null) {
            wrapper.eq(Deal::getStatus, queryDTO.getStatus());
        }
        
        wrapper.eq(Deal::getStatus, 1)
               .orderByDesc(Deal::getCreatedAt);
        
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
        Deal deal = dealMapper.selectById(dto.getId());
        if (deal == null) {
            throw new BusinessException(ResultCodeEnum.DEAL_NOT_FOUND);
        }

        dealConverter.updateDealFromDTO(dto, deal);
        dealMapper.updateById(deal);
        log.info("优惠信息更新成功，dealId: {}", dto.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteDeal(Long id) {
        Deal deal = dealMapper.selectById(id);
        if (deal == null) {
            throw new BusinessException(ResultCodeEnum.DEAL_NOT_FOUND);
        }
        
        dealMapper.deleteById(id);
        log.info("优惠信息删除成功，dealId: {}", id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void verifyDeal(Long id, boolean passed) {
        Deal deal = dealMapper.selectById(id);
        if (deal == null) {
            throw new BusinessException(ResultCodeEnum.DEAL_NOT_FOUND);
        }

        deal.setStatus(passed ? 1 : 0);
        dealMapper.updateById(deal);
        log.info("优惠审核完成，dealId: {}, passed: {}", id, passed);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void favoriteDeal(Long id) {
        Long userId = SecurityUtils.getCurrentUserId();
        
        LambdaQueryWrapper<UserFavorite> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserFavorite::getUserId, userId)
               .eq(UserFavorite::getTargetType, 1)
               .eq(UserFavorite::getTargetId, id);
        
        UserFavorite exist = userFavoriteMapper.selectOne(wrapper);
        if (exist != null) {
            throw new BusinessException(ResultCodeEnum.ALREADY_FAVORITED);
        }

        UserFavorite favorite = new UserFavorite();
        favorite.setUserId(userId);
        favorite.setTargetType(1);
        favorite.setTargetId(id);
        userFavoriteMapper.insert(favorite);

        Deal deal = dealMapper.selectById(id);
        deal.setFavoriteCount(deal.getFavoriteCount() + 1);
        dealMapper.updateById(deal);
        
        log.info("优惠收藏成功，dealId: {}, userId: {}", id, userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void unfavoriteDeal(Long id) {
        Long userId = SecurityUtils.getCurrentUserId();

        LambdaQueryWrapper<UserFavorite> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserFavorite::getUserId, userId)
               .eq(UserFavorite::getTargetType, 1)
               .eq(UserFavorite::getTargetId, id);
        userFavoriteMapper.delete(wrapper);

        Deal deal = dealMapper.selectById(id);
        if (deal != null && deal.getFavoriteCount() > 0) {
            deal.setFavoriteCount(deal.getFavoriteCount() - 1);
            dealMapper.updateById(deal);
        }
        
        log.info("优惠取消收藏成功，dealId: {}, userId: {}", id, userId);
    }

    @Override
    public boolean isFavorite(Long userId, Long id) {
        LambdaQueryWrapper<UserFavorite> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserFavorite::getUserId, userId)
               .eq(UserFavorite::getTargetType, 1)
               .eq(UserFavorite::getTargetId, id);
        return userFavoriteMapper.selectCount(wrapper) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void incrementViewCount(Long id) {
        Deal deal = dealMapper.selectById(id);
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
               .orderByDesc(Deal::getCreatedAt);
        
        Page<Deal> result = dealMapper.selectPage(dealPage, wrapper);
        return (Page<DealVO>) result.convert(dealConverter::toVO);
    }
}
