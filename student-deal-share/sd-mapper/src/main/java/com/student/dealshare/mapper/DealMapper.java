package com.student.dealshare.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.student.dealshare.model.entity.Deal;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DealMapper extends BaseMapper<Deal> {
}
