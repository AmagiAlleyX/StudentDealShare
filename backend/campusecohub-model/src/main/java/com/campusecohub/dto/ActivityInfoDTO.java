package com.campusecohub.dto;

import lombok.Data;

import java.util.Date;

/**
 * 活动信息响应DTO
 */
@Data
public class ActivityInfoDTO {
    /**
     * 活动ID
     */
    private Long id;

    /**
     * 活动标题
     */
    private String title;

    /**
     * 活动描述
     */
    private String description;

    /**
     * 活动分类
     */
    private String category;

    /**
     * 活动来源
     */
    private String source;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 参与方式
     */
    private String participateWay;

    /**
     * 活动链接
     */
    private String link;

    /**
     * 是否学生专属（0：非学生专属，1：学生专属）
     */
    private Integer isStudentExclusive;

    /**
     * 学校ID（校内活动）
     */
    private Long schoolId;

    /**
     * 学校名称
     */
    private String schoolName;

    /**
     * 是否已收藏
     */
    private Boolean isCollected;

    /**
     * 剩余时间（毫秒）
     */
    private Long remainingTime;

    /**
     * 创建时间
     */
    private Date createTime;
}
