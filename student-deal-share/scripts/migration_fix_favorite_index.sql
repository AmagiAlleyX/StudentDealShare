-- ============================================
-- 数据库迁移脚本 - 修复收藏表唯一索引问题
-- 执行时间：2026-04-28
-- 说明：修改 t_user_favorite 表的唯一索引，将 deleted 字段包含进去
-- ============================================

USE student_deal_share;

-- 删除旧的唯一索引
ALTER TABLE t_user_favorite DROP INDEX uk_user_target;

-- 添加新的唯一索引（包含 deleted 字段）
ALTER TABLE t_user_favorite ADD UNIQUE KEY uk_user_target (user_id, target_type, target_id, deleted);

-- 验证索引是否创建成功
SHOW INDEX FROM t_user_favorite;

-- 说明：
-- 修改后的唯一索引允许同一用户对同一目标同时存在 deleted=0 和 deleted=1 的记录
-- 这样可以实现：
-- 1. 用户收藏时插入 deleted=0 的记录
-- 2. 取消收藏时更新为 deleted=1（不会违反唯一索引）
-- 3. 再次收藏时可以插入新的 deleted=0 的记录（因为 (user_id, target_type, target_id, 0) 与 (user_id, target_type, target_id, 1) 不同）
-- 4. 如果已存在 deleted=0 的记录，再次插入会违反唯一索引，从而防止重复收藏
