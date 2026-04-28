package com.student.dealshare.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.student.dealshare.model.entity.UserFavorite;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import java.time.LocalDateTime;

@Mapper
public interface UserFavoriteMapper extends BaseMapper<UserFavorite> {
    @Select("SELECT * FROM t_user_favorite WHERE user_id=#{userId} " +
            "AND target_type=#{targetType} AND target_id=#{targetId} LIMIT 1")
    UserFavorite selectIgnoreLogicDel(@Param("userId") Long userId,
                                      @Param("targetType") Integer targetType,
                                      @Param("targetId") Long targetId);

    // ✅ 新增：更新时同样忽略 deleted 条件
    @Update("UPDATE t_user_favorite SET deleted=#{deleted}, created_at=#{createdAt} WHERE id=#{id}")
    int updateIgnoreLogicDel(@Param("id") Long id,
                             @Param("deleted") Integer deleted,
                             @Param("createdAt") LocalDateTime createdAt);
}
