package com.campusecohub.mapper;

import com.campusecohub.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;

/**
 * 用户Mapper
 */
@Mapper
public interface UserMapper {
    /**
     * 根据ID查询用户
     */
    User selectById(Long id);

    /**
     * 根据手机号查询用户
     */
    User selectByPhone(String phone);

    /**
     * 根据微信openid查询用户
     */
    @Select("SELECT * FROM user WHERE wechat_openid = #{wechatOpenid}")
    User selectByWechatOpenid(String wechatOpenid);

    /**
     * 插入用户
     */
    int insert(User user);

    /**
     * 更新用户
     */

    int update(User user);

    /**
     * 更新学生认证状态
     */
    @Update("UPDATE user SET student_status = #{studentStatus}, school_id = #{schoolId}, update_time = NOW() WHERE id = #{id}")
    int updateStudentStatus(Long id, Integer studentStatus, Long schoolId);
}
