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

    private Long dealId;

    private String title;

    private String description;

    private Long categoryId;

    private String categoryName;

    private String brand;

    private BigDecimal originalPrice;

    private BigDecimal dealPrice;

    private String discountInfo;

    private String activityType;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String link;

    private String coverImage;

    private String[] images;

    private Integer status;

    private Integer isVerified;

    private Long viewCount;

    private Long favoriteCount;

    private Long shareCount;

    private Long userId;

    private String authorName;

    private String authorAvatar;

    private LocalDateTime createTime;
}
