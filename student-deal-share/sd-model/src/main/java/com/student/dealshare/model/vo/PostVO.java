package com.student.dealshare.model.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class PostVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    private String title;

    private String content;

    private Integer type;

    private String imageUrls;

    private String videoUrls;

    private String tags;

    private Long userId;

    private Long categoryId;

    private String authorName;

    private String authorAvatar;

    private Integer status;

    private Integer top;

    private Integer essence;

    private Integer viewCount;

    private Integer favoriteCount;

    private Integer likeCount;

    private Integer commentCount;

    private Integer shareCount;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
