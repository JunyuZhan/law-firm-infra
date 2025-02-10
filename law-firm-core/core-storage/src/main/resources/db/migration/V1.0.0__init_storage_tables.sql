-- 文件存储记录表
CREATE TABLE IF NOT EXISTS storage_file (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    file_name VARCHAR(255) NOT NULL COMMENT '文件名',
    file_path VARCHAR(500) NOT NULL COMMENT '存储路径',
    file_size BIGINT NOT NULL COMMENT '文件大小(字节)',
    file_type VARCHAR(100) COMMENT '文件类型',
    md5 VARCHAR(32) COMMENT '文件MD5',
    upload_time DATETIME NOT NULL COMMENT '上传时间',
    uploader_id BIGINT COMMENT '上传者ID',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态：1-正常，0-已删除',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY uk_file_path (file_path)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文件存储记录表';

-- 文件分片上传记录表
CREATE TABLE IF NOT EXISTS storage_multipart_upload (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    upload_id VARCHAR(100) NOT NULL COMMENT '分片上传ID',
    file_name VARCHAR(255) NOT NULL COMMENT '文件名',
    total_size BIGINT NOT NULL COMMENT '文件总大小',
    chunk_size INT NOT NULL COMMENT '分片大小',
    total_chunks INT NOT NULL COMMENT '总分片数',
    status TINYINT NOT NULL DEFAULT 0 COMMENT '状态：0-进行中，1-已完成，2-已取消',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY uk_upload_id (upload_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='分片上传记录表';

-- 文件分片记录表
CREATE TABLE IF NOT EXISTS storage_chunk (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    upload_id VARCHAR(100) NOT NULL COMMENT '分片上传ID',
    chunk_number INT NOT NULL COMMENT '分片序号',
    chunk_size BIGINT NOT NULL COMMENT '分片大小',
    etag VARCHAR(100) COMMENT '分片ETag',
    status TINYINT NOT NULL DEFAULT 0 COMMENT '状态：0-未上传，1-已上传',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY uk_upload_chunk (upload_id, chunk_number)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文件分片记录表';

-- 文件预览记录表
CREATE TABLE IF NOT EXISTS storage_preview (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    file_id BIGINT NOT NULL COMMENT '关联的文件ID',
    preview_path VARCHAR(500) NOT NULL COMMENT '预览文件路径',
    preview_type VARCHAR(50) NOT NULL COMMENT '预览类型',
    status TINYINT NOT NULL DEFAULT 1 COMMENT '状态：1-正常，0-已删除',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY uk_file_preview (file_id, preview_type),
    FOREIGN KEY (file_id) REFERENCES storage_file(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文件预览记录表';

-- 异步任务记录表
CREATE TABLE IF NOT EXISTS storage_async_task (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    task_id VARCHAR(100) NOT NULL COMMENT '任务ID',
    task_type VARCHAR(50) NOT NULL COMMENT '任务类型',
    file_id BIGINT COMMENT '关联的文件ID',
    status TINYINT NOT NULL DEFAULT 0 COMMENT '状态：0-进行中，1-已完成，2-已失败',
    error_message TEXT COMMENT '错误信息',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY uk_task_id (task_id),
    FOREIGN KEY (file_id) REFERENCES storage_file(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='异步任务记录表'; 