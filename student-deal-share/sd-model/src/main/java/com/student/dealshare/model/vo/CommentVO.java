package com.student.dealshare.model.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class CommentVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;

    private Long userId;

    private Integer targetType;

    private Long targetId;

    private Long parentId;

    private Long replyUserId;

    private String authorName;

    private String authorAvatar;

    private String replyUserName;

    private String content;

    private String imageUrls;

    private Integer likeCount;

    private Integer status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
