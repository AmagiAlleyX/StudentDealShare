package com.student.dealshare.converter;

import com.student.dealshare.model.dto.PostCreateDTO;
import com.student.dealshare.model.dto.PostUpdateDTO;
import com.student.dealshare.model.entity.Post;
import com.student.dealshare.model.vo.PostVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

/**
 * 帖子对象转换器
 */
@Mapper(componentModel = "spring")
public interface PostConverter {

    PostConverter INSTANCE = Mappers.getMapper(PostConverter.class);

    /**
     * DTO 转 Entity
     */
    Post toEntity(PostCreateDTO dto);

    /**
     * Entity 转 VO
     */
    PostVO toVO(Post post);

    /**
     * DTO 更新 Entity
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "viewCount", ignore = true)
    @Mapping(target = "commentCount", ignore = true)
    @Mapping(target = "likeCount", ignore = true)
    @Mapping(target = "shareCount", ignore = true)
    void updatePostFromDTO(PostUpdateDTO dto, @MappingTarget Post post);
}
