-- 档案模块权限数据
-- 版本: V12003
-- 模块: archive
-- 创建时间: 2025-04-26
-- 说明: 初始化档案管理模块的权限数据
-- 依赖: V12001档案表结构, V0002基础权限表数据

-- 设置字符集
SET NAMES utf8mb4;

-- 档案管理菜单
INSERT INTO sys_menu(menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES('档案管理', 0, 120, 'archive', NULL, 1, 0, 'M', '0', '0', '', 'archive', 'admin', NOW(), '', NULL, '档案管理菜单');

-- 获取档案管理菜单ID
SET @parentId = LAST_INSERT_ID();

-- 案件档案管理菜单
INSERT INTO sys_menu(menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES('案件档案', @parentId, 1, 'caseArchive', 'archive/caseArchive/index', 1, 0, 'C', '0', '0', 'archive:case:list', 'case-archive', 'admin', NOW(), '', NULL, '案件档案菜单');

-- 获取案件档案管理菜单ID
SET @caseArchiveId = LAST_INSERT_ID();

-- 案件档案管理按钮
INSERT INTO sys_menu(menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES('案件档案查询', @caseArchiveId, 1, '', '', 1, 0, 'F', '0', '0', 'archive:case:query', '#', 'admin', NOW(), '', NULL, '');

INSERT INTO sys_menu(menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES('案件档案新增', @caseArchiveId, 2, '', '', 1, 0, 'F', '0', '0', 'archive:case:add', '#', 'admin', NOW(), '', NULL, '');

INSERT INTO sys_menu(menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES('案件档案修改', @caseArchiveId, 3, '', '', 1, 0, 'F', '0', '0', 'archive:case:edit', '#', 'admin', NOW(), '', NULL, '');

INSERT INTO sys_menu(menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES('案件档案删除', @caseArchiveId, 4, '', '', 1, 0, 'F', '0', '0', 'archive:case:remove', '#', 'admin', NOW(), '', NULL, '');

INSERT INTO sys_menu(menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES('案件档案导出', @caseArchiveId, 5, '', '', 1, 0, 'F', '0', '0', 'archive:case:export', '#', 'admin', NOW(), '', NULL, '');

-- 档案文件菜单
INSERT INTO sys_menu(menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES('档案文件', @parentId, 2, 'archiveFile', 'archive/archiveFile/index', 1, 0, 'C', '0', '0', 'archive:file:list', 'file', 'admin', NOW(), '', NULL, '档案文件菜单');

-- 获取档案文件菜单ID
SET @archiveFileId = LAST_INSERT_ID();

-- 档案文件按钮
INSERT INTO sys_menu(menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES('档案文件查询', @archiveFileId, 1, '', '', 1, 0, 'F', '0', '0', 'archive:file:query', '#', 'admin', NOW(), '', NULL, '');

INSERT INTO sys_menu(menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES('档案文件上传', @archiveFileId, 2, '', '', 1, 0, 'F', '0', '0', 'archive:file:upload', '#', 'admin', NOW(), '', NULL, '');

INSERT INTO sys_menu(menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES('档案文件删除', @archiveFileId, 3, '', '', 1, 0, 'F', '0', '0', 'archive:file:remove', '#', 'admin', NOW(), '', NULL, '');

INSERT INTO sys_menu(menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES('档案文件借阅', @archiveFileId, 4, '', '', 1, 0, 'F', '0', '0', 'archive:file:borrow', '#', 'admin', NOW(), '', NULL, '');

INSERT INTO sys_menu(menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES('档案文件归还', @archiveFileId, 5, '', '', 1, 0, 'F', '0', '0', 'archive:file:return', '#', 'admin', NOW(), '', NULL, '');

INSERT INTO sys_menu(menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES('档案文件下载', @archiveFileId, 6, '', '', 1, 0, 'F', '0', '0', 'archive:file:download', '#', 'admin', NOW(), '', NULL, '');

-- 档案管理菜单
INSERT INTO sys_menu(menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
VALUES('档案统计', @parentId, 3, 'archiveStats', 'archive/stats/index', 1, 0, 'C', '0', '0', 'archive:stats:list', 'chart', 'admin', NOW(), '', NULL, '档案统计菜单');

-- 赋予管理员所有档案模块权限
INSERT INTO sys_role_menu(role_id, menu_id)
SELECT 1, menu_id FROM sys_menu WHERE menu_id = @parentId OR parent_id = @parentId OR parent_id IN (SELECT menu_id FROM sys_menu WHERE parent_id = @parentId); 