-- ============================================
-- 大学生资源信息分享平台 - 数据库初始化脚本
-- 数据库版本：MariaDB 10.6+ / MySQL 8.0+
-- 字符集：utf8mb4
-- 排序规则：utf8mb4_general_ci
-- ============================================

-- 创建数据库
CREATE DATABASE IF NOT EXISTS student_deal_share 
DEFAULT CHARACTER SET utf8mb4 
DEFAULT COLLATE utf8mb4_general_ci;

-- 使用数据库
USE student_deal_share;

-- ============================================
-- 1. 用户相关表
-- ============================================

-- 1.1 用户表
DROP TABLE IF EXISTS t_user;
CREATE TABLE t_user (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '用户 ID',
    username VARCHAR(50) NOT NULL COMMENT '用户名',
    password VARCHAR(100) NOT NULL COMMENT '密码 (加密)',
    nickname VARCHAR(50) DEFAULT NULL COMMENT '昵称',
    avatar VARCHAR(255) DEFAULT NULL COMMENT '头像 URL',
    gender TINYINT DEFAULT 0 COMMENT '性别 0-未知 1-男 2-女',
    phone VARCHAR(20) DEFAULT NULL COMMENT '手机号',
    email VARCHAR(100) DEFAULT NULL COMMENT '邮箱',
    school VARCHAR(100) DEFAULT NULL COMMENT '学校',
    college VARCHAR(100) DEFAULT NULL COMMENT '学院',
    student_id VARCHAR(50) DEFAULT NULL COMMENT '学号',
    level INT DEFAULT 1 COMMENT '用户等级',
    points INT DEFAULT 0 COMMENT '积分',
    status TINYINT DEFAULT 1 COMMENT '状态 0-禁用 1-正常',
    last_login_time DATETIME DEFAULT NULL COMMENT '最后登录时间',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '删除标识 0-未删除 1-已删除',
    PRIMARY KEY (id),
    UNIQUE KEY uk_username (username),
    UNIQUE KEY uk_phone (phone),
    UNIQUE KEY uk_email (email),
    KEY idx_nickname (nickname),
    KEY idx_school (school)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户表';

-- 1.2 用户关注表
DROP TABLE IF EXISTS t_user_follow;
CREATE TABLE t_user_follow (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键 ID',
    user_id BIGINT NOT NULL COMMENT '用户 ID',
    follow_user_id BIGINT NOT NULL COMMENT '被关注用户 ID',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    deleted TINYINT DEFAULT 0 COMMENT '删除标识 0-未删除 1-已删除',
    PRIMARY KEY (id),
    UNIQUE KEY uk_user_follow (user_id, follow_user_id),
    KEY idx_follow_user_id (follow_user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户关注表';

-- 1.3 用户收藏表
DROP TABLE IF EXISTS t_user_favorite;
CREATE TABLE t_user_favorite (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键 ID',
    user_id BIGINT NOT NULL COMMENT '用户 ID',
    target_type TINYINT NOT NULL COMMENT '目标类型 1-优惠 2-帖子',
    target_id BIGINT NOT NULL COMMENT '目标 ID',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    deleted TINYINT DEFAULT 0 COMMENT '删除标识 0-未删除 1-已删除',
    PRIMARY KEY (id),
    UNIQUE KEY uk_user_target (user_id, target_type, target_id, deleted),
    KEY idx_target (target_type, target_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户收藏表';

-- 1.4 用户浏览历史表
DROP TABLE IF EXISTS t_user_history;
CREATE TABLE t_user_history (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键 ID',
    user_id BIGINT NOT NULL COMMENT '用户 ID',
    target_type TINYINT NOT NULL COMMENT '目标类型 1-优惠 2-帖子',
    target_id BIGINT NOT NULL COMMENT '目标 ID',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    deleted TINYINT DEFAULT 0 COMMENT '删除标识 0-未删除 1-已删除',
    PRIMARY KEY (id),
    KEY idx_user (user_id),
    KEY idx_target (target_type, target_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户浏览历史表';

-- ============================================
-- 2. 优惠相关表
-- ============================================

-- 2.1 优惠信息表
DROP TABLE IF EXISTS t_deal;
CREATE TABLE t_deal (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '优惠 ID',
    title VARCHAR(200) NOT NULL COMMENT '标题',
    description TEXT COMMENT '描述',
    category_id BIGINT DEFAULT NULL COMMENT '分类 ID',
    user_id BIGINT NOT NULL COMMENT '发布用户 ID',
    type TINYINT DEFAULT 1 COMMENT '类型 1-预售 2-折扣 3-优惠券 4-限时抢购 5-其他',
    image_urls TEXT COMMENT '图片 URL 列表 (JSON)',
    video_url VARCHAR(255) DEFAULT NULL COMMENT '视频 URL',
    price DECIMAL(10,2) DEFAULT NULL COMMENT '原价',
    deal_price DECIMAL(10,2) DEFAULT NULL COMMENT '优惠价格',
    discount VARCHAR(20) DEFAULT NULL COMMENT '折扣力度',
    start_time DATETIME DEFAULT NULL COMMENT '开始时间',
    end_time DATETIME DEFAULT NULL COMMENT '结束时间',
    url VARCHAR(500) DEFAULT NULL COMMENT '活动链接',
    qr_code VARCHAR(255) DEFAULT NULL COMMENT '二维码 URL',
    tags VARCHAR(200) DEFAULT NULL COMMENT '标签 (逗号分隔)',
    status TINYINT DEFAULT 1 COMMENT '状态 0-下架 1-上架 2-审核中',
    view_count INT DEFAULT 0 COMMENT '浏览次数',
    favorite_count INT DEFAULT 0 COMMENT '收藏次数',
    like_count INT DEFAULT 0 COMMENT '点赞次数',
    comment_count INT DEFAULT 0 COMMENT '评论次数',
    share_count INT DEFAULT 0 COMMENT '分享次数',
    top TINYINT DEFAULT 0 COMMENT '是否置顶 0-否 1-是',
    recommend TINYINT DEFAULT 0 COMMENT '是否推荐 0-否 1-是',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '删除标识 0-未删除 1-已删除',
    PRIMARY KEY (id),
    KEY idx_category_id (category_id),
    KEY idx_user_id (user_id),
    KEY idx_type (type),
    KEY idx_status (status),
    KEY idx_start_time (start_time),
    KEY idx_end_time (end_time),
    KEY idx_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='优惠信息表';

-- 2.2 优惠分类表
DROP TABLE IF EXISTS t_category;
CREATE TABLE t_category (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '分类 ID',
    name VARCHAR(50) NOT NULL COMMENT '分类名称',
    parent_id BIGINT DEFAULT 0 COMMENT '父分类 ID',
    icon VARCHAR(255) DEFAULT NULL COMMENT '图标',
    sort INT DEFAULT 0 COMMENT '排序',
    level INT DEFAULT 1 COMMENT '层级',
    status TINYINT DEFAULT 1 COMMENT '状态 0-禁用 1-启用',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '删除标识 0-未删除 1-已删除',
    PRIMARY KEY (id),
    KEY idx_parent_id (parent_id),
    KEY idx_sort (sort)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='优惠分类表';

-- 2.3 优惠标签表
DROP TABLE IF EXISTS t_tag;
CREATE TABLE t_tag (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '标签 ID',
    name VARCHAR(50) NOT NULL COMMENT '标签名称',
    type TINYINT DEFAULT 1 COMMENT '类型 1-系统 2-用户',
    use_count INT DEFAULT 0 COMMENT '使用次数',
    status TINYINT DEFAULT 1 COMMENT '状态 0-禁用 1-启用',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_name (name),
    KEY idx_type (type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='优惠标签表';

-- ============================================
-- 3. 社区相关表
-- ============================================

-- 3.1 帖子表
DROP TABLE IF EXISTS t_post;
CREATE TABLE t_post (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '帖子 ID',
    title VARCHAR(200) DEFAULT NULL COMMENT '标题',
    content TEXT NOT NULL COMMENT '内容',
    user_id BIGINT NOT NULL COMMENT '发布用户 ID',
    category_id BIGINT DEFAULT NULL COMMENT '分类 ID',
    image_urls TEXT COMMENT '图片 URL 列表 (JSON)',
    video_urls TEXT COMMENT '视频 URL 列表 (JSON)',
    tags VARCHAR(200) DEFAULT NULL COMMENT '标签 (逗号分隔)',
    type TINYINT DEFAULT 1 COMMENT '类型 1-图文 2-视频',
    status TINYINT DEFAULT 1 COMMENT '状态 0-删除 1-正常 2-审核中',
    view_count INT DEFAULT 0 COMMENT '浏览次数',
    favorite_count INT DEFAULT 0 COMMENT '收藏次数',
    like_count INT DEFAULT 0 COMMENT '点赞次数',
    comment_count INT DEFAULT 0 COMMENT '评论次数',
    share_count INT DEFAULT 0 COMMENT '分享次数',
    top TINYINT DEFAULT 0 COMMENT '是否置顶 0-否 1-是',
    essence TINYINT DEFAULT 0 COMMENT '是否精华 0-否 1-是',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '删除标识 0-未删除 1-已删除',
    PRIMARY KEY (id),
    KEY idx_user_id (user_id),
    KEY idx_category_id (category_id),
    KEY idx_status (status),
    KEY idx_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='帖子表';

-- 3.2 评论表
DROP TABLE IF EXISTS t_comment;
CREATE TABLE t_comment (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '评论 ID',
    user_id BIGINT NOT NULL COMMENT '用户 ID',
    target_type TINYINT NOT NULL COMMENT '目标类型 1-优惠 2-帖子',
    target_id BIGINT NOT NULL COMMENT '目标 ID',
    parent_id BIGINT DEFAULT 0 COMMENT '父评论 ID',
    reply_user_id BIGINT DEFAULT NULL COMMENT '回复的用户 ID',
    content VARCHAR(1000) NOT NULL COMMENT '内容',
    image_urls TEXT COMMENT '图片 URL 列表 (JSON)',
    like_count INT DEFAULT 0 COMMENT '点赞次数',
    status TINYINT DEFAULT 1 COMMENT '状态 0-删除 1-正常',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '删除标识 0-未删除 1-已删除',
    PRIMARY KEY (id),
    KEY idx_target (target_type, target_id),
    KEY idx_user_id (user_id),
    KEY idx_parent_id (parent_id),
    KEY idx_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='评论表';

-- 3.3 点赞记录表
DROP TABLE IF EXISTS t_like_record;
CREATE TABLE t_like_record (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键 ID',
    user_id BIGINT NOT NULL COMMENT '用户 ID',
    target_type TINYINT NOT NULL COMMENT '目标类型 1-优惠 2-帖子 3-评论',
    target_id BIGINT NOT NULL COMMENT '目标 ID',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_user_target (user_id, target_type, target_id),
    KEY idx_target (target_type, target_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='点赞记录表';

-- 3.4 话题表
DROP TABLE IF EXISTS t_topic;
CREATE TABLE t_topic (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '话题 ID',
    name VARCHAR(100) NOT NULL COMMENT '话题名称',
    description VARCHAR(500) DEFAULT NULL COMMENT '话题描述',
    cover_image VARCHAR(255) DEFAULT NULL COMMENT '封面图片',
    post_count INT DEFAULT 0 COMMENT '帖子数量',
    view_count INT DEFAULT 0 COMMENT '浏览次数',
    status TINYINT DEFAULT 1 COMMENT '状态 0-禁用 1-启用',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_name (name),
    KEY idx_post_count (post_count)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='话题表';

-- 3.5 帖子话题关联表
DROP TABLE IF EXISTS t_post_topic;
CREATE TABLE t_post_topic (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键 ID',
    post_id BIGINT NOT NULL COMMENT '帖子 ID',
    topic_id BIGINT NOT NULL COMMENT '话题 ID',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_post_topic (post_id, topic_id),
    KEY idx_topic_id (topic_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='帖子话题关联表';

-- ============================================
-- 4. 消息相关表
-- ============================================

-- 4.1 消息通知表
DROP TABLE IF EXISTS t_notification;
CREATE TABLE t_notification (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '消息 ID',
    user_id BIGINT NOT NULL COMMENT '接收用户 ID',
    type TINYINT NOT NULL COMMENT '类型 1-系统 2-点赞 3-评论 4-回复 5-关注 6-私信',
    title VARCHAR(200) DEFAULT NULL COMMENT '标题',
    content VARCHAR(1000) NOT NULL COMMENT '内容',
    target_type TINYINT DEFAULT NULL COMMENT '目标类型 1-优惠 2-帖子 3-评论',
    target_id BIGINT DEFAULT NULL COMMENT '目标 ID',
    from_user_id BIGINT DEFAULT NULL COMMENT '发送用户 ID',
    is_read TINYINT DEFAULT 0 COMMENT '是否已读 0-未读 1-已读',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    deleted TINYINT DEFAULT 0 COMMENT '删除标识 0-未删除 1-已删除',
    PRIMARY KEY (id),
    KEY idx_user_id (user_id),
    KEY idx_type (type),
    KEY idx_is_read (is_read),
    KEY idx_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='消息通知表';

-- 4.2 私信表
DROP TABLE IF EXISTS t_private_message;
CREATE TABLE t_private_message (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '消息 ID',
    sender_id BIGINT NOT NULL COMMENT '发送者 ID',
    receiver_id BIGINT NOT NULL COMMENT '接收者 ID',
    content VARCHAR(1000) NOT NULL COMMENT '内容',
    image_urls TEXT COMMENT '图片 URL 列表 (JSON)',
    is_read TINYINT DEFAULT 0 COMMENT '是否已读 0-未读 1-已读',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    deleted TINYINT DEFAULT 0 COMMENT '删除标识 0-未删除 1-已删除',
    PRIMARY KEY (id),
    KEY idx_sender (sender_id),
    KEY idx_receiver (receiver_id),
    KEY idx_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='私信表';

-- ============================================
-- 5. 系统相关表
-- ============================================

-- 5.1 管理员表
DROP TABLE IF EXISTS t_admin;
CREATE TABLE t_admin (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '管理员 ID',
    username VARCHAR(50) NOT NULL COMMENT '用户名',
    password VARCHAR(100) NOT NULL COMMENT '密码 (加密)',
    nickname VARCHAR(50) DEFAULT NULL COMMENT '昵称',
    avatar VARCHAR(255) DEFAULT NULL COMMENT '头像',
    phone VARCHAR(20) DEFAULT NULL COMMENT '手机号',
    email VARCHAR(100) DEFAULT NULL COMMENT '邮箱',
    role TINYINT DEFAULT 1 COMMENT '角色 1-超级管理员 2-普通管理员',
    status TINYINT DEFAULT 1 COMMENT '状态 0-禁用 1-正常',
    last_login_time DATETIME DEFAULT NULL COMMENT '最后登录时间',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '删除标识 0-未删除 1-已删除',
    PRIMARY KEY (id),
    UNIQUE KEY uk_username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='管理员表';

-- 5.2 系统配置表
DROP TABLE IF EXISTS t_system_config;
CREATE TABLE t_system_config (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '配置 ID',
    config_key VARCHAR(100) NOT NULL COMMENT '配置键',
    config_value VARCHAR(500) DEFAULT NULL COMMENT '配置值',
    config_type VARCHAR(50) DEFAULT NULL COMMENT '配置类型',
    description VARCHAR(200) DEFAULT NULL COMMENT '描述',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_config_key (config_key)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='系统配置表';

-- 5.3 操作日志表
DROP TABLE IF EXISTS t_operation_log;
CREATE TABLE t_operation_log (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '日志 ID',
    user_id BIGINT DEFAULT NULL COMMENT '用户 ID',
    username VARCHAR(50) DEFAULT NULL COMMENT '用户名',
    operation VARCHAR(100) NOT NULL COMMENT '操作',
    module VARCHAR(50) DEFAULT NULL COMMENT '模块',
    method VARCHAR(100) DEFAULT NULL COMMENT '方法',
    params TEXT COMMENT '请求参数',
    result TEXT COMMENT '返回结果',
    ip VARCHAR(50) DEFAULT NULL COMMENT 'IP 地址',
    user_agent VARCHAR(500) DEFAULT NULL COMMENT '浏览器信息',
    time BIGINT DEFAULT NULL COMMENT '执行时间 (ms)',
    status TINYINT DEFAULT 1 COMMENT '状态 0-失败 1-成功',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (id),
    KEY idx_user_id (user_id),
    KEY idx_operation (operation),
    KEY idx_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='操作日志表';

-- 5.4 敏感词表
DROP TABLE IF EXISTS t_sensitive_word;
CREATE TABLE t_sensitive_word (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '敏感词 ID',
    word VARCHAR(100) NOT NULL COMMENT '敏感词',
    type TINYINT DEFAULT 1 COMMENT '类型 1-政治 2-色情 3-暴力 4-广告 5-其他',
    level TINYINT DEFAULT 1 COMMENT '等级 1-低 2-中 3-高',
    status TINYINT DEFAULT 1 COMMENT '状态 0-禁用 1-启用',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    KEY idx_type (type),
    KEY idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='敏感词表';

-- ============================================
-- 6. 初始化数据
-- ============================================

-- 6.1 默认管理员 (密码：admin123 BCrypt 加密)
INSERT INTO t_admin (id, username, password, nickname, role, status) 
VALUES (1, 'admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lqkkO9QS3TzCjH3rS', '系统管理员', 1, 1);

-- 6.2 默认分类
INSERT INTO t_category (id, name, parent_id, level, sort) VALUES
(1, '全部', 0, 1, 1),
(2, '电商优惠', 0, 1, 2),
(3, '外卖美食', 0, 1, 3),
(4, '教育培训', 0, 1, 4),
(5, '生活服务', 0, 1, 5),
(6, '数码产品', 0, 1, 6);

-- 6.3 系统配置
INSERT INTO t_system_config (config_key, config_value, config_type, description) VALUES
('app.name', '大学生资源信息分享平台', 'string', '应用名称'),
('app.version', '1.0.0', 'string', '应用版本'),
('user.default.points', '100', 'number', '用户默认积分'),
('deal.audit.enable', 'true', 'boolean', '优惠审核开关');

-- 6.4 系统标签
INSERT INTO t_tag (name, type, status) VALUES
('618', 1, 1),
('双 11', 1, 1),
('双 12', 1, 1),
('开学季', 1, 1),
('毕业季', 1, 1),
('满减', 1, 1),
('包邮', 1, 1),
('新人福利', 1, 1),
('限时抢购', 1, 1),
('优惠券', 1, 1);

-- ============================================
-- 7. 视图和索引优化（可选）
-- ============================================

-- 创建优惠信息统计视图
CREATE OR REPLACE VIEW v_deal_statistics AS
SELECT 
    d.id,
    d.title,
    d.type,
    d.status,
    d.view_count,
    d.favorite_count,
    d.like_count,
    d.comment_count,
    d.created_at,
    u.nickname AS user_nickname,
    c.name AS category_name
FROM t_deal d
LEFT JOIN t_user u ON d.user_id = u.id
LEFT JOIN t_category c ON d.category_id = c.id
WHERE d.deleted = 0;

-- 创建帖子统计视图
CREATE OR REPLACE VIEW v_post_statistics AS
SELECT 
    p.id,
    p.title,
    p.type,
    p.status,
    p.view_count,
    p.favorite_count,
    p.like_count,
    p.comment_count,
    p.created_at,
    u.nickname AS user_nickname
FROM t_post p
LEFT JOIN t_user u ON p.user_id = u.id
WHERE p.deleted = 0;

-- ============================================
-- 结束
-- ============================================

SELECT '数据库初始化完成！' AS message;
