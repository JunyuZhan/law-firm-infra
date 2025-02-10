-- 搜索配置表
CREATE TABLE IF NOT EXISTS search_config (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    index_name VARCHAR(100) NOT NULL COMMENT '索引名称',
    alias_name VARCHAR(100) COMMENT '索引别名',
    mapping TEXT NOT NULL COMMENT '索引mapping配置',
    settings TEXT NOT NULL COMMENT '索引settings配置',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态：1-启用，0-禁用',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY uk_index_name (index_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='搜索配置表';

-- 搜索历史记录表
CREATE TABLE IF NOT EXISTS search_history (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL COMMENT '用户ID',
    keyword VARCHAR(500) NOT NULL COMMENT '搜索关键词',
    index_name VARCHAR(100) NOT NULL COMMENT '搜索的索引',
    filters TEXT COMMENT '过滤条件(JSON)',
    total_hits BIGINT NOT NULL DEFAULT 0 COMMENT '搜索结果数',
    search_time BIGINT NOT NULL COMMENT '搜索耗时(毫秒)',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uk_user_search (user_id, keyword, index_name, created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='搜索历史记录表';

-- 搜索热词表
CREATE TABLE IF NOT EXISTS search_hot_keyword (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    keyword VARCHAR(100) NOT NULL COMMENT '热搜关键词',
    index_name VARCHAR(100) NOT NULL COMMENT '索引名称',
    search_count BIGINT NOT NULL DEFAULT 0 COMMENT '搜索次数',
    last_search_at DATETIME NOT NULL COMMENT '最后搜索时间',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY uk_keyword_index (keyword, index_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='搜索热词表';

-- 索引同步记录表
CREATE TABLE IF NOT EXISTS search_sync_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    index_name VARCHAR(100) NOT NULL COMMENT '索引名称',
    sync_type VARCHAR(50) NOT NULL COMMENT '同步类型：FULL-全量，INCREMENTAL-增量',
    start_time DATETIME NOT NULL COMMENT '开始时间',
    end_time DATETIME COMMENT '结束时间',
    total_count BIGINT NOT NULL DEFAULT 0 COMMENT '总记录数',
    success_count BIGINT NOT NULL DEFAULT 0 COMMENT '成功数',
    error_count BIGINT NOT NULL DEFAULT 0 COMMENT '失败数',
    status TINYINT NOT NULL DEFAULT 0 COMMENT '状态：0-进行中，1-成功，2-失败',
    error_message TEXT COMMENT '错误信息',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='索引同步记录表'; 