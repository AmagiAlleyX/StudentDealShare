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

    @NotNull(message = "类型不能为空")
    private Integer type;

    private String imageUrls;

    private String videoUrl;

    @NotNull(message = "价格不能为空")
    @DecimalMin(value = "0.01", message = "价格必须大于 0")
    private BigDecimal price;

    private BigDecimal dealPrice;

    private String discount;

    private java.time.LocalDateTime startTime;

    private java.time.LocalDateTime endTime;

    private String url;

    private String qrCode;

    private String tags;
}
