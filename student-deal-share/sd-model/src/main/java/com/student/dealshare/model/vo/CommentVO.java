package com.student.dealshare.model.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class CommentVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long commentId;

    private Long postId;

    private Long userId;

    private String authorName;

    private String authorAvatar;

    private Long parentId;

    private Integer level;

    private String content;

    private String[] images;

    private Integer status;

    private Long likeCount;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
