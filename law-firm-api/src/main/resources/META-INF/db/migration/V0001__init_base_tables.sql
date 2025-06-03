-- API层基础表结构初始化
-- 版本: V0001
-- 模块: API (系统基础层)
-- 创建时间: 2023-06-01
-- 说明: 系统核心基础表，为所有业务模块提供基础支撑
-- 包括：系统配置、数据字典、日志记录、文件存储等基础功能

-- 设置字符集和数据库选项
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ======================= 系统配置表 =======================

-- sys_config表（系统配置表）
DROP TABLE IF EXISTS sys_config;
CREATE TABLE sys_config (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '配置ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    config_key VARCHAR(100) NOT NULL COMMENT '配置键',
    config_value TEXT COMMENT '配置值',
    config_type TINYINT DEFAULT 1 COMMENT '配置类型(1-字符串,2-数字,3-布尔,4-JSON)',
    group_name VARCHAR(50) COMMENT '分组名称',
    description VARCHAR(255) COMMENT '配置描述',
    is_system TINYINT DEFAULT 0 COMMENT '是否系统配置(0-否,1-是)',
    is_encrypted TINYINT DEFAULT 0 COMMENT '是否加密存储(0-否,1-是)',
    sort INT DEFAULT 0 COMMENT '排序号',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    UNIQUE KEY uk_tenant_config_key (tenant_id, config_key),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_group_name (group_name),
    INDEX idx_is_system (is_system),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统配置表';

-- ======================= 数据字典表 =======================

-- sys_dict_type表（字典类型表）
DROP TABLE IF EXISTS sys_dict_type;
CREATE TABLE sys_dict_type (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '字典类型ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    dict_name VARCHAR(100) NOT NULL COMMENT '字典名称',
    dict_type VARCHAR(100) NOT NULL COMMENT '字典类型',
    description VARCHAR(255) COMMENT '字典描述',
    is_system TINYINT DEFAULT 0 COMMENT '是否系统字典(0-否,1-是)',
    sort INT DEFAULT 0 COMMENT '排序号',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    UNIQUE KEY uk_dict_type (dict_type),
    UNIQUE KEY uk_tenant_dict_type (tenant_id, dict_type),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_is_system (is_system),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='字典类型表';

-- sys_dict_data表（字典数据表）
DROP TABLE IF EXISTS sys_dict_data;
CREATE TABLE sys_dict_data (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '字典数据ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    dict_type VARCHAR(100) NOT NULL COMMENT '字典类型',
    dict_label VARCHAR(100) NOT NULL COMMENT '字典标签',
    dict_value VARCHAR(100) NOT NULL COMMENT '字典值',
    dict_sort INT DEFAULT 0 COMMENT '字典排序',
    is_default TINYINT DEFAULT 0 COMMENT '是否默认(0-否,1-是)',
    css_class VARCHAR(100) COMMENT 'CSS样式类',
    list_class VARCHAR(100) COMMENT '表格样式类',
    tag_type VARCHAR(20) COMMENT '标签类型',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_dict_type (dict_type),
    INDEX idx_dict_sort (dict_sort),
    INDEX idx_status (status),
    
    CONSTRAINT fk_dict_data_type FOREIGN KEY (dict_type) REFERENCES sys_dict_type(dict_type) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='字典数据表';

-- ======================= 系统日志表 =======================

-- sys_login_log表（登录日志表）
DROP TABLE IF EXISTS sys_login_log;
CREATE TABLE sys_login_log (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '日志ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    user_id BIGINT COMMENT '用户ID',
    username VARCHAR(50) COMMENT '用户名',
    login_type TINYINT DEFAULT 1 COMMENT '登录类型(1-账号密码,2-手机验证码,3-第三方)',
    login_status TINYINT COMMENT '登录状态(0-失败,1-成功,2-登出)',
    login_ip VARCHAR(45) COMMENT '登录IP(支持IPv6)',
    login_location VARCHAR(100) COMMENT '登录地点',
    user_agent TEXT COMMENT '用户代理信息',
    browser VARCHAR(50) COMMENT '浏览器',
    os VARCHAR(50) COMMENT '操作系统',
    device_type VARCHAR(20) COMMENT '设备类型',
    session_id VARCHAR(100) COMMENT '会话ID',
    login_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '登录时间',
    logout_time DATETIME COMMENT '登出时间',
    online_duration INT COMMENT '在线时长(秒)',
    failure_reason VARCHAR(255) COMMENT '失败原因',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_user_id (user_id),
    INDEX idx_username (username),
    INDEX idx_login_status (login_status),
    INDEX idx_login_time (login_time),
    INDEX idx_login_ip (login_ip)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='登录日志表';

-- sys_operation_log表（操作日志表）
DROP TABLE IF EXISTS sys_operation_log;
CREATE TABLE sys_operation_log (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '日志ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    user_id BIGINT COMMENT '操作用户ID',
    username VARCHAR(50) COMMENT '操作用户名',
    operation_type VARCHAR(20) COMMENT '操作类型(CREATE,UPDATE,DELETE,QUERY)',
    module VARCHAR(50) COMMENT '操作模块',
    business_type VARCHAR(50) COMMENT '业务类型',
    business_id VARCHAR(100) COMMENT '业务ID',
    operation_desc VARCHAR(255) COMMENT '操作描述',
    request_method VARCHAR(10) COMMENT '请求方法',
    request_url VARCHAR(255) COMMENT '请求URL',
    request_params TEXT COMMENT '请求参数',
    response_result TEXT COMMENT '响应结果',
    operation_status TINYINT DEFAULT 1 COMMENT '操作状态(0-失败,1-成功)',
    error_msg TEXT COMMENT '错误信息',
    operation_ip VARCHAR(45) COMMENT '操作IP',
    operation_location VARCHAR(100) COMMENT '操作地点',
    user_agent TEXT COMMENT '用户代理',
    execution_time INT COMMENT '执行时长(毫秒)',
    operation_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_user_id (user_id),
    INDEX idx_username (username),
    INDEX idx_operation_type (operation_type),
    INDEX idx_module (module),
    INDEX idx_business_type (business_type),
    INDEX idx_operation_status (operation_status),
    INDEX idx_operation_time (operation_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='操作日志表';

-- ======================= 文件存储表 =======================

-- storage_bucket表（存储桶表）
DROP TABLE IF EXISTS storage_bucket;
CREATE TABLE storage_bucket (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '存储桶ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    bucket_name VARCHAR(100) NOT NULL COMMENT '存储桶名称',
    storage_type TINYINT NOT NULL COMMENT '存储类型(1-本地,2-MinIO,3-阿里云OSS,4-腾讯云COS,5-AWS S3)',
    endpoint VARCHAR(255) COMMENT '存储端点',
    access_key VARCHAR(100) COMMENT '访问密钥',
    secret_key VARCHAR(255) COMMENT '密钥',
    region VARCHAR(50) COMMENT '区域',
    bucket_policy TEXT COMMENT '存储桶策略',
    is_default TINYINT DEFAULT 0 COMMENT '是否默认(0-否,1-是)',
    is_public TINYINT DEFAULT 0 COMMENT '是否公开(0-否,1-是)',
    max_file_size BIGINT DEFAULT 104857600 COMMENT '最大文件大小(字节)',
    allowed_extensions TEXT COMMENT '允许的文件扩展名(JSON格式)',
    sort INT DEFAULT 0 COMMENT '排序号',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    UNIQUE KEY uk_tenant_bucket_name (tenant_id, bucket_name),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_storage_type (storage_type),
    INDEX idx_is_default (is_default),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='存储桶表';

-- storage_file表（文件信息表）
DROP TABLE IF EXISTS storage_file;
CREATE TABLE storage_file (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '文件ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    bucket_id BIGINT NOT NULL COMMENT '存储桶ID',
    file_name VARCHAR(255) NOT NULL COMMENT '文件名称',
    original_name VARCHAR(255) NOT NULL COMMENT '原始文件名',
    file_path VARCHAR(500) NOT NULL COMMENT '文件路径',
    file_url VARCHAR(500) COMMENT '文件访问URL',
    file_size BIGINT NOT NULL COMMENT '文件大小(字节)',
    file_type VARCHAR(50) COMMENT '文件类型',
    file_extension VARCHAR(20) COMMENT '文件扩展名',
    mime_type VARCHAR(100) COMMENT 'MIME类型',
    md5_hash VARCHAR(32) COMMENT 'MD5哈希值',
    sha1_hash VARCHAR(40) COMMENT 'SHA1哈希值',
    upload_user_id BIGINT COMMENT '上传用户ID',
    upload_ip VARCHAR(45) COMMENT '上传IP',
    business_type VARCHAR(50) COMMENT '业务类型',
    business_id VARCHAR(100) COMMENT '业务ID',
    is_temp TINYINT DEFAULT 0 COMMENT '是否临时文件(0-否,1-是)',
    expire_time DATETIME COMMENT '过期时间',
    download_count INT DEFAULT 0 COMMENT '下载次数',
    last_access_time DATETIME COMMENT '最后访问时间',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_bucket_id (bucket_id),
    INDEX idx_file_name (file_name),
    INDEX idx_original_name (original_name),
    INDEX idx_file_type (file_type),
    INDEX idx_md5_hash (md5_hash),
    INDEX idx_upload_user_id (upload_user_id),
    INDEX idx_business_type_id (business_type, business_id),
    INDEX idx_is_temp (is_temp),
    INDEX idx_status (status),
    
    CONSTRAINT fk_storage_file_bucket FOREIGN KEY (bucket_id) REFERENCES storage_bucket(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='文件信息表';

-- ======================= 系统监控表 =======================

-- sys_server_monitor表（服务器监控表）
DROP TABLE IF EXISTS sys_server_monitor;
CREATE TABLE sys_server_monitor (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '监控ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    server_name VARCHAR(100) NOT NULL COMMENT '服务器名称',
    server_ip VARCHAR(45) NOT NULL COMMENT '服务器IP',
    cpu_usage DECIMAL(5,2) COMMENT 'CPU使用率',
    memory_usage DECIMAL(5,2) COMMENT '内存使用率',
    disk_usage DECIMAL(5,2) COMMENT '磁盘使用率',
    network_rx BIGINT COMMENT '网络入流量(字节)',
    network_tx BIGINT COMMENT '网络出流量(字节)',
    server_load VARCHAR(100) COMMENT '系统负载',
    monitor_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '监控时间',
    version INT DEFAULT 0 COMMENT '版本号',
    status INT DEFAULT 0 COMMENT '状态',
    sort INT DEFAULT 0 COMMENT '排序号',
    deleted INT DEFAULT 0 COMMENT '删除标记',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_server_ip (server_ip),
    INDEX idx_monitor_time (monitor_time),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='服务器监控表';

-- 恢复外键约束
SET FOREIGN_KEY_CHECKS = 1; 