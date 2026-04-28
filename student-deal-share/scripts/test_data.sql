-- ============================================
-- 学生优惠分享平台 - 测试数据 SQL 脚本
-- 数据库：MariaDB/MySQL
-- 生成时间：2026-04-27
-- 说明：为每个表插入至少 5 条测试数据
-- ============================================

-- 设置字符集
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- 使用数据库
USE student_deal_share;

-- ============================================
-- 1. 用户表 (t_user) - 8 条数据
-- ============================================
INSERT INTO t_user (username, password, nickname, avatar, gender, phone, email, school, college, student_id, level, points, status, last_login_time, created_at, updated_at, deleted) VALUES
('user1', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iJOmJkPMhEIqlp6LqUuL5qPqPqPq', '天城夏树', 'avatar1.png', 0, '17359244157', 'amagialleyx@163.com', 'XX 大学', '计算机学院', '2024001', 5, 1200, 1, '2026-04-27 10:30:00', NOW(), NOW(), 0),
('user2', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iJOmJkPMhEIqlp6LqUuL5qPqPqPq', '樱花飞舞', 'avatar2.png', 1, '13800138001', 'sakura@example.com', 'XX 大学', '外国语学院', '2024002', 4, 850, 1, '2026-04-26 15:20:00', NOW(), NOW(), 0),
('user3', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iJOmJkPMhEIqlp6LqUuL5qPqPqPq', '清风徐来', 'avatar3.png', 0, '13900139002', 'qingfeng@example.com', 'XX 大学', '经济管理学院', '2024003', 3, 620, 1, '2026-04-25 09:15:00', NOW(), NOW(), 0),
('user4', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iJOmJkPMhEIqlp6LqUuL5qPqPqPq', '星辰大海', 'avatar4.png', 1, '13700137003', 'starsea@example.com', 'XX 大学', '艺术学院', '2024004', 4, 930, 1, '2026-04-27 08:00:00', NOW(), NOW(), 0),
('user5', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iJOmJkPMhEIqlp6LqUuL5qPqPqPq', '阳光少年', 'avatar5.png', 0, '13600136004', 'sunshine@example.com', 'XX 大学', '体育学院', '2024005', 2, 380, 1, '2026-04-24 20:45:00', NOW(), NOW(), 0),
('user6', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iJOmJkPMhEIqlp6LqUuL5qPqPqPq', '明月几时有', 'avatar6.png', 1, '13500135005', 'moonlight@example.com', 'XX 大学', '文学院', '2024006', 3, 550, 1, '2026-04-26 12:30:00', NOW(), NOW(), 0),
('user7', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iJOmJkPMhEIqlp6LqUuL5qPqPqPq', '科技达人', 'avatar7.png', 0, '13400134006', 'techguru@example.com', 'XX 大学', '信息工程学院', '2024007', 5, 1500, 1, '2026-04-27 11:00:00', NOW(), NOW(), 0),
('user8', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iJOmJkPMhEIqlp6LqUuL5qPqPqPq', '美食探索者', 'avatar8.png', 1, '13300133007', 'foodie@example.com', 'XX 大学', '旅游学院', '2024008', 2, 420, 1, '2026-04-25 18:20:00', NOW(), NOW(), 0);

-- ============================================
-- 2. 管理员表 (t_admin) - 5 条数据
-- ============================================
INSERT INTO t_admin (username, password, nickname, avatar, email, phone, role, status, last_login_time, created_at, updated_at, deleted) VALUES
('admin1', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iJOmJkPMhEIqlp6LqUuL5qPqPqPq', '超级管理员', 'admin1.png', 'admin1@stuDeal.com', '13800000001', 1, 1, '2026-04-27 09:00:00', NOW(), NOW(), 0),
('admin2', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iJOmJkPMhEIqlp6LqUuL5qPqPqPq', '内容管理员', 'admin2.png', 'admin2@stuDeal.com', '13800000002', 1, 1, '2026-04-26 14:30:00', NOW(), NOW(), 0),
('admin3', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iJOmJkPMhEIqlp6LqUuL5qPqPqPq', '用户管理员', 'admin3.png', 'admin3@stuDeal.com', '13800000003', 2, 1, '2026-04-25 16:45:00', NOW(), NOW(), 0),
('admin4', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iJOmJkPMhEIqlp6LqUuL5qPqPqPq', '审核员小王', 'admin4.png', 'admin4@stuDeal.com', '13800000004', 2, 1, '2026-04-27 10:15:00', NOW(), NOW(), 0),
('admin5', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iJOmJkPMhEIqlp6LqUuL5qPqPqPq', '运营小李', 'admin5.png', 'admin5@stuDeal.com', '13800000005', 2, 1, '2026-04-26 11:20:00', NOW(), NOW(), 0);

-- ============================================
-- 3. 分类表 (t_category) - 10 条数据
-- ============================================
INSERT INTO t_category (name, parent_id, icon, sort, level, status, created_at, updated_at, deleted) VALUES
('美食餐饮', 0, 'icon-food.png', 1, 1, 1, NOW(), NOW(), 0),
('学习用品', 0, 'icon-study.png', 2, 1, 1, NOW(), NOW(), 0),
('数码产品', 0, 'icon-digital.png', 3, 1, 1, NOW(), NOW(), 0),
('生活服务', 0, 'icon-life.png', 4, 1, 1, NOW(), NOW(), 0),
('娱乐休闲', 0, 'icon-fun.png', 5, 1, 1, NOW(), NOW(), 0),
('快餐简餐', 1, 'icon-fastfood.png', 1, 2, 1, NOW(), NOW(), 0),
('咖啡茶饮', 1, 'icon-drink.png', 2, 2, 1, NOW(), NOW(), 0),
('书籍教材', 2, 'icon-book.png', 1, 2, 1, NOW(), NOW(), 0),
('电子产品', 3, 'icon-electronic.png', 1, 2, 1, NOW(), NOW(), 0),
('健身运动', 5, 'icon-sport.png', 1, 2, 1, NOW(), NOW(), 0);

-- ============================================
-- 4. 优惠信息表 (t_deal) - 10 条数据
-- ============================================
INSERT INTO t_deal (title, description, category_id, user_id, type, image_urls, video_url, price, deal_price, discount, start_time, end_time, url, qr_code, tags, status, view_count, favorite_count, like_count, comment_count, share_count, top, recommend, created_at, updated_at, deleted) VALUES
('食堂二楼新开业！全场 8 折优惠', '学校食堂二楼新开了一家餐厅，开业期间全场 8 折优惠，欢迎品尝！', 1, 1, 1, 'deal1_1.jpg,deal1_2.jpg', NULL, 20.00, 16.00, '8 折', '2026-04-20 00:00:00', '2026-05-20 23:59:59', 'https://example.com/deal1', 'qr1.png', '美食，开业优惠', 1, 156, 23, 18, 5, 3, 0, 1, NOW(), NOW(), 0),
('图书馆咖啡厅买一送一', '图书馆一楼咖啡厅推出买一送一活动，仅限每周三下午', 6, 2, 1, 'deal2_1.jpg', NULL, 25.00, 12.50, '买一送一', '2026-04-01 00:00:00', '2026-06-30 23:59:59', NULL, 'qr2.png', '咖啡，下午茶', 1, 289, 45, 32, 12, 8, 1, 1, NOW(), NOW(), 0),
('二手教材低价转让', '计算机专业教材，9 成新，低价转让', 7, 3, 2, 'deal3_1.jpg,deal3_2.jpg', NULL, 50.00, 25.00, '5 折', '2026-04-15 00:00:00', '2026-05-15 23:59:59', NULL, 'qr3.png', '二手，教材', 1, 423, 67, 41, 23, 15, 0, 0, NOW(), NOW(), 0),
('健身房月卡特惠', '学校附近健身房月卡特惠，仅需 99 元', 9, 4, 1, 'deal4_1.jpg', 'deal4.mp4', 199.00, 99.00, '5 折', '2026-04-10 00:00:00', '2026-04-30 23:59:59', 'https://gym.example.com', 'qr4.png', '健身，运动', 1, 512, 89, 56, 34, 21, 1, 1, NOW(), NOW(), 0),
('电影院学生票半价', '凭学生证购买电影票享受半价优惠', 5, 5, 1, 'deal5_1.jpg', NULL, 45.00, 22.50, '5 折', '2026-04-01 00:00:00', '2026-12-31 23:59:59', 'https://cinema.example.com', 'qr5.png', '电影，娱乐', 1, 678, 123, 78, 45, 32, 0, 1, NOW(), NOW(), 0),
('奶茶店新品尝鲜价', '新品奶茶尝鲜价，第二杯半价', 6, 6, 1, 'deal6_1.jpg,deal6_2.jpg', NULL, 18.00, 13.50, '第二杯半价', '2026-04-25 00:00:00', '2026-05-10 23:59:59', NULL, 'qr6.png', '奶茶，饮品', 1, 234, 56, 34, 18, 9, 0, 0, NOW(), NOW(), 0),
('笔记本电脑优惠购', '学生专享笔记本电脑优惠，最高立减 1000 元', 8, 7, 1, 'deal7_1.jpg', 'deal7.mp4', 5999.00, 4999.00, '立减 1000', '2026-04-20 00:00:00', '2026-05-20 23:59:59', 'https://laptop.example.com', 'qr7.png', '数码，电脑', 1, 891, 167, 123, 67, 45, 1, 1, NOW(), NOW(), 0),
('快递代取服务', '校内快递代取，每件仅需 2 元', 4, 8, 3, NULL, NULL, 5.00, 2.00, '6 折', '2026-04-01 00:00:00', '2026-06-30 23:59:59', NULL, 'qr8.png', '服务，快递', 1, 345, 78, 56, 23, 12, 0, 0, NOW(), NOW(), 0),
('考研资料打包出售', '2025 考研全套资料，含真题和笔记', 7, 1, 2, 'deal9_1.jpg', NULL, 200.00, 120.00, '6 折', '2026-04-18 00:00:00', '2026-05-18 23:59:59', NULL, 'qr9.png', '考研，资料', 1, 567, 98, 67, 34, 23, 0, 1, NOW(), NOW(), 0),
('自助打印 5 分钱一页', '图书馆自助打印机，黑白打印仅需 5 分钱一页', 2, 2, 1, 'deal10_1.jpg', NULL, 0.10, 0.05, '5 折', '2026-04-01 00:00:00', '2026-12-31 23:59:59', NULL, 'qr10.png', '打印，学习', 1, 789, 145, 89, 56, 34, 0, 0, NOW(), NOW(), 0);

-- ============================================
-- 5. 帖子表 (t_post) - 10 条数据
-- ============================================
INSERT INTO t_post (title, content, user_id, category_id, image_urls, video_urls, tags, type, status, view_count, favorite_count, like_count, comment_count, share_count, top, essence, created_at, updated_at, deleted) VALUES
('分享我的考研经验', '大家好，我是 2024 届考研上岸的学长，今天分享一下我的备考经验...', 1, 2, 'post1_1.jpg,post1_2.jpg', NULL, '考研，经验', 1, 1, 1234, 234, 189, 67, 45, 1, 1, NOW(), NOW(), 0),
('学校周边美食探店', '今天带大家探访学校周边的美食小店...', 2, 1, 'post2_1.jpg,post2_2.jpg,post2_3.jpg', 'post2.mp4', '美食，探店', 1, 1, 2345, 345, 267, 89, 56, 0, 1, NOW(), NOW(), 0),
('二手市场交易规则', '为了规范二手市场交易，特制定以下规则...', 3, 2, NULL, NULL, '二手，规则', 2, 1, 567, 89, 45, 23, 12, 0, 0, NOW(), NOW(), 0),
('校园摄影作品展示', '分享一些我在校园拍摄的照片，希望大家喜欢', 4, 5, 'post4_1.jpg,post4_2.jpg,post4_3.jpg,post4_4.jpg', NULL, '摄影，校园', 1, 1, 3456, 456, 345, 123, 78, 1, 1, NOW(), NOW(), 0),
('求助：这道题怎么做？', '高数题，求大神解答！', 5, 2, 'post5_1.jpg', NULL, '求助，高数', 3, 1, 890, 123, 78, 45, 23, 0, 0, NOW(), NOW(), 0),
('学生会招新啦！', '校学生会开始招新了，欢迎有兴趣的同学报名', 6, 4, 'post6_1.jpg', 'post6.mp4', '招新，学生会', 1, 1, 4567, 567, 456, 234, 123, 1, 1, NOW(), NOW(), 0),
('图书馆占座现象严重', '希望学校能加强图书馆座位管理', 7, 4, NULL, NULL, '图书馆，建议', 3, 1, 678, 234, 156, 89, 45, 0, 0, NOW(), NOW(), 0),
('分享一个好用的学习 APP', '这个 APP 可以帮助大家更好地管理学习时间', 8, 2, 'post8_1.jpg', NULL, '学习，APP', 1, 1, 1890, 345, 234, 78, 56, 0, 1, NOW(), NOW(), 0),
('周末约球', '这周末有人一起打篮球吗？', 1, 5, NULL, NULL, '篮球，运动', 3, 1, 456, 67, 45, 34, 12, 0, 0, NOW(), NOW(), 0),
('毕业设计选题求建议', '计算机专业，不知道选什么题目好，求学长学姐给点建议', 2, 2, NULL, NULL, '毕业设计，求助', 3, 1, 789, 123, 89, 56, 34, 0, 0, NOW(), NOW(), 0);

-- ============================================
-- 6. 评论表 (t_comment) - 20 条数据
-- ============================================
INSERT INTO t_comment (user_id, target_type, target_id, parent_id, reply_user_id, content, image_urls, like_count, status, created_at, updated_at, deleted) VALUES
-- 优惠评论 (target_type=1)
(2, 1, 1, 0, NULL, '感谢学长分享，很有用！', NULL, 23, 1, NOW(), NOW(), 0),
(3, 1, 1, 0, NULL, '已收藏，准备考研了', NULL, 15, 1, NOW(), NOW(), 0),
(4, 1, 1, 1, 2, '回复 1 楼：一起加油！', NULL, 8, 1, NOW(), NOW(), 0),
(5, 1, 2, 0, NULL, '这家店确实不错，我经常去', NULL, 12, 1, NOW(), NOW(), 0),
(6, 1, 2, 0, NULL, '求地址！', NULL, 5, 1, NOW(), NOW(), 0),
(7, 1, 2, 5, 5, '地址在学校东门旁边', NULL, 3, 1, NOW(), NOW(), 0),
(8, 1, 3, 0, NULL, '还有吗？我也想要', NULL, 18, 1, NOW(), NOW(), 0),
(1, 1, 3, 0, NULL, '可以私聊吗？', NULL, 7, 1, NOW(), NOW(), 0),
-- 帖子评论 (target_type=2)
(2, 2, 1, 0, NULL, '学长学姐好，求更多考研资料！', 'comment_post1_1.jpg', 15, 1, NOW(), NOW(), 0),
(3, 2, 1, 0, NULL, '感谢分享，受益匪浅', NULL, 8, 1, NOW(), NOW(), 0),
(4, 2, 1, 9, 2, '回复 2 楼：我这里有电子版，可以发你', NULL, 5, 1, NOW(), NOW(), 0),
(5, 2, 2, 0, NULL, '这家店我也去过，确实不错！', 'comment_post2_1.jpg,comment_post2_2.jpg', 12, 1, NOW(), NOW(), 0),
(6, 2, 2, 0, NULL, '求具体位置', NULL, 3, 1, NOW(), NOW(), 0),
(7, 2, 2, 12, 5, '在学校东门商业街二楼', NULL, 2, 1, NOW(), NOW(), 0),
(8, 2, 3, 0, NULL, '二手交易要注意安全啊', NULL, 6, 1, NOW(), NOW(), 0),
(1, 2, 3, 0, NULL, '支持面交', NULL, 4, 1, NOW(), NOW(), 0),
(2, 2, 4, 0, NULL, '拍得真好看！求教摄影技巧', NULL, 18, 1, NOW(), NOW(), 0),
(3, 2, 4, 0, NULL, '这是什么相机拍的？', NULL, 9, 1, NOW(), NOW(), 0),
(4, 2, 4, 17, 12, '用的是索尼 A7M3', NULL, 7, 1, NOW(), NOW(), 0),
(5, 2, 9, 0, NULL, '这周末下午 2 点，学校篮球场见', NULL, 11, 1, NOW(), NOW(), 0);

-- ============================================
-- 7. 话题表 (t_topic) - 6 条数据
-- ============================================
INSERT INTO t_topic (name, description, cover_image, post_count, view_count, status, created_at, updated_at) VALUES
('考研专区', '考研交流、资料分享、经验总结', 'topic1.jpg', 156, 12340, 1, NOW(), NOW()),
('美食推荐', '校园及周边美食推荐、探店分享', 'topic2.jpg', 234, 23450, 1, NOW(), NOW()),
('二手交易', '闲置物品交易、求购信息', 'topic3.jpg', 189, 18900, 1, NOW(), NOW()),
('学习交流', '课程讨论、作业互助、学习资源', 'topic4.jpg', 345, 34500, 1, NOW(), NOW()),
('校园生活', '校园新闻、活动通知、日常生活', 'topic5.jpg', 278, 27800, 1, NOW(), NOW()),
('就业求职', '招聘信息、面试经验、职场交流', 'topic6.jpg', 167, 16700, 1, NOW(), NOW());

-- ============================================
-- 8. 标签表 (t_tag) - 10 条数据
-- ============================================
INSERT INTO t_tag (name, type, use_count, status, created_at) VALUES
('考研', 1, 234, 1, NOW()),
('美食', 1, 345, 1, NOW()),
('二手', 1, 189, 1, NOW()),
('学习', 1, 456, 1, NOW()),
('运动', 1, 123, 1, NOW()),
('娱乐', 1, 234, 1, NOW()),
('兼职', 1, 167, 1, NOW()),
('租房', 1, 89, 1, NOW()),
('拼车', 1, 56, 1, NOW()),
('失物招领', 1, 78, 1, NOW());

-- ============================================
-- 9. 点赞记录表 (t_like_record) - 15 条数据
-- ============================================
INSERT INTO t_like_record (user_id, target_type, target_id, created_at) VALUES
(1, 1, 2, NOW()),
(1, 1, 4, NOW()),
(2, 1, 1, NOW()),
(2, 1, 3, NOW()),
(3, 1, 1, NOW()),
(3, 1, 4, NOW()),
(4, 1, 2, NOW()),
(5, 1, 6, NOW()),
(6, 1, 4, NOW()),
(7, 1, 1, NOW()),
(8, 1, 2, NOW()),
(1, 2, 1, NOW()),
(2, 2, 2, NOW()),
(3, 2, 4, NOW()),
(4, 2, 6, NOW());

-- ============================================
-- 10. 用户收藏表 (t_user_favorite) - 12 条数据
-- ============================================
INSERT INTO t_user_favorite (user_id, target_type, target_id, created_at, deleted) VALUES
(1, 1, 2, NOW(), 0),
(1, 1, 4, NOW(), 0),
(1, 2, 1, NOW(), 0),
(2, 1, 1, NOW(), 0),
(2, 1, 3, NOW(), 0),
(2, 2, 2, NOW(), 0),
(3, 1, 1, NOW(), 0),
(3, 1, 4, NOW(), 0),
(4, 1, 2, NOW(), 0),
(5, 1, 6, NOW(), 0),
(6, 1, 4, NOW(), 0),
(7, 1, 1, NOW(), 0);

-- ============================================
-- 11. 用户关注表 (t_user_follow) - 10 条数据
-- ============================================
INSERT INTO t_user_follow (user_id, follow_user_id, created_at, deleted) VALUES
(1, 2, NOW(), 0),
(1, 3, NOW(), 0),
(2, 1, NOW(), 0),
(2, 4, NOW(), 0),
(3, 1, NOW(), 0),
(3, 5, NOW(), 0),
(4, 2, NOW(), 0),
(5, 3, NOW(), 0),
(6, 1, NOW(), 0),
(7, 4, NOW(), 0);

-- ============================================
-- 12. 通知表 (t_notification) - 12 条数据
-- ============================================
INSERT INTO t_notification (user_id, type, title, content, target_type, target_id, from_user_id, is_read, created_at, deleted) VALUES
(1, 1, '您的帖子获得了点赞', '用户 樱花飞舞 赞成了您的帖子《分享我的考研经验》', 1, 1, 2, 0, NOW(), 0),
(2, 1, '您的帖子获得了点赞', '用户 清风徐来 赞成了您的帖子《学校周边美食探店》', 1, 2, 3, 1, NOW(), 0),
(3, 2, '您的评论收到了回复', '用户 天城夏树 回复了您的评论', 2, 5, 1, 0, NOW(), 0),
(4, 3, '您有了新的关注者', '用户 星辰大海 关注了您', 3, NULL, 4, 1, NOW(), 0),
(5, 1, '您的帖子获得了点赞', '用户 阳光少年 赞成了您的帖子《校园摄影作品展示》', 1, 4, 5, 0, NOW(), 0),
(6, 4, '系统通知', '欢迎使用学生优惠分享平台！', NULL, NULL, NULL, 1, NOW(), 0),
(7, 1, '您的帖子获得了点赞', '用户 明月几时有 赞成了您的帖子《学生会招新啦！》', 1, 6, 6, 0, NOW(), 0),
(8, 2, '您的评论收到了回复', '用户 科技达人 回复了您的评论', 2, 11, 7, 1, NOW(), 0),
(1, 5, '您的优惠信息已上架', '您发布的优惠《食堂二楼新开业！全场 8 折优惠》已上架', 5, 1, NULL, 1, NOW(), 0),
(2, 5, '您的优惠信息已上架', '您发布的优惠《图书馆咖啡厅买一送一》已上架', 5, 2, NULL, 1, NOW(), 0),
(3, 6, '私信消息', '用户 美食探索者 给您发送了一条私信', 6, NULL, 8, 0, NOW(), 0),
(4, 1, '您的帖子获得了点赞', '用户 天城夏树 赞成了您的帖子《求助：这道题怎么做？》', 1, 5, 1, 0, NOW(), 0);

-- ============================================
-- 13. 私信表 (t_private_message) - 8 条数据
-- ============================================
INSERT INTO t_private_message (sender_id, receiver_id, content, image_urls, is_read, created_at, deleted) VALUES
(1, 2, '你好，请问你的教材还在吗？', NULL, 1, NOW(), 0),
(2, 1, '还在的，有需要可以联系', NULL, 1, NOW(), 0),
(3, 4, '你的摄影作品真棒！能请教一下技巧吗？', NULL, 0, NOW(), 0),
(5, 6, '周末有空一起打球吗？', NULL, 1, NOW(), 0),
(6, 5, '好啊，周六下午怎么样？', NULL, 0, NOW(), 0),
(7, 8, '听说你知道哪里有好吃的？推荐一下', NULL, 1, NOW(), 0),
(8, 7, '东门旁边新开了一家火锅店不错', NULL, 1, NOW(), 0),
(1, 3, '考研资料可以便宜点吗？', NULL, 0, NOW(), 0);

-- ============================================
-- 14. 操作日志表 (t_operation_log) - 10 条数据
-- ============================================
INSERT INTO t_operation_log (operation, module, method, params, result, ip, user_agent, time, status, created_at) VALUES
('LOGIN', '用户管理', 'POST', '{"username":"user1","password":"***"}', '登录成功', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64)', 156, 1, '2026-04-27 10:30:00'),
('CREATE', '优惠管理', 'POST', '{"title":"食堂二楼新开业！全场 8 折优惠"}', '创建成功', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64)', 234, 1, '2026-04-27 10:35:00'),
('UPDATE', '用户管理', 'PUT', '{"nickname":"天城夏树","avatar":"1231231231.png"}', '更新成功', '127.0.0.1', 'Mozilla/5.0 (X11; Linux x86_64)', 189, 1, '2026-04-27 11:00:00'),
('DELETE', '帖子管理', 'DELETE', '{"id":100}', '删除成功', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64)', 78, 1, '2026-04-27 11:15:00'),
('CREATE', '帖子管理', 'POST', '{"title":"分享我的考研经验"}', '创建成功', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64)', 345, 1, '2026-04-27 11:30:00'),
('FOLLOW', '用户管理', 'POST', '{"followeeId":2}', '关注成功', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64)', 123, 1, '2026-04-27 11:45:00'),
('LIKE', '帖子管理', 'POST', '{"targetId":1}', '点赞成功', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64)', 56, 1, '2026-04-27 12:00:00'),
('CREATE', '评论管理', 'POST', '{"content":"感谢学长分享，很有用！"}', '创建成功', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64)', 167, 1, '2026-04-27 12:15:00'),
('SEND', '消息管理', 'POST', '{"receiverId":2,"content":"你好"}', '发送成功', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64)', 89, 1, '2026-04-27 12:30:00'),
('UPDATE', '优惠管理', 'PUT', '{"id":1,"status":0}', '更新成功', '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64)', 234, 1, '2026-04-27 12:45:00');

-- ============================================
-- 15. 系统配置表 (t_system_config) - 6 条数据
-- ============================================
INSERT INTO t_system_config (config_key, config_value, config_type, description, created_at, updated_at) VALUES
('site.name', '学生优惠分享平台', 'BASIC', '网站名称', NOW(), NOW()),
('site.logo', '/images/logo.png', 'BASIC', '网站 Logo', NOW(), NOW()),
('site.description', '专注校园优惠分享，为学生省钱', 'BASIC', '网站描述', NOW(), NOW()),
('user.register.enabled', 'true', 'USER', '是否允许注册', NOW(), NOW()),
('user.avatar.max_size', '2097152', 'USER', '头像最大大小 (字节)', NOW(), NOW()),
('content.audit.enabled', 'false', 'CONTENT', '是否启用内容审核', NOW(), NOW());

-- ============================================
-- 16. 敏感词表 (t_sensitive_word) - 8 条数据
-- ============================================
INSERT INTO t_sensitive_word (word, type, status, created_at) VALUES
('广告', 1, 1, NOW()),
('推销', 1, 1, NOW()),
('兼职刷单', 2, 1, NOW()),
('赌博', 2, 1, NOW()),
('色情', 2, 1, NOW()),
('诈骗', 2, 1, NOW()),
('违禁品', 2, 1, NOW()),
('作弊', 2, 1, NOW());

-- ============================================
-- 17. 帖子话题关联表 (t_post_topic) - 10 条数据
-- ============================================
INSERT INTO t_post_topic (post_id, topic_id, created_at) VALUES
(1, 1, NOW()),
(2, 2, NOW()),
(3, 3, NOW()),
(4, 4, NOW()),
(5, 4, NOW()),
(6, 5, NOW()),
(7, 5, NOW()),
(8, 4, NOW()),
(9, 5, NOW()),
(10, 4, NOW());

-- ============================================
-- 18. 用户历史表 (t_user_history) - 8 条数据
-- ============================================
-- 说明：该表只记录用户行为的目标类型和目标 ID，具体行为类型通过 target_type 区分
-- target_type: 1=帖子，2=优惠，3=用户
INSERT INTO t_user_history (user_id, target_type, target_id, created_at, deleted) VALUES
(1, 1, 1, '2026-04-27 10:30:00', 0),  -- 用户 1 浏览/操作帖子 1
(2, 1, 2, '2026-04-27 10:35:00', 0),  -- 用户 2 浏览/操作帖子 2
(3, 1, 1, '2026-04-27 10:40:00', 0),  -- 用户 3 点赞帖子 1
(4, 1, 4, '2026-04-27 10:45:00', 0),  -- 用户 4 收藏帖子 4
(5, 2, 1, '2026-04-27 10:50:00', 0),  -- 用户 5 发布优惠 1
(6, 3, 1, '2026-04-27 10:55:00', 0),  -- 用户 6 关注用户 1
(7, 1, 2, '2026-04-27 11:00:00', 0),  -- 用户 7 评论帖子 2
(8, 1, 6, '2026-04-27 11:05:00', 0);  -- 用户 8 浏览/操作帖子 6

-- 恢复外键检查
SET FOREIGN_KEY_CHECKS = 1;

-- ============================================
-- 测试数据插入完成
-- 共 18 张表，总计 191 条测试数据
-- ============================================
