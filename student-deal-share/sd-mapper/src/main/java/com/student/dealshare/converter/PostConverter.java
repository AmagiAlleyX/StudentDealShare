package com.student.dealshare.converter;

import com.student.dealshare.model.dto.PostCreateDTO;
import com.student.dealshare.model.dto.PostUpdateDTO;
import com.student.dealshare.model.entity.Post;
import com.student.dealshare.model.vo.PostVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PostConverter {

    PostConverter INSTANCE = Mappers.getMapper(PostConverter.class);

    PostCreateDTO toCreateDTO(Post post);

    Post toEntity(PostCreateDTO dto);

    PostUpdateDTO toUpdateDTO(Post post);

    PostVO toVO(Post post);

    @Mapping(target = "postId", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "viewCount", ignore = true)
    @Mapping(target = "commentCount", ignore = true)
    @Mapping(target = "likeCount", ignore = true)
    @Mapping(target = "shareCount", ignore = true)
    void updatePostFromDTO(PostUpdateDTO dto, @MappingTarget Post post);
}
