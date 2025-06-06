-- Auth模块表结构初始化
-- 版本: V2001
-- 模块: auth
-- 创建时间: 2023-06-10
-- 说明: 重新设计认证模块表结构，统一规范和约束

-- 设置字符集和数据库选项
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ======================= 认证模块核心表 =======================

-- auth_user表（用户表）
DROP TABLE IF EXISTS auth_user;
CREATE TABLE auth_user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '用户ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    tenant_code VARCHAR(50) COMMENT '租户编码',
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    password VARCHAR(255) NOT NULL COMMENT '密码(BCrypt加密)',
    email VARCHAR(100) UNIQUE COMMENT '邮箱',
    mobile VARCHAR(20) UNIQUE COMMENT '手机号',
    real_name VARCHAR(50) COMMENT '真实姓名',
    avatar VARCHAR(255) COMMENT '头像URL',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常,2-锁定)',
    employee_id BIGINT COMMENT '关联员工ID',
    last_login_time DATETIME COMMENT '最后登录时间',
    last_login_ip VARCHAR(45) COMMENT '最后登录IP(支持IPv6)',
    account_expire_time DATETIME COMMENT '账号过期时间',
    password_expire_time DATETIME COMMENT '密码过期时间',
    login_failure_count INT DEFAULT 0 COMMENT '连续登录失败次数',
    locked_until DATETIME COMMENT '锁定到期时间',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    sort INT DEFAULT 0 COMMENT '排序号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_status (status),
    INDEX idx_employee_id (employee_id),
    INDEX idx_last_login_time (last_login_time),
    INDEX idx_create_time (create_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

-- auth_role表（角色表）
DROP TABLE IF EXISTS auth_role;
CREATE TABLE auth_role (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '角色ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    tenant_code VARCHAR(50) COMMENT '租户编码',
    code VARCHAR(50) NOT NULL COMMENT '角色编码',
    name VARCHAR(50) NOT NULL COMMENT '角色名称',
    description VARCHAR(255) COMMENT '角色描述',
    data_scope TINYINT DEFAULT 1 COMMENT '数据权限范围(1-全部,2-部门,3-个人)',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    is_system TINYINT DEFAULT 0 COMMENT '是否系统角色(0-否,1-是)',
    sort INT DEFAULT 0 COMMENT '排序号',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    UNIQUE KEY uk_tenant_code (tenant_id, code),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_status (status),
    INDEX idx_sort (sort)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色表';

-- auth_permission表（权限表）
DROP TABLE IF EXISTS auth_permission;
CREATE TABLE auth_permission (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '权限ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    tenant_code VARCHAR(50) COMMENT '租户编码',
    parent_id BIGINT DEFAULT 0 COMMENT '父权限ID',
    code VARCHAR(100) NOT NULL COMMENT '权限编码',
    name VARCHAR(50) NOT NULL COMMENT '权限名称',
    type TINYINT DEFAULT 1 COMMENT '权限类型(1-菜单,2-按钮,3-接口)',
    path VARCHAR(255) COMMENT '路由路径',
    component VARCHAR(255) COMMENT '组件路径',
    icon VARCHAR(50) COMMENT '图标',
    method VARCHAR(10) COMMENT 'HTTP方法(GET,POST,PUT,DELETE)',
    url VARCHAR(255) COMMENT 'API接口地址',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    visible TINYINT DEFAULT 1 COMMENT '是否显示(0-隐藏,1-显示)',
    keep_alive TINYINT DEFAULT 0 COMMENT '是否缓存(0-否,1-是)',
    sort INT DEFAULT 0 COMMENT '排序号',
    level INT DEFAULT 1 COMMENT '层级深度',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    UNIQUE KEY uk_tenant_code (tenant_id, code),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_parent_id (parent_id),
    INDEX idx_type (type),
    INDEX idx_status (status),
    INDEX idx_sort (sort),
    INDEX idx_level (level)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='权限表';

-- auth_user_role表（用户角色关联表）
DROP TABLE IF EXISTS auth_user_role;
CREATE TABLE auth_user_role (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '关联ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    tenant_code VARCHAR(50) COMMENT '租户编码',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    role_id BIGINT NOT NULL COMMENT '角色ID',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    effective_time DATETIME COMMENT '生效时间',
    expire_time DATETIME COMMENT '失效时间',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    sort INT DEFAULT 0 COMMENT '排序号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    UNIQUE KEY uk_user_role (tenant_id, user_id, role_id),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_user_id (user_id),
    INDEX idx_role_id (role_id),
    INDEX idx_status (status),
    
    CONSTRAINT fk_user_role_user FOREIGN KEY (user_id) REFERENCES auth_user(id) ON DELETE CASCADE,
    CONSTRAINT fk_user_role_role FOREIGN KEY (role_id) REFERENCES auth_role(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户角色关联表';

-- auth_role_permission表（角色权限关联表）
DROP TABLE IF EXISTS auth_role_permission;
CREATE TABLE auth_role_permission (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '关联ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    tenant_code VARCHAR(50) COMMENT '租户编码',
    role_id BIGINT NOT NULL COMMENT '角色ID',
    permission_id BIGINT NOT NULL COMMENT '权限ID',
    status TINYINT DEFAULT 1 COMMENT '状态(0-禁用,1-正常)',
    version INT DEFAULT 0 COMMENT '乐观锁版本号',
    sort INT DEFAULT 0 COMMENT '排序号',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记(0-正常,1-删除)',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) COMMENT '创建人',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    update_by VARCHAR(50) COMMENT '更新人',
    remark VARCHAR(255) COMMENT '备注',
    
    UNIQUE KEY uk_role_permission (tenant_id, role_id, permission_id),
    INDEX idx_tenant_id (tenant_id),
    INDEX idx_role_id (role_id),
    INDEX idx_permission_id (permission_id),
    INDEX idx_status (status),
    
    CONSTRAINT fk_role_permission_role FOREIGN KEY (role_id) REFERENCES auth_role(id) ON DELETE CASCADE,
    CONSTRAINT fk_role_permission_permission FOREIGN KEY (permission_id) REFERENCES auth_permission(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色权限关联表';

-- auth_login_log表（登录日志表 - 统一日志记录）
DROP TABLE IF EXISTS auth_login_log;
CREATE TABLE auth_login_log (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '日志ID',
    tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
    user_id BIGINT COMMENT '用户ID',
    username VARCHAR(50) COMMENT '用户名',
    login_type TINYINT DEFAULT 1 COMMENT '登录类型(1-账号密码,2-手机验证码,3-第三方)',
    login_status TINYINT COMMENT '登录状态(0-失败,1-成功,2-登出)',
    login_ip VARCHAR(45) COMMENT '登录IP',
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
    INDEX idx_login_ip (login_ip),
    
    CONSTRAINT fk_login_log_user FOREIGN KEY (user_id) REFERENCES auth_user(id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='登录日志表';

-- auth_operation_log表（操作日志表 - 统一操作记录）
DROP TABLE IF EXISTS auth_operation_log;
CREATE TABLE auth_operation_log (
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
    INDEX idx_operation_time (operation_time),
    
    CONSTRAINT fk_operation_log_user FOREIGN KEY (user_id) REFERENCES auth_user(id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='操作日志表';

-- 恢复外键约束
SET FOREIGN_KEY_CHECKS = 1; 