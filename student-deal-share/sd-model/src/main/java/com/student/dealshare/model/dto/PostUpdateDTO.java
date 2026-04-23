package com.student.dealshare.model.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Data
public class PostUpdateDTO {

    @NotNull(message = "帖子 ID 不能为空")
    private Long id;

    @Size(max = 100, message = "标题最多 100 字")
    private String title;

    @Size(max = 10000, message = "内容最多 10000 字")
    private String content;

    private Integer type;

    private String imageUrls;

    private String videoUrls;

    private String tags;

    private Long categoryId;

    private Long[] topicIds;

    private Integer status;

    private Integer top;

    private Integer essence;
}
