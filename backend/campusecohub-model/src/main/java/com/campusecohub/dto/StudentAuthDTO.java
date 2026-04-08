package com.campusecohub.dto;

import lombok.Data;

/**
 * 学生认证请求DTO
 */
@Data
public class StudentAuthDTO {
    /**
     * 学生ID
     */
    private String studentId;

    /**
     * 学校ID
     */
    private Long schoolId;

    /**
     * 学生姓名
     */
    private String name;
}
