package com.campusecohub.entity;

import lombok.Data;

import java.util.Date;

/**
 * 活动实体类
 */
@Data
public class Activity {
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
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 学校信息（关联查询）
     */
    private School school;

    /**
     * 是否已收藏（关联查询）
     */
    private Boolean isCollected;

    /**
     * 剩余时间（关联查询）
     */
    private Long remainingTime;
}
