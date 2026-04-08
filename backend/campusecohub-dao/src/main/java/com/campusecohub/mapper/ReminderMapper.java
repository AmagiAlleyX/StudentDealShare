package com.campusecohub.mapper;

import com.campusecohub.entity.Reminder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Delete;

import java.util.Date;
import java.util.List;

/**
 * 提醒Mapper
 */
@Mapper
public interface ReminderMapper {
    /**
     * 根据ID查询提醒
     */
    Reminder selectById(Long id);

    /**
     * 根据用户ID查询提醒
     */
    List<Reminder> selectByUserId(Long userId);

    /**
     * 根据活动ID查询提醒
     */
    @Select("SELECT * FROM reminder WHERE activity_id = #{activityId}")
    List<Reminder> selectByActivityId(Long activityId);

    /**
     * 查询未提醒且提醒时间小于等于当前时间的提醒
     */
    List<Reminder> selectUnsentReminders(Date now);

    /**
     * 插入提醒
     */
    @Insert("INSERT INTO reminder (user_id, activity_id, reminder_time, status, create_time) VALUES (#{userId}, #{activityId}, #{reminderTime}, 0, NOW())")
    int insert(Reminder reminder);

    /**
     * 更新提醒状态
     */
    int updateStatus(Long id, Integer status);

    /**
     * 删除提醒
     */
    int delete(Long id);

    /**
     * 根据用户ID和活动ID删除提醒
     */
    @Delete("DELETE FROM reminder WHERE user_id = #{userId} AND activity_id = #{activityId}")
    int deleteByUserIdAndActivityId(Long userId, Long activityId);

    /**
     * 根据用户ID删除提醒
     */
    @Delete("DELETE FROM reminder WHERE user_id = #{userId}")
    int deleteByUserId(Long userId);

    /**
     * 根据活动ID删除提醒
     */
    @Delete("DELETE FROM reminder WHERE activity_id = #{activityId}")
    int deleteByActivityId(Long activityId);
}
