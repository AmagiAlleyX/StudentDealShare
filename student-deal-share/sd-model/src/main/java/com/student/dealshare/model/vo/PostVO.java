package com.student.dealshare.model.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class PostVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long postId;

    private String title;

    private String content;

    private Integer postType;

    private String[] images;

    private Long userId;

    private String authorName;

    private String authorAvatar;

    private Integer status;

    private Integer isTop;

    private Integer isEssence;

    private Long viewCount;

    private Long commentCount;

    private Long likeCount;

    private Long shareCount;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
