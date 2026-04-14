package com.student.dealshare.model.vo;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class TopicVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long topicId;

    private String name;

    private String description;

    private String coverImage;

    private Long postCount;

    private Long followCount;

    private Integer status;

    private LocalDateTime createTime;
}
