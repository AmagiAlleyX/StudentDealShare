package com.student.dealshare.model.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
public class PostCreateDTO {

    @NotBlank(message = "标题不能为空")
    @Size(max = 100, message = "标题最多 100 字")
    private String title;

    @NotBlank(message = "内容不能为空")
    @Size(max = 10000, message = "内容最多 10000 字")
    private String content;

    private Integer type;

    private String imageUrls;

    private String videoUrls;

    private String tags;

    private Long categoryId;

    private Long[] topicIds;
}
