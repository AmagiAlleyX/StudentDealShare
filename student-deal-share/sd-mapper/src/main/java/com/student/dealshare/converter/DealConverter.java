package com.student.dealshare.converter;

import com.student.dealshare.model.dto.DealCreateDTO;
import com.student.dealshare.model.dto.DealUpdateDTO;
import com.student.dealshare.model.entity.Deal;
import com.student.dealshare.model.vo.DealVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

/**
 * 优惠信息对象转换器
 */
@Mapper(componentModel = "spring")
public interface DealConverter {

    DealConverter INSTANCE = Mappers.getMapper(DealConverter.class);

    /**
     * DTO 转 Entity
     */
    Deal toEntity(DealCreateDTO dto);

    /**
     * Entity 转 VO
     */
    DealVO toVO(Deal deal);

    /**
     * DTO 更新 Entity
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "viewCount", ignore = true)
    @Mapping(target = "favoriteCount", ignore = true)
    @Mapping(target = "shareCount", ignore = true)
    void updateDealFromDTO(DealUpdateDTO dto, @MappingTarget Deal deal);
}
