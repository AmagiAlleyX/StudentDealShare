package com.student.dealshare.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("post")
public class Post implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "post_id", type = IdType.ASSIGN_ID)
    private Long postId;

    private String title;

    private String content;

    private Integer postType;

    private String[] images;

    private Long userId;

    private Integer status;

    private Integer isTop;

    private Integer isEssence;

    private Long viewCount;

    private Long commentCount;

    private Long likeCount;

    private Long shareCount;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableLogic
    private Integer deleted;
}
