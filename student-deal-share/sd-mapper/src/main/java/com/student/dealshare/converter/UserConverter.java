package com.student.dealshare.converter;

import com.student.dealshare.model.dto.UserRegisterDTO;
import com.student.dealshare.model.dto.UserUpdateDTO;
import com.student.dealshare.model.entity.User;
import com.student.dealshare.model.vo.UserVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserConverter {

    UserConverter INSTANCE = Mappers.getMapper(UserConverter.class);

    UserRegisterDTO toRegisterDTO(User user);

    User toEntity(UserRegisterDTO dto);

    UserUpdateDTO toUpdateDTO(User user);

    UserVO toVO(User user);

    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "username", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    void updateUserFromDTO(UserUpdateDTO dto, @MappingTarget User user);
}
