-- ======================================================
-- 律师事务所管理系统数据库初始化脚本 - 统一版本
-- ======================================================

-- 确保使用UTF8mb4字符集
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- 创建律所管理系统数据库
CREATE DATABASE IF NOT EXISTS law_firm DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE law_firm;

-- ==================
-- 核心系统表 (system)
-- ==================

-- 系统用户表
CREATE TABLE IF NOT EXISTS sys_user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    username VARCHAR(50) NOT NULL COMMENT '用户名',
    password VARCHAR(100) NOT NULL COMMENT '密码',
    real_name VARCHAR(50) COMMENT '真实姓名',
    avatar VARCHAR(255) COMMENT '头像URL',
    email VARCHAR(100) COMMENT '邮箱',
    mobile VARCHAR(20) COMMENT '手机号',
    gender TINYINT DEFAULT 0 COMMENT '性别: 0-未知, 1-男, 2-女',
    dept_id BIGINT COMMENT '部门ID',
    status TINYINT DEFAULT 1 COMMENT '状态: 0-禁用, 1-正常',
    login_ip VARCHAR(50) COMMENT '最后登录IP',
    login_time DATETIME COMMENT '最后登录时间',
    remark VARCHAR(255) COMMENT '备注',
    creator BIGINT COMMENT '创建人',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updater BIGINT COMMENT '更新人',
    update_time DATETIME ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '是否删除: 0-未删除, 1-已删除',
    UNIQUE KEY uk_username (username)
) COMMENT '系统用户表';

-- 系统角色表
CREATE TABLE IF NOT EXISTS sys_role (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    name VARCHAR(50) NOT NULL COMMENT '角色名称',
    code VARCHAR(50) NOT NULL COMMENT '角色编码',
    sort INT DEFAULT 0 COMMENT '显示顺序',
    status TINYINT DEFAULT 1 COMMENT '状态: 0-禁用, 1-正常',
    remark VARCHAR(255) COMMENT '备注',
    creator BIGINT COMMENT '创建人',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updater BIGINT COMMENT '更新人',
    update_time DATETIME ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '是否删除: 0-未删除, 1-已删除',
    UNIQUE KEY uk_code (code)
) COMMENT '系统角色表';

-- 用户角色关联表
CREATE TABLE IF NOT EXISTS sys_user_role (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    role_id BIGINT NOT NULL COMMENT '角色ID',
    creator BIGINT COMMENT '创建人',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updater BIGINT COMMENT '更新人',
    update_time DATETIME ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '是否删除: 0-未删除, 1-已删除',
    UNIQUE KEY uk_user_role (user_id, role_id)
) COMMENT '用户角色关联表';

-- 系统菜单表
CREATE TABLE IF NOT EXISTS sys_menu (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    parent_id BIGINT DEFAULT 0 COMMENT '父菜单ID',
    name VARCHAR(50) NOT NULL COMMENT '菜单名称',
    path VARCHAR(200) COMMENT '路由路径',
    component VARCHAR(255) COMMENT '组件路径',
    perms VARCHAR(100) COMMENT '权限标识',
    type TINYINT NOT NULL COMMENT '类型: 0-目录, 1-菜单, 2-按钮',
    icon VARCHAR(100) COMMENT '图标',
    sort INT DEFAULT 0 COMMENT '显示顺序',
    visible TINYINT DEFAULT 1 COMMENT '是否可见: 0-隐藏, 1-显示',
    status TINYINT DEFAULT 1 COMMENT '状态: 0-禁用, 1-正常',
    creator BIGINT COMMENT '创建人',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updater BIGINT COMMENT '更新人',
    update_time DATETIME ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '是否删除: 0-未删除, 1-已删除'
) COMMENT '系统菜单表';

-- 角色菜单关联表
CREATE TABLE IF NOT EXISTS sys_role_menu (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    role_id BIGINT NOT NULL COMMENT '角色ID',
    menu_id BIGINT NOT NULL COMMENT '菜单ID',
    creator BIGINT COMMENT '创建人',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updater BIGINT COMMENT '更新人',
    update_time DATETIME ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '是否删除: 0-未删除, 1-已删除',
    UNIQUE KEY uk_role_menu (role_id, menu_id)
) COMMENT '角色菜单关联表';

-- 部门表
CREATE TABLE IF NOT EXISTS sys_dept (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
    parent_id BIGINT DEFAULT 0 COMMENT '父部门ID',
    name VARCHAR(50) NOT NULL COMMENT '部门名称',
    sort INT DEFAULT 0 COMMENT '显示顺序',
    leader VARCHAR(50) COMMENT '负责人',
    phone VARCHAR(20) COMMENT '联系电话',
    email VARCHAR(100) COMMENT '邮箱',
    status TINYINT DEFAULT 1 COMMENT '状态: 0-禁用, 1-正常',
    creator BIGINT COMMENT '创建人',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updater BIGINT COMMENT '更新人',
    update_time DATETIME ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT DEFAULT 0 COMMENT '是否删除: 0-未删除, 1-已删除'
) COMMENT '部门表';

-- ==================
-- 初始化数据
-- ==================

