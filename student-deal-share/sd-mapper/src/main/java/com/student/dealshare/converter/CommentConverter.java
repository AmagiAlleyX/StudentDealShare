package com.student.dealshare.converter;

import com.student.dealshare.model.dto.CommentCreateDTO;
import com.student.dealshare.model.entity.Comment;
import com.student.dealshare.model.vo.CommentVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CommentConverter {

    CommentConverter INSTANCE = Mappers.getMapper(CommentConverter.class);

    CommentCreateDTO toCreateDTO(Comment comment);

    Comment toEntity(CommentCreateDTO dto);

    CommentVO toVO(Comment comment);

    @Mapping(target = "commentId", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "likeCount", ignore = true)
    void updateCommentFromDTO(CommentCreateDTO dto, @MappingTarget Comment comment);
}
