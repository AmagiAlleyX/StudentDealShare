package com.student.dealshare.model.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

@Data
public class DealUpdateDTO {

    @NotNull(message = "优惠 ID 不能为空")
    private Long dealId;

    @Size(max = 100, message = "标题最多 100 字")
    private String title;

    @Size(max = 1000, message = "描述最多 1000 字")
    private String description;

    private Long categoryId;

    @Size(max = 50, message = "品牌名称最多 50 字")
    private String brand;

    @DecimalMin(value = "0.01", message = "原价必须大于 0")
    private BigDecimal originalPrice;

    @DecimalMin(value = "0.01", message = "活动价必须大于 0")
    private BigDecimal dealPrice;

    private String discountInfo;

    private String activityType;

    private java.time.LocalDateTime startTime;

    private java.time.LocalDateTime endTime;

    private String link;

    private String coverImage;

    private String[] images;

    private Integer status;
}
