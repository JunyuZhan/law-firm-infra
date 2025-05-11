-- 档案模块权限数据
-- 版本: V12003
-- 模块: archive
-- 创建时间: 2025-04-26
-- 说明: 初始化档案管理模块的权限数据
-- 依赖: V12001档案表结构, V0002基础权限表数据

-- 设置字符集
SET NAMES utf8mb4;

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

-- 赋予管理员所有档案模块权限
INSERT INTO sys_role_menu(role_id, menu_id, creator, updater, deleted, tenant_id)
SELECT 1, menu_id, 1, 1, 0, 0 FROM sys_menu WHERE menu_id = @parentId OR parent_id = @parentId OR parent_id IN (SELECT menu_id FROM sys_menu WHERE parent_id = @parentId); 