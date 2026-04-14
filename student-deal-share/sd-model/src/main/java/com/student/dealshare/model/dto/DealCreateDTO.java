package com.student.dealshare.model.dto;

import lombok.Data;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

@Data
public class DealCreateDTO {

    @NotBlank(message = "标题不能为空")
    @Size(max = 100, message = "标题最多 100 字")
    private String title;

    @Size(max = 1000, message = "描述最多 1000 字")
    private String description;

    @NotNull(message = "分类不能为空")
    private Long categoryId;

    @Size(max = 50, message = "品牌名称最多 50 字")
    private String brand;

    @NotNull(message = "原价不能为空")
    @DecimalMin(value = "0.01", message = "原价必须大于 0")
    private BigDecimal originalPrice;

    @NotNull(message = "活动价不能为空")
    @DecimalMin(value = "0.01", message = "活动价必须大于 0")
    private BigDecimal dealPrice;

    private String discountInfo;

    private String activityType;

    private java.time.LocalDateTime startTime;

    private java.time.LocalDateTime endTime;

    @NotBlank(message = "购买链接不能为空")
    private String link;

    private String coverImage;

    private String[] images;
}
