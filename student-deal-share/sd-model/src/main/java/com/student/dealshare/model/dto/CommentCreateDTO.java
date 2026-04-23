package com.student.dealshare.model.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Data
public class CommentCreateDTO {

    @NotNull(message = "目标类型不能为空")
    private Integer targetType;

    @NotNull(message = "目标 ID 不能为空")
    private Long targetId;

    private Long parentId;

    private Long replyUserId;

    @NotBlank(message = "评论内容不能为空")
    @Size(max = 1000, message = "评论最多 1000 字")
    private String content;

    private String imageUrls;
}
