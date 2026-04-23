package com.student.dealshare.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("t_deal")
public class Deal implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String title;

    private String description;

    private Long categoryId;

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

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;

    @TableLogic
    private Integer deleted;
}
