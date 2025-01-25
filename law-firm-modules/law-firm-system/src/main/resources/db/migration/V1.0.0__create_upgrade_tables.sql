-- 创建系统升级包表
CREATE TABLE IF NOT EXISTS sys_upgrade_package (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT 'ID',
    package_name VARCHAR(100) NOT NULL COMMENT '升级包名称',
    version VARCHAR(20) NOT NULL COMMENT '目标版本号',
    description VARCHAR(500) COMMENT '升级说明',
    file_path VARCHAR(200) NOT NULL COMMENT '升级包路径',
    md5 VARCHAR(32) NOT NULL COMMENT '文件MD5',
    file_size BIGINT NOT NULL COMMENT '文件大小(字节)',
    status TINYINT NOT NULL DEFAULT 0 COMMENT '状态(0:待升级 1:升级中 2:升级成功 3:升级失败)',
    error_message VARCHAR(500) COMMENT '错误信息',
    create_by VARCHAR(50) COMMENT '创建人',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by VARCHAR(50) COMMENT '更新人',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    del_flag TINYINT NOT NULL DEFAULT 0 COMMENT '删除标记(0:正常 1:已删除)',
    UNIQUE KEY uk_version (version)
) COMMENT '系统升级包';

-- 创建系统升级日志表
CREATE TABLE IF NOT EXISTS sys_upgrade_log (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT 'ID',
    package_id BIGINT NOT NULL COMMENT '升级包ID',
    operation VARCHAR(50) NOT NULL COMMENT '操作类型',
    detail VARCHAR(500) NOT NULL COMMENT '详细信息',
    success TINYINT NOT NULL COMMENT '是否成功',
    error_message VARCHAR(500) COMMENT '错误信息',
    create_by VARCHAR(50) COMMENT '创建人',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by VARCHAR(50) COMMENT '更新人',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    del_flag TINYINT NOT NULL DEFAULT 0 COMMENT '删除标记(0:正常 1:已删除)',
    KEY idx_package_id (package_id)
) COMMENT '系统升级日志'; 