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

import java.time.LocalDateTime;
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

        // 查询包含软删除的历史记录
        UserFavorite exist = userFavoriteMapper.selectIgnoreLogicDel(userId, 1, id);

        if (exist != null) {
            if (exist.getDeleted() == 0) {
                // 当前已是收藏状态，幂等拦截
                log.info("用户已收藏，无需重复操作，userId: {}, targetId: {}", userId, id);
                throw new BusinessException(5008, "您已经收藏过该内容");
            }
            // ✅ 用自定义 update，绕过 @TableLogic 的 AND deleted=0 限制
            int rows = userFavoriteMapper.updateIgnoreLogicDel(exist.getId(), 0, LocalDateTime.now());
            if (rows == 0) {
                // 更新失败兜底保护，不继续累加收藏数
                throw new BusinessException("收藏操作失败，请重试");
            }
        } else {
            // 全新记录
            UserFavorite favorite = new UserFavorite();
            favorite.setUserId(userId);
            favorite.setTargetType(1);
            favorite.setTargetId(id);
            userFavoriteMapper.insert(favorite);
        }

        // 只有上面成功才会走到这里
        Deal deal = dealMapper.selectById(id);
        if (deal != null) {
            deal.setFavoriteCount(deal.getFavoriteCount() + 1);
            dealMapper.updateById(deal);
        }
        log.info("优惠收藏成功，dealId: {}, userId: {}", id, userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void unfavoriteDeal(Long id) {
        Long userId = SecurityUtils.getCurrentUserId();

        UserFavorite exist = userFavoriteMapper.selectIgnoreLogicDel(userId, 1, id);

        if (exist == null || exist.getDeleted() == 1) {
            log.info("用户未收藏，无需操作，userId: {}, targetId: {}", userId, id);
            return;
        }

        // ✅ 同样用自定义 update
        int rows = userFavoriteMapper.updateIgnoreLogicDel(exist.getId(), 1, exist.getCreatedAt());
        if (rows > 0) {
            Deal deal = dealMapper.selectById(id);
            if (deal != null && deal.getFavoriteCount() > 0) {
                deal.setFavoriteCount(deal.getFavoriteCount() - 1);
                dealMapper.updateById(deal);
            }
            log.info("优惠取消收藏成功，dealId: {}, userId: {}", id, userId);
        }
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
