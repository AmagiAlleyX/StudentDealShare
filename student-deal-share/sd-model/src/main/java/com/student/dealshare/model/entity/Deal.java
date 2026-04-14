package com.student.dealshare.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("deal")
public class Deal implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "deal_id", type = IdType.ASSIGN_ID)
    private Long dealId;

    private String title;

    private String description;

    private Long categoryId;

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

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableLogic
    private Integer deleted;
}
