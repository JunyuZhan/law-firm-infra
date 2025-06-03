-- 档案模块权限数据
-- 版本: V12003
-- 模块: archive
-- 创建时间: 2025-04-26
-- 说明: 初始化档案管理模块的权限数据
-- 依赖: V12001档案表结构, V0002基础权限表数据

-- 设置字符集
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- 检查sys_menu表是否存在
SELECT COUNT(*) INTO @sys_menu_exists 
FROM information_schema.tables 
WHERE table_schema = DATABASE() 
AND table_name = 'sys_menu';

-- 如果sys_menu表不存在，先创建它
DROP PROCEDURE IF EXISTS create_sys_menu_if_not_exists;
DELIMITER //
CREATE PROCEDURE create_sys_menu_if_not_exists()
BEGIN
    IF @sys_menu_exists = 0 THEN
        CREATE TABLE IF NOT EXISTS sys_menu (
            menu_id BIGINT NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
            name VARCHAR(100) NOT NULL COMMENT '菜单名称',
            parent_id BIGINT DEFAULT 0 COMMENT '父菜单ID',
            sort INTEGER DEFAULT 0 COMMENT '显示顺序',
            path VARCHAR(200) COMMENT '路由地址',
            component VARCHAR(255) COMMENT '组件路径',
            always_show TINYINT DEFAULT 0 COMMENT '是否始终显示',
            keep_alive TINYINT DEFAULT 0 COMMENT '是否缓存',
            type INTEGER DEFAULT 1 COMMENT '菜单类型（1-目录 2-菜单 3-按钮）',
            visible TINYINT DEFAULT 1 COMMENT '菜单状态（0-隐藏 1-显示）',
            status TINYINT DEFAULT 1 COMMENT '菜单状态（0-禁用 1-正常）',
            permission VARCHAR(100) COMMENT '权限标识',
            icon VARCHAR(100) COMMENT '菜单图标',
            creator BIGINT COMMENT '创建者',
            create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
            updater BIGINT COMMENT '更新者',
            update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
            deleted TINYINT DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
            PRIMARY KEY (menu_id),
            KEY idx_parent_id (parent_id)
        ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='菜单权限表';

        -- 创建sys_role_menu表，如果它不存在
        CREATE TABLE IF NOT EXISTS sys_role_menu (
            id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
            role_id BIGINT NOT NULL COMMENT '角色ID',
            menu_id BIGINT NOT NULL COMMENT '菜单ID',
            creator BIGINT COMMENT '创建者',
            create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
            updater BIGINT COMMENT '更新者',
            update_time DATETIME DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
            deleted TINYINT DEFAULT 0 COMMENT '删除标记（0-正常，1-删除）',
            tenant_id BIGINT DEFAULT 0 COMMENT '租户ID',
            PRIMARY KEY (id),
            UNIQUE KEY uk_role_menu (role_id, menu_id)
        ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色菜单关联表';
        
        -- 记录创建表日志
        INSERT INTO sys_operation_log(operate_type, module, description, create_time, operator_name)
        VALUES ('CREATE', 'archive', 'Created sys_menu and sys_role_menu tables in V12003', NOW(), 'system');
    END IF;
END //
DELIMITER ;

-- 执行检查并创建表的存储过程
CALL create_sys_menu_if_not_exists();
DROP PROCEDURE IF EXISTS create_sys_menu_if_not_exists;

-- 档案管理菜单
INSERT INTO sys_menu(name, parent_id, sort, path, component, always_show, keep_alive, type, visible, status, permission, icon, creator, create_time, updater, update_time, deleted)
VALUES('档案管理', 0, 120, 'archive', NULL, 1, 1, 1, 1, 1, '', 'archive', 1, NOW(), 1, NOW(), 0);

-- 获取档案管理菜单ID
SET @parentId = LAST_INSERT_ID();

-- 案件档案管理菜单
INSERT INTO sys_menu(name, parent_id, sort, path, component, always_show, keep_alive, type, visible, status, permission, icon, creator, create_time, updater, update_time, deleted)
VALUES('案件档案', @parentId, 1, 'caseArchive', 'archive/caseArchive/index', 1, 1, 2, 1, 1, 'archive:case:list', 'case-archive', 1, NOW(), 1, NOW(), 0);

-- 获取案件档案管理菜单ID
SET @caseArchiveId = LAST_INSERT_ID();

-- 案件档案管理按钮
INSERT INTO sys_menu(name, parent_id, sort, path, component, always_show, keep_alive, type, visible, status, permission, icon, creator, create_time, updater, update_time, deleted)
VALUES('案件档案查询', @caseArchiveId, 1, '', '', 1, 1, 3, 1, 1, 'archive:case:query', '#', 1, NOW(), 1, NOW(), 0);

INSERT INTO sys_menu(name, parent_id, sort, path, component, always_show, keep_alive, type, visible, status, permission, icon, creator, create_time, updater, update_time, deleted)
VALUES('案件档案新增', @caseArchiveId, 2, '', '', 1, 1, 3, 1, 1, 'archive:case:add', '#', 1, NOW(), 1, NOW(), 0);

INSERT INTO sys_menu(name, parent_id, sort, path, component, always_show, keep_alive, type, visible, status, permission, icon, creator, create_time, updater, update_time, deleted)
VALUES('案件档案修改', @caseArchiveId, 3, '', '', 1, 1, 3, 1, 1, 'archive:case:edit', '#', 1, NOW(), 1, NOW(), 0);

INSERT INTO sys_menu(name, parent_id, sort, path, component, always_show, keep_alive, type, visible, status, permission, icon, creator, create_time, updater, update_time, deleted)
VALUES('案件档案删除', @caseArchiveId, 4, '', '', 1, 1, 3, 1, 1, 'archive:case:remove', '#', 1, NOW(), 1, NOW(), 0);

INSERT INTO sys_menu(name, parent_id, sort, path, component, always_show, keep_alive, type, visible, status, permission, icon, creator, create_time, updater, update_time, deleted)
VALUES('案件档案导出', @caseArchiveId, 5, '', '', 1, 1, 3, 1, 1, 'archive:case:export', '#', 1, NOW(), 1, NOW(), 0);

-- 档案文件菜单
INSERT INTO sys_menu(name, parent_id, sort, path, component, always_show, keep_alive, type, visible, status, permission, icon, creator, create_time, updater, update_time, deleted)
VALUES('档案文件', @parentId, 2, 'archiveFile', 'archive/archiveFile/index', 1, 1, 2, 1, 1, 'archive:file:list', 'file', 1, NOW(), 1, NOW(), 0);

-- 获取档案文件菜单ID
SET @archiveFileId = LAST_INSERT_ID();

-- 档案文件按钮
INSERT INTO sys_menu(name, parent_id, sort, path, component, always_show, keep_alive, type, visible, status, permission, icon, creator, create_time, updater, update_time, deleted)
VALUES('档案文件查询', @archiveFileId, 1, '', '', 1, 1, 3, 1, 1, 'archive:file:query', '#', 1, NOW(), 1, NOW(), 0);

INSERT INTO sys_menu(name, parent_id, sort, path, component, always_show, keep_alive, type, visible, status, permission, icon, creator, create_time, updater, update_time, deleted)
VALUES('档案文件上传', @archiveFileId, 2, '', '', 1, 1, 3, 1, 1, 'archive:file:upload', '#', 1, NOW(), 1, NOW(), 0);

INSERT INTO sys_menu(name, parent_id, sort, path, component, always_show, keep_alive, type, visible, status, permission, icon, creator, create_time, updater, update_time, deleted)
VALUES('档案文件删除', @archiveFileId, 3, '', '', 1, 1, 3, 1, 1, 'archive:file:remove', '#', 1, NOW(), 1, NOW(), 0);

INSERT INTO sys_menu(name, parent_id, sort, path, component, always_show, keep_alive, type, visible, status, permission, icon, creator, create_time, updater, update_time, deleted)
VALUES('档案文件借阅', @archiveFileId, 4, '', '', 1, 1, 3, 1, 1, 'archive:file:borrow', '#', 1, NOW(), 1, NOW(), 0);

INSERT INTO sys_menu(name, parent_id, sort, path, component, always_show, keep_alive, type, visible, status, permission, icon, creator, create_time, updater, update_time, deleted)
VALUES('档案文件归还', @archiveFileId, 5, '', '', 1, 1, 3, 1, 1, 'archive:file:return', '#', 1, NOW(), 1, NOW(), 0);

INSERT INTO sys_menu(name, parent_id, sort, path, component, always_show, keep_alive, type, visible, status, permission, icon, creator, create_time, updater, update_time, deleted)
VALUES('档案文件下载', @archiveFileId, 6, '', '', 1, 1, 3, 1, 1, 'archive:file:download', '#', 1, NOW(), 1, NOW(), 0);

-- 档案管理菜单
INSERT INTO sys_menu(name, parent_id, sort, path, component, always_show, keep_alive, type, visible, status, permission, icon, creator, create_time, updater, update_time, deleted)
VALUES('档案统计', @parentId, 3, 'archiveStats', 'archive/stats/index', 1, 1, 2, 1, 1, 'archive:stats:list', 'chart', 1, NOW(), 1, NOW(), 0);

-- 检查sys_role_menu表是否存在
SELECT COUNT(*) INTO @sys_role_menu_exists 
FROM information_schema.tables 
WHERE table_schema = DATABASE() 
AND table_name = 'sys_role_menu';

-- 赋予管理员所有档案模块权限
SET @insert_role_menu = CONCAT("
INSERT INTO sys_role_menu(role_id, menu_id, creator, updater, deleted, tenant_id)
    SELECT 1, menu_id, 1, 1, 0, 0 FROM sys_menu 
    WHERE menu_id = ", @parentId, " OR parent_id = ", @parentId, " 
    OR parent_id IN (SELECT menu_id FROM sys_menu WHERE parent_id = ", @parentId, ")
");

-- 判断表是否存在，存在才执行
SET @stmt = IF(@sys_role_menu_exists > 0, @insert_role_menu, 'SELECT 1');
PREPARE stmt FROM @stmt;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

SET FOREIGN_KEY_CHECKS = 1; 