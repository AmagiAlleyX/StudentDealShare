package com.student.dealshare.model.dto;

import jakarta.validation.constraints.DecimalMin;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

@Data
public class DealUpdateDTO {

    @NotNull(message = "优惠 ID 不能为空")
    private Long id;

    @Size(max = 100, message = "标题最多 100 字")
    private String title;

    @Size(max = 1000, message = "描述最多 1000 字")
    private String description;

    private Long categoryId;

    private Integer type;

    private String imageUrls;

    private String videoUrl;

    @DecimalMin(value = "0.01", message = "价格必须大于 0")
    private BigDecimal price;

    private BigDecimal dealPrice;

    private String discount;

    private java.time.LocalDateTime startTime;

    private java.time.LocalDateTime endTime;

    private String url;

    private String qrCode;

    private String tags;

    private Integer status;

    private Integer top;

    private Integer recommend;
}