-- 插入管理员用户 (密码: admin123)
INSERT INTO sys_user (id, username, password, real_name, email, status, creator, create_time) 
VALUES (1, 'admin', '$2a$10$mW/yJPHjyueQ1g26WNDhtO6M8zJ7UgaDiPdQ4PGFqKGJTRHyGj.Uy', '系统管理员', 'admin@lawfirm.com', 1, 1, NOW())
ON DUPLICATE KEY UPDATE
    update_time = NOW();

-- 插入角色
INSERT INTO sys_role (id, name, code, sort, status, creator, create_time)
VALUES 
    (1, '超级管理员', 'SUPER_ADMIN', 1, 1, 1, NOW()),
    (2, '普通用户', 'USER', 2, 1, 1, NOW())
ON DUPLICATE KEY UPDATE
    update_time = NOW();

-- 绑定用户和角色
INSERT INTO sys_user_role (user_id, role_id, creator, create_time)
VALUES (1, 1, 1, NOW())
ON DUPLICATE KEY UPDATE
    update_time = NOW();

-- 插入菜单
INSERT INTO sys_menu (id, parent_id, name, path, component, perms, type, icon, sort, visible, status, creator, create_time)
VALUES
    -- 系统管理
    (1, 0, '系统管理', '/system', NULL, NULL, 0, 'system', 1, 1, 1, 1, NOW()),
    -- 用户管理
    (2, 1, '用户管理', '/system/user', 'system/user/index', 'system:user:list', 1, 'user', 1, 1, 1, 1, NOW()),
    -- 用户管理按钮
    (3, 2, '用户查询', NULL, NULL, 'system:user:query', 2, NULL, 1, 1, 1, 1, NOW()),
    (4, 2, '用户新增', NULL, NULL, 'system:user:create', 2, NULL, 2, 1, 1, 1, NOW()),
    (5, 2, '用户修改', NULL, NULL, 'system:user:update', 2, NULL, 3, 1, 1, 1, NOW()),
    (6, 2, '用户删除', NULL, NULL, 'system:user:delete', 2, NULL, 4, 1, 1, 1, NOW()),
    -- 角色管理
    (7, 1, '角色管理', '/system/role', 'system/role/index', 'system:role:list', 1, 'role', 2, 1, 1, 1, NOW()),
    -- 角色管理按钮
    (8, 7, '角色查询', NULL, NULL, 'system:role:query', 2, NULL, 1, 1, 1, 1, NOW()),
    (9, 7, '角色新增', NULL, NULL, 'system:role:create', 2, NULL, 2, 1, 1, 1, NOW()),
    (10, 7, '角色修改', NULL, NULL, 'system:role:update', 2, NULL, 3, 1, 1, 1, NOW()),
    (11, 7, '角色删除', NULL, NULL, 'system:role:delete', 2, NULL, 4, 1, 1, 1, NOW()),
    -- 菜单管理
    (12, 1, '菜单管理', '/system/menu', 'system/menu/index', 'system:menu:list', 1, 'menu', 3, 1, 1, 1, NOW()),
    -- 部门管理
    (13, 1, '部门管理', '/system/dept', 'system/dept/index', 'system:dept:list', 1, 'dept', 4, 1, 1, 1, NOW())
ON DUPLICATE KEY UPDATE
    update_time = NOW();

-- 绑定角色和菜单
INSERT INTO sys_role_menu (role_id, menu_id, creator, create_time)
VALUES
    -- 超级管理员拥有所有菜单权限
    (1, 1, 1, NOW()),
    (1, 2, 1, NOW()),
    (1, 3, 1, NOW()),
    (1, 4, 1, NOW()),
    (1, 5, 1, NOW()),
    (1, 6, 1, NOW()),
    (1, 7, 1, NOW()),
    (1, 8, 1, NOW()),
    (1, 9, 1, NOW()),
    (1, 10, 1, NOW()),
    (1, 11, 1, NOW()),
    (1, 12, 1, NOW()),
    (1, 13, 1, NOW()),
    -- 普通用户仅拥有查询权限
    (2, 1, 1, NOW()),
    (2, 2, 1, NOW()),
    (2, 3, 1, NOW()),
    (2, 7, 1, NOW()),
    (2, 8, 1, NOW()),
    (2, 12, 1, NOW()),
    (2, 13, 1, NOW())
ON DUPLICATE KEY UPDATE
    update_time = NOW();

-- 部门表初始数据
INSERT INTO sys_dept (id, parent_id, name, sort, leader, status, creator, create_time)
VALUES
    (1, 0, '律所总部', 1, '张三', 1, 1, NOW()),
    (2, 1, '法律事务部', 1, '李四', 1, 1, NOW()),
    (3, 1, '行政部', 2, '王五', 1, 1, NOW()),
    (4, 1, '财务部', 3, '赵六', 1, 1, NOW()),
    (5, 2, '民事法律组', 1, '孙七', 1, 1, NOW()),
    (6, 2, '商事法律组', 2, '周八', 1, 1, NOW())
ON DUPLICATE KEY UPDATE
    update_time = NOW();

-- 重新启用外键检查
SET FOREIGN_KEY_CHECKS = 1; 