-- 学校表
CREATE TABLE IF NOT EXISTS school (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    region VARCHAR(50) NOT NULL,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 用户表
CREATE TABLE IF NOT EXISTS user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(100) NOT NULL,
    phone VARCHAR(20) UNIQUE NOT NULL,
    wechat_openid VARCHAR(100) UNIQUE,
    student_status TINYINT DEFAULT 0 COMMENT '0: 未认证, 1: 已认证',
    school_id BIGINT,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (school_id) REFERENCES school(id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 活动表
CREATE TABLE IF NOT EXISTS activity (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    description TEXT NOT NULL,
    category VARCHAR(20) NOT NULL,
    source VARCHAR(50) NOT NULL,
    start_time DATETIME NOT NULL,
    end_time DATETIME NOT NULL,
    participate_way TEXT NOT NULL,
    link VARCHAR(255) NOT NULL,
    is_student_exclusive TINYINT DEFAULT 0 COMMENT '0: 非学生专属, 1: 学生专属',
    school_id BIGINT,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (school_id) REFERENCES school(id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 帖子表
CREATE TABLE IF NOT EXISTS post (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL,
    content TEXT NOT NULL,
    images VARCHAR(500),
    link VARCHAR(255),
    user_id BIGINT NOT NULL,
    like_count INT DEFAULT 0,
    comment_count INT DEFAULT 0,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 评论表
CREATE TABLE IF NOT EXISTS comment (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    post_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    content TEXT NOT NULL,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (post_id) REFERENCES post(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 收藏表
CREATE TABLE IF NOT EXISTS collection (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    activity_id BIGINT NOT NULL,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE,
    FOREIGN KEY (activity_id) REFERENCES activity(id) ON DELETE CASCADE,
    UNIQUE KEY uk_user_activity (user_id, activity_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 点赞表
CREATE TABLE IF NOT EXISTS `like` (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    post_id BIGINT NOT NULL,
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE,
    FOREIGN KEY (post_id) REFERENCES post(id) ON DELETE CASCADE,
    UNIQUE KEY uk_user_post (user_id, post_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 提醒表
CREATE TABLE IF NOT EXISTS reminder (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    activity_id BIGINT NOT NULL,
    reminder_time DATETIME NOT NULL,
    status TINYINT DEFAULT 0 COMMENT '0: 未提醒, 1: 已提醒',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE,
    FOREIGN KEY (activity_id) REFERENCES activity(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 创建索引
-- 用户表索引
CREATE INDEX idx_user_phone ON user(phone);
CREATE INDEX idx_user_wechat_openid ON user(wechat_openid);
CREATE INDEX idx_user_school_id ON user(school_id);

-- 活动表索引
CREATE INDEX idx_activity_category ON activity(category);
CREATE INDEX idx_activity_source ON activity(source);
CREATE INDEX idx_activity_start_time ON activity(start_time);
CREATE INDEX idx_activity_end_time ON activity(end_time);
CREATE INDEX idx_activity_school_id ON activity(school_id);

-- 帖子表索引
CREATE INDEX idx_post_user_id ON post(user_id);
CREATE INDEX idx_post_create_time ON post(create_time);

-- 评论表索引
CREATE INDEX idx_comment_post_id ON comment(post_id);
CREATE INDEX idx_comment_user_id ON comment(user_id);

-- 收藏表索引
CREATE INDEX idx_collection_user_id ON collection(user_id);
CREATE INDEX idx_collection_activity_id ON collection(activity_id);

-- 点赞表索引
CREATE INDEX idx_like_user_id ON `like`(user_id);
CREATE INDEX idx_like_post_id ON `like`(post_id);

-- 提醒表索引
CREATE INDEX idx_reminder_user_id ON reminder(user_id);
CREATE INDEX idx_reminder_activity_id ON reminder(activity_id);
CREATE INDEX idx_reminder_reminder_time ON reminder(reminder_time);
CREATE INDEX idx_reminder_status ON reminder(status);

-- 插入初始学校数据
INSERT INTO school (name, region) VALUES 
('清华大学', '北京'),
('北京大学', '北京'),
('复旦大学', '上海'),
('上海交通大学', '上海'),
('浙江大学', '浙江'),
('南京大学', '江苏'),
('中山大学', '广东'),
('华中科技大学', '湖北'),
('武汉大学', '湖北'),
('西安交通大学', '陕西');
