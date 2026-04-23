package com.student.dealshare.converter;

import com.student.dealshare.model.dto.CommentCreateDTO;
import com.student.dealshare.model.entity.Comment;
import com.student.dealshare.model.vo.CommentVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 评论对象转换器
 */
@Mapper(componentModel = "spring")
public interface CommentConverter {

    CommentConverter INSTANCE = Mappers.getMapper(CommentConverter.class);

    /**
     * DTO 转 Entity
     */
    Comment toEntity(CommentCreateDTO dto);

    /**
     * Entity 转 VO
     */
    CommentVO toVO(Comment comment);
}
