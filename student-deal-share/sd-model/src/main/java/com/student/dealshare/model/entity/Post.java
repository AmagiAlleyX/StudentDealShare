package com.student.dealshare.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("t_post")
public class Post implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String title;

    private String content;

    private Long userId;

    private Long categoryId;

    private String imageUrls;

    private String videoUrls;

    private String tags;

    private Integer type;

    private Integer status;

    private Integer viewCount;

    private Integer favoriteCount;

    private Integer likeCount;

    private Integer commentCount;

    private Integer shareCount;

    private Integer top;

    private Integer essence;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;

    @TableLogic
    private Integer deleted;
}
