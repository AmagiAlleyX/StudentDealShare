package com.student.dealshare.service.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.student.dealshare.model.dto.DealCreateDTO;
import com.student.dealshare.model.dto.DealQueryDTO;
import com.student.dealshare.model.dto.DealUpdateDTO;
import com.student.dealshare.model.vo.DealVO;

import java.util.List;

public interface DealService {

    DealVO createDeal(DealCreateDTO dto);

    DealVO getDealById(Long dealId);

    Page<DealVO> pageDeals(DealQueryDTO queryDTO);

    List<DealVO> listHotDeals(int limit);

    void updateDeal(DealUpdateDTO dto);

    void deleteDeal(Long dealId);

    void verifyDeal(Long dealId, boolean passed);

    void favoriteDeal(Long dealId);

    void unfavoriteDeal(Long dealId);

    boolean isFavorite(Long userId, Long dealId);

    void incrementViewCount(Long dealId);

    Page<DealVO> pageUserDeals(Long userId, int page, int size);
}
