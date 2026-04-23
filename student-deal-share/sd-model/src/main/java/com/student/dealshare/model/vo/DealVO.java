package com.student.dealshare.model.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class DealVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    private String title;

    private String description;

    private Long categoryId;

    private String categoryName;

    private Long userId;

    private Integer type;

    private String imageUrls;

    private String videoUrl;

    private BigDecimal price;

    private BigDecimal dealPrice;

    private String discount;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String url;

    private String qrCode;

    private String tags;

    private Integer status;

    private Integer viewCount;

    private Integer favoriteCount;

    private Integer likeCount;

    private Integer commentCount;

    private Integer shareCount;

    private Integer top;

    private Integer recommend;

    private String authorName;

    private String authorAvatar;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
